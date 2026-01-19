package com.example.library.repository;

import com.example.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

    boolean existsByNameAndAuthorAndPublishingHouse(
            String name,
            String author,
            String publishingHouse
    );
}
