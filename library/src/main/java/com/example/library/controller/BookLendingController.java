package com.example.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.library.service.BookLendingService;
import com.example.library.repository.BookRepository;
import com.example.library.repository.ReaderRepository;

@Controller
@RequestMapping("/lendings")
public class BookLendingController {

    private final BookLendingService lendingService;
    private final BookRepository bookRepository;
    private final ReaderRepository readerRepository;

    public BookLendingController(BookLendingService lendingService,
                                 BookRepository bookRepository,
                                 ReaderRepository readerRepository) {
        this.lendingService = lendingService;
        this.bookRepository = bookRepository;
        this.readerRepository = readerRepository;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("lendings", lendingService.findActiveLendings());
        return "lendings/list";
    }

    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("books", bookRepository.findAll());
        model.addAttribute("readers", readerRepository.findAll());
        return "lendings/form";
    }

    @PostMapping
    public String issue(@RequestParam Long bookId,
                        @RequestParam Long readerId,
                        RedirectAttributes ra) {
        try {
            lendingService.lendBook(bookId, readerId);
            ra.addFlashAttribute("successMessage", "Книга выдана");
        } catch (Exception e) {
            ra.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/lendings";
    }

    @PostMapping("/return")
    public String returnBook(@RequestParam("lendingId") Long id,
                             RedirectAttributes ra) {
        try {
            lendingService.returnBook(id);
            ra.addFlashAttribute("successMessage", "Книга возвращена");
        } catch (Exception e) {
            ra.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/lendings";
    }

}
