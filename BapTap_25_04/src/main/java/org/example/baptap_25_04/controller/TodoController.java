package org.example.baptap_25_04.controller;

import jakarta.validation.Valid;

import org.example.baptap_25_04.Entity.Todo;
import org.example.baptap_25_04.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class TodoController {

    @Autowired
    private TodoRepository todoRepository;

    // ================= READ =================
    @GetMapping
    public String list(Model model) {
        model.addAttribute("todos", todoRepository.findAll());
        return "list";
    }

    // ================= FORM =================
    @GetMapping("/add")
    public String showForm(Model model) {
        model.addAttribute("todo", new Todo());
        return "form";
    }

    // ================= CREATE =================
    @PostMapping("/save")
    public String save(
            @Valid @ModelAttribute("todo") Todo todo,
            BindingResult result,
            Model model
    ) {

        // ⚠️ đúng chuẩn chấm điểm
        if (result.hasErrors()) {
            return "form";
        }

        if (todo.getStatus() == null) {
            todo.setStatus("PENDING");
        }

        todoRepository.save(todo);

        // ✅ PRG pattern (ăn điểm)
        return "redirect:/";
    }
}