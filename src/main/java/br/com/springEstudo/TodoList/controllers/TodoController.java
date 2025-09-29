package br.com.springEstudo.TodoList.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.springEstudo.TodoList.business.TodoService;
import br.com.springEstudo.TodoList.business.dto.TodoRequestDto;
import br.com.springEstudo.TodoList.business.dto.TodoResponseDto;
import br.com.springEstudo.TodoList.business.dto.TodoUpdateDto;



@RestController
@RequestMapping("/api")
public class TodoController {

	private final TodoService todoService;
	
	public TodoController(TodoService todoService) {
		this.todoService=todoService;
	}
	
	@PostMapping("/create")
	public ResponseEntity<TodoResponseDto> createTodo(@RequestBody TodoRequestDto request) {
		TodoResponseDto response =todoService.createTodo(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@GetMapping
	public ResponseEntity<List<TodoResponseDto>> getAll() {
		List<TodoResponseDto>list=todoService.getListTodosOrdenado();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<TodoResponseDto> getById(@PathVariable UUID id) {
		TodoResponseDto response=todoService.getTodoById(id);
		return ResponseEntity.ok().body(response);
	}
	
	@PutMapping("/{id}/update")
	public ResponseEntity<TodoResponseDto> updateTodoExistente(@PathVariable UUID id,@RequestBody TodoUpdateDto request) {
		TodoResponseDto response =todoService.updateTodo(id, request);
		return ResponseEntity.ok().body(response);
	}
	@PutMapping("/{id}/updateStatus")
	public ResponseEntity<TodoResponseDto> updateTodoStatus(@PathVariable UUID id) {
		todoService.updateStatus(id);
		TodoResponseDto response =todoService.getTodoById(id);
		return ResponseEntity.ok().body(response);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable UUID id){
		todoService.deleteById(id);
		return ResponseEntity.ok().build();
	}
	
}
