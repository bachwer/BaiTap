package org.example.baitap_27_04.repository;

import org.example.baitap_27_04.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}

