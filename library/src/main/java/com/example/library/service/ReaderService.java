package com.example.library.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.library.model.Reader;
import com.example.library.repository.BookLendingRepository;
import com.example.library.repository.ReaderRepository;

import java.util.List;

@Service
@Transactional
public class ReaderService {

    private final ReaderRepository readerRepository;
    private final BookLendingRepository bookLendingRepository;

    public ReaderService(ReaderRepository readerRepository,
                         BookLendingRepository bookLendingRepository) {
        this.readerRepository = readerRepository;
        this.bookLendingRepository = bookLendingRepository;
    }

    @Transactional(readOnly = true)
    public List<Reader> findAll() {
        return readerRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Reader findById(Long id) {
        return readerRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Читатель с ID " + id + " не найден"));
    }

    public Reader create(Reader reader) {

        if (readerRepository.existsByReadersTicket(reader.getReadersTicket())) {
            throw new IllegalStateException("Читательский билет уже существует");
        }

        if (readerRepository.existsByFirstNameAndLastNameAndDateOfBirth(
                reader.getFirstName(),
                reader.getLastName(),
                reader.getDateOfBirth())) {
            throw new IllegalStateException("Читатель уже существует");
        }

        return readerRepository.save(reader);
    }

    public Reader update(Long id, Reader updated) {

        Reader existing = findById(id);

        if (readerRepository.existsByFirstNameAndLastNameAndDateOfBirthAndIdNot(
                updated.getFirstName(),
                updated.getLastName(),
                updated.getDateOfBirth(),
                id)) {
            throw new IllegalStateException("Читатель с такими данными уже существует");
        }

        existing.setFirstName(updated.getFirstName());
        existing.setLastName(updated.getLastName());
        existing.setMiddleName(updated.getMiddleName());
        existing.setEmail(updated.getEmail());
        existing.setPhone(updated.getPhone());
        existing.setDateOfBirth(updated.getDateOfBirth());
        existing.setReadersTicket(updated.getReadersTicket());

        return readerRepository.save(existing);
    }

    public void delete(Long id) {
        if (bookLendingRepository.existsByReaderIdAndReturnDateIsNull(id)) {
            throw new IllegalStateException("Нельзя удалить читателя с активными выдачами");
        }
        readerRepository.delete(findById(id));
    }
}
