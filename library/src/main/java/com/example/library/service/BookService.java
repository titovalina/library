package com.example.library.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.library.model.Book;
import com.example.library.repository.BookLendingRepository;
import com.example.library.repository.BookRepository;

import java.util.List;

@Service
@Transactional
public class BookService {

    private final BookRepository bookRepository;
    private final BookLendingRepository lendingRepository;

    public BookService(BookRepository bookRepository,
                       BookLendingRepository lendingRepository) {
        this.bookRepository = bookRepository;
        this.lendingRepository = lendingRepository;
    }

    @Transactional(readOnly = true)
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Книга не найдена"));
    }

    public Book create(Book book) {
        if (bookRepository.existsByNameAndAuthorAndPublishingHouse(
                book.getName(),
                book.getAuthor(),
                book.getPublishingHouse()
        )) {
            throw new IllegalStateException("Такая книга уже существует");
        }
        return bookRepository.save(book);
    }

    public Book update(Long id, Book updated) {
        Book existing = findById(id);

        boolean duplicate =
                bookRepository.existsByNameAndAuthorAndPublishingHouse(
                        updated.getName(),
                        updated.getAuthor(),
                        updated.getPublishingHouse()
                ) &&
                        !(existing.getName().equals(updated.getName())
                                && existing.getAuthor().equals(updated.getAuthor())
                                && equalsNullable(existing.getPublishingHouse(), updated.getPublishingHouse()));

        if (duplicate) {
            throw new IllegalStateException("Такая книга уже существует");
        }

        existing.setName(updated.getName());
        existing.setAuthor(updated.getAuthor());
        existing.setGenre(updated.getGenre());
        existing.setPublishingHouse(updated.getPublishingHouse());
        existing.setYearOfPublication(updated.getYearOfPublication());

        return bookRepository.save(existing);
    }

    public void delete(Long id) {
        if (lendingRepository.existsByBookIdAndReturnDateIsNull(id)) {
            throw new IllegalStateException("Книга сейчас выдана");
        }
        bookRepository.delete(findById(id));
    }

    private boolean equalsNullable(Object a, Object b) {
        return a == null ? b == null : a.equals(b);
    }
}
