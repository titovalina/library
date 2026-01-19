package com.example.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.library.service.BookLendingService;
import com.example.library.service.BookService;
import com.example.library.service.ReaderService;

@Controller
public class IndexController {

    private final BookService bookService;
    private final ReaderService readerService;
    private final BookLendingService bookLendingService;

    public IndexController(
            BookService bookService,
            ReaderService readerService,
            BookLendingService bookLendingService
    ) {
        this.bookService = bookService;
        this.readerService = readerService;
        this.bookLendingService = bookLendingService;
    }

    @GetMapping("/")
    public String index(Model model) {

        model.addAttribute("books", bookService.findAll());
        model.addAttribute("readers", readerService.findAll());
        model.addAttribute("activeLendings", bookLendingService.findActiveLendings());

        model.addAttribute("booksCount", bookService.findAll().size());
        model.addAttribute("readersCount", readerService.findAll().size());
        model.addAttribute("activeLendingsCount", bookLendingService.findActiveLendings().size());

        return "index";
    }
}
