package br.com.springEstudo.TodoList.business;

import org.springframework.stereotype.Service;

import br.com.springEstudo.TodoList.business.mapstruct.TodoMapper;
import br.com.springEstudo.TodoList.infraestructure.repositories.TodoRepository;

@Service
public class TodoService {

	private final TodoRepository todoRepository;
	private final TodoMapper todoMapper;
	
	public TodoService(TodoRepository todoRepository, TodoMapper todoMapper) {
		this.todoMapper=todoMapper;
		this.todoRepository=todoRepository;
	}
	
	
	
}
