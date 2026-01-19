package com.example.library.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "book_lending")
public class BookLending {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_book_lending")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_reader", nullable = false)
    private Reader reader;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_book", nullable = false)
    private Book book;

    @Column(name = "date_of_issue", nullable = false)
    private LocalDate dateOfIssue;

    @Column(name = "date_of_due", nullable = false)
    private LocalDate dateOfDue;

    @Column(name = "return_date")
    private LocalDate returnDate;

    // ===== getters & setters =====

    public Long getId() {
        return id;
    }

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public LocalDate getDateOfIssue() {
        return dateOfIssue;
    }

    public void setDateOfIssue(LocalDate dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
    }

    public LocalDate getDateOfDue() {
        return dateOfDue;
    }

    public void setDateOfDue(LocalDate dateOfDue) {
        this.dateOfDue = dateOfDue;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
}

