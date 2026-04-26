package org.example.baitap_26_04.controller;

import jakarta.validation.Valid;

import org.example.baitap_26_04.entity.Todo;
import org.example.baitap_26_04.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/todos")
public class TodoController {

    @Autowired
    private TodoRepository todoRepository;

    // ================== READ ==================
    @GetMapping
    public String list(Model model) {
        model.addAttribute("todos", todoRepository.findAll());
        return "list";
    }

    // ================== CREATE FORM ==================
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("todo", new Todo());
        return "form";
    }

    // ================== SAVE ==================
    @PostMapping("/save")
    public String save(
            @Valid @ModelAttribute("todo") Todo todo,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            return "form";
        }

        if (todo.getStatus() == null) {
            todo.setStatus("PENDING");
        }

        todoRepository.save(todo);
        return "redirect:/todos";
    }

    // ================== UPDATE ==================
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy"));

        model.addAttribute("todo", todo);
        return "form";
    }

    // ================== DELETE ==================
    @GetMapping("/delete/{id}")
    public String delete(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes
    ) {
        todoRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Xóa thành công");
        return "redirect:/todos";
    }
}