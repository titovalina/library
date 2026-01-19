package com.example.library.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

@Entity
@Table(
        name = "reader",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"readers_ticket"}),
                @UniqueConstraint(columnNames = {"first_name", "last_name", "date_of_birth"})
        }
)
public class Reader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reader")
    private Long id;

    @NotBlank(message = "Имя обязательно")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotBlank(message = "Фамилия обязательна")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotBlank(message = "Отчество обязательно")
    @Column(name = "middle_name", nullable = false)
    private String middleName;

    @NotNull(message = "Дата рождения обязательна")
    @Past(message = "Дата рождения должна быть в прошлом")
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @NotBlank(message = "Номер читательского билета обязателен")
    @Column(name = "readers_ticket", nullable = false)
    private String readersTicket;

    @NotBlank(message = "Email обязателен")
    @Email(message = "Email введён некорректно")
    private String email;

    @NotBlank(message = "Телефон обязателен")
    private String phone;

    /* ===== getters / setters ===== */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }


    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }


    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }


    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getReadersTicket() {
        return readersTicket;
    }

    public void setReadersTicket(String readersTicket) {
        this.readersTicket = readersTicket;
    }

    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public String getPhone() {
        return phone;
    }


    public void setPhone(String phone) {
        this.phone = phone;
    }

    /* ===== computed field for Thymeleaf ===== */

    @Transient
    public String getFullName() {
        if (middleName == null || middleName.isBlank()) {
            return lastName + " " + firstName;
        }
        return lastName + " " + firstName + " " + middleName;
    }
}
