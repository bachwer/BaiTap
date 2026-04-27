package org.example.baitap_27_04.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.example.baitap_27_04.config.SessionConstants;
import org.example.baitap_27_04.model.Todo;
import org.example.baitap_27_04.model.TodoPriority;
import org.example.baitap_27_04.model.TodoStatus;
import org.example.baitap_27_04.repository.TodoRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class TodoController {

    private final TodoRepository todoRepository;

    public TodoController(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @GetMapping("/todos")
    public String listTodos(HttpSession session, Model model) {
        if (!hasOwner(session)) {
            return "redirect:/owner";
        }

        if (!model.containsAttribute("todoForm")) {
            model.addAttribute("todoForm", new Todo());
        }

        populatePageModel(model, session);
        model.addAttribute("editMode", false);
        return "todos";
    }

    @GetMapping("/todos/{id}/edit")
    public String showEditForm(@PathVariable Long id, HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        if (!hasOwner(session)) {
            return "redirect:/owner";
        }

        return todoRepository.findById(id)
                .map(todo -> {
                    model.addAttribute("todoForm", todo);
                    model.addAttribute("editMode", true);
                    populatePageModel(model, session);
                    return "todos";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("errorMessage", "todo.notFound");
                    return "redirect:/todos";
                });
    }

    @PostMapping("/todos/save")
    public String saveTodo(
            @Valid @ModelAttribute("todoForm") Todo todoForm,
            BindingResult bindingResult,
            HttpSession session,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        if (!hasOwner(session)) {
            return "redirect:/owner";
        }

        if (bindingResult.hasErrors()) {
            populatePageModel(model, session);
            model.addAttribute("editMode", todoForm.getId() != null);
            return "todos";
        }

        if (todoForm.getId() != null) {
            return updateTodo(todoForm, redirectAttributes);
        }

        todoRepository.save(todoForm);
        redirectAttributes.addFlashAttribute("successMessage", "todo.create.success");
        return "redirect:/todos";
    }

    @PostMapping("/todos/{id}/delete")
    public String deleteTodo(@PathVariable Long id, HttpSession session, RedirectAttributes redirectAttributes) {
        if (!hasOwner(session)) {
            return "redirect:/owner";
        }

        if (!todoRepository.existsById(id)) {
            redirectAttributes.addFlashAttribute("errorMessage", "todo.notFound");
            return "redirect:/todos";
        }

        todoRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "todo.delete.success");
        return "redirect:/todos";
    }

    @PostMapping("/owner/clear")
    public String clearOwner(HttpSession session) {
        session.removeAttribute(SessionConstants.OWNER_NAME);
        return "redirect:/owner";
    }

    @ModelAttribute("statuses")
    public TodoStatus[] statuses() {
        return TodoStatus.values();
    }

    @ModelAttribute("priorities")
    public TodoPriority[] priorities() {
        return TodoPriority.values();
    }

    private boolean hasOwner(HttpSession session) {
        Object owner = session.getAttribute(SessionConstants.OWNER_NAME);
        return owner instanceof String && !((String) owner).isBlank();
    }

    private String updateTodo(Todo todoForm, RedirectAttributes redirectAttributes) {
        return todoRepository.findById(todoForm.getId())
                .map(existingTodo -> {
                    existingTodo.setContent(todoForm.getContent());
                    existingTodo.setDueDate(todoForm.getDueDate());
                    existingTodo.setStatus(todoForm.getStatus());
                    existingTodo.setPriority(todoForm.getPriority());
                    todoRepository.save(existingTodo);
                    redirectAttributes.addFlashAttribute("successMessage", "todo.update.success");
                    return "redirect:/todos";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("errorMessage", "todo.notFound");
                    return "redirect:/todos";
                });
    }

    private void populatePageModel(Model model, HttpSession session) {
        model.addAttribute("ownerName", session.getAttribute(SessionConstants.OWNER_NAME));
        model.addAttribute("todos", todoRepository.findAll(Sort.by(Sort.Direction.DESC, "id")));
    }
}

