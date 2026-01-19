package com.example.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.library.model.BookLending;

import java.util.List;

public interface BookLendingRepository extends JpaRepository<BookLending, Long> {

    boolean existsByReaderIdAndReturnDateIsNull(Long readerId);

    boolean existsByBookIdAndReturnDateIsNull(Long bookId);

    List<BookLending> findAllByReturnDateIsNull();
}
