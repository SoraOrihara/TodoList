package br.com.springEstudo.TodoList.infraestructure.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.springEstudo.TodoList.infraestructure.entities.TodoEntity;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, UUID> {

}
