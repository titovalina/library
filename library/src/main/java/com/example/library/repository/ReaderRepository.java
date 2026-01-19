package com.example.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.library.model.Reader;

import java.time.LocalDate;
import java.util.List;

public interface ReaderRepository extends JpaRepository<Reader, Long> {

    List<Reader> findAllByLastNameIgnoreCase(String lastName);

    boolean existsByReadersTicket(String readersTicket);

    boolean existsByFirstNameAndLastNameAndDateOfBirth(
            String firstName,
            String lastName,
            LocalDate dateOfBirth
    );

    boolean existsByFirstNameAndLastNameAndDateOfBirthAndIdNot(
            String firstName,
            String lastName,
            LocalDate dateOfBirth,
            Long id
    );
}
