package com.example.library.controller;

import com.example.library.model.Book;
import com.example.library.service.BookService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // ===== СПИСОК КНИГ =====
    @GetMapping
    public String list(Model model) {
        model.addAttribute("books", bookService.findAll());
        return "books/list";
    }

    // ===== ФОРМА СОЗДАНИЯ =====
    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("book", new Book());
        return "books/form";
    }

    // ===== СОЗДАНИЕ =====
    @PostMapping
    public String create(
            @Valid @ModelAttribute("book") Book book,
            BindingResult result,
            RedirectAttributes ra
    ) {
        if (result.hasErrors()) {
            return "books/form";
        }

        try {
            bookService.create(book);
            ra.addFlashAttribute("successMessage", "Книга добавлена");
            return "redirect:/books";
        } catch (IllegalStateException e) {
            result.rejectValue("name", "", e.getMessage());
            return "books/form";
        }
    }

    // ===== ФОРМА РЕДАКТИРОВАНИЯ =====
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("book", bookService.findById(id));
        return "books/form";
    }

    // ===== ОБНОВЛЕНИЕ =====
    @PostMapping("/edit/{id}")
    public String update(
            @PathVariable Long id,
            @Valid @ModelAttribute("book") Book book,
            BindingResult result,
            RedirectAttributes ra
    ) {
        if (result.hasErrors()) {
            return "books/form";
        }

        try {
            bookService.update(id, book);
            ra.addFlashAttribute("successMessage", "Книга обновлена");
            return "redirect:/books";
        } catch (IllegalStateException e) {
            result.rejectValue("name", "", e.getMessage());
            return "books/form";
        }
    }

    // ===== УДАЛЕНИЕ =====
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        try {
            bookService.delete(id);
            ra.addFlashAttribute("successMessage", "Книга удалена");
        } catch (IllegalStateException e) {
            ra.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/books";
    }
}
