package org.example.baptap_25_04.repository;

import org.example.baptap_25_04.Entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}