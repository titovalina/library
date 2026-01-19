package com.example.library.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_book")
    private Long id;

    @NotBlank(message = "Название книги обязательно")
    @Size(max = 225)
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank(message = "Автор обязателен")
    @Size(max = 100)
    @Column(name = "author", nullable = false)
    private String author;

    @Size(max = 50)
    @Column(name = "publishing_house")
    private String publishingHouse;

    @Size(max = 50)
    @Column(name = "genre")
    private String genre;

    @Min(0)
    @Column(name = "year_of_publication")
    private Integer yearOfPublication;

    // ===== getters / setters =====

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublishingHouse() {
        return publishingHouse;
    }

    public void setPublishingHouse(String publishingHouse) {
        this.publishingHouse = publishingHouse;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Integer getYearOfPublication() {
        return yearOfPublication;
    }

    public void setYearOfPublication(Integer yearOfPublication) {
        this.yearOfPublication = yearOfPublication;
    }
}
