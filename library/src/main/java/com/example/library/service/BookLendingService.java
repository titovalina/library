package com.example.library.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.library.model.Book;
import com.example.library.model.BookLending;
import com.example.library.model.Reader;
import com.example.library.repository.BookLendingRepository;
import com.example.library.repository.BookRepository;
import com.example.library.repository.ReaderRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class BookLendingService {

    private final BookLendingRepository lendingRepository;
    private final BookRepository bookRepository;
    private final ReaderRepository readerRepository;

    public BookLendingService(BookLendingRepository lendingRepository,
                              BookRepository bookRepository,
                              ReaderRepository readerRepository) {
        this.lendingRepository = lendingRepository;
        this.bookRepository = bookRepository;
        this.readerRepository = readerRepository;
    }

    // ===== ВЫДАЧА КНИГИ =====
    public void lendBook(Long bookId, Long readerId) {

        if (lendingRepository.existsByBookIdAndReturnDateIsNull(bookId)) {
            throw new IllegalStateException("Книга уже выдана");
        }

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Книга не найдена"));

        Reader reader = readerRepository.findById(readerId)
                .orElseThrow(() -> new EntityNotFoundException("Читатель не найден"));

        BookLending lending = new BookLending();
        lending.setBook(book);
        lending.setReader(reader);
        lending.setDateOfIssue(LocalDate.now());
        lending.setDateOfDue(LocalDate.now().plusDays(14));

        lendingRepository.save(lending);
    }

    // ===== ВОЗВРАТ КНИГИ =====
    public void returnBook(Long lendingId) {
        BookLending lending = lendingRepository.findById(lendingId)
                .orElseThrow(() -> new EntityNotFoundException("Выдача не найдена"));

        if (lending.getReturnDate() != null) {
            throw new IllegalStateException("Книга уже возвращена");
        }

        lending.setReturnDate(LocalDate.now());
        lendingRepository.save(lending);
    }

    // ===== АКТИВНЫЕ ВЫДАЧИ =====
    @Transactional(readOnly = true)
    public List<BookLending> findActiveLendings() {
        return lendingRepository.findAllByReturnDateIsNull();
    }
}
