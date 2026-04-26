package org.example.baitap_26_04.repository;


import org.example.baitap_26_04.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}