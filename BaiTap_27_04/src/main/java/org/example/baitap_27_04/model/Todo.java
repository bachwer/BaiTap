package org.example.baitap_27_04.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "{todo.content.notBlank}")
    private String content;

    @FutureOrPresent(message = "{todo.dueDate.futureOrPresent}")
    private LocalDate dueDate;

    @NotNull(message = "{todo.status.notNull}")
    @Enumerated(EnumType.STRING)
    private TodoStatus status = TodoStatus.TODO;

    @NotNull(message = "{todo.priority.notNull}")
    @Enumerated(EnumType.STRING)
    private TodoPriority priority = TodoPriority.MEDIUM;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public TodoStatus getStatus() {
        return status;
    }

    public void setStatus(TodoStatus status) {
        this.status = status;
    }

    public TodoPriority getPriority() {
        return priority;
    }

    public void setPriority(TodoPriority priority) {
        this.priority = priority;
    }
}

