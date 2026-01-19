package com.example.library.controller;

import com.example.library.model.Reader;
import com.example.library.service.ReaderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/readers")
public class ReaderController {

    private final ReaderService readerService;

    public ReaderController(ReaderService readerService) {
        this.readerService = readerService;
    }

    // ===== СПИСОК ЧИТАТЕЛЕЙ =====
    @GetMapping
    public String list(Model model) {
        model.addAttribute("readers", readerService.findAll());
        return "reader/list";
    }

    // ===== ФОРМА СОЗДАНИЯ =====
    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("reader", new Reader());
        return "reader/form";
    }

    // ===== СОЗДАНИЕ =====
    @PostMapping
    public String create(@ModelAttribute Reader reader, Model model) {
        try {
            readerService.create(reader);
            return "redirect:/readers";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("reader", reader);
            return "reader/form";
        }
    }

    // ===== ФОРМА РЕДАКТИРОВАНИЯ =====
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("reader", readerService.findById(id));
        return "reader/form";
    }

    // ===== ОБНОВЛЕНИЕ =====
    @PostMapping("/{id}")
    public String update(
            @PathVariable Long id,
            @ModelAttribute Reader reader,
            Model model
    ) {
        try {
            readerService.update(id, reader);
            return "redirect:/readers";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("reader", reader);
            return "reader/form";
        }
    }

    // ===== УДАЛЕНИЕ =====
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, Model model) {
        try {
            readerService.delete(id);
            return "redirect:/readers";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return list(model);
        }
    }
}
