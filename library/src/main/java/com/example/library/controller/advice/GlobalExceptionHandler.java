package com.example.library.controller.advice;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public String handleNotFound(EntityNotFoundException ex, Model model) {
        model.addAttribute("error", ex.getMessage());
        return "error/404";
    }

    @ExceptionHandler(IllegalStateException.class)
    public String handleIllegalState(IllegalStateException ex, Model model) {
        model.addAttribute("error", ex.getMessage());
        return "error/error";
    }

    @ExceptionHandler(RuntimeException.class)
    public String handleRuntime(RuntimeException ex, Model model) {
        model.addAttribute("error", ex.getMessage());
        return "error/error";
    }

    @ExceptionHandler(Exception.class)
    public String handleOther(Exception ex, Model model) {
        model.addAttribute("error", "Произошла непредвиденная ошибка");
        return "error/error";
    }
}
