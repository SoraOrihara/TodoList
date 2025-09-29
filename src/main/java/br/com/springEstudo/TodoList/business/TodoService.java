package br.com.springEstudo.TodoList.business;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.springEstudo.TodoList.business.dto.TodoRequestDto;
import br.com.springEstudo.TodoList.business.dto.TodoResponseDto;
import br.com.springEstudo.TodoList.business.dto.TodoUpdateDto;
import br.com.springEstudo.TodoList.business.mapstruct.TodoMapper;
import br.com.springEstudo.TodoList.exceptions.ResourceNotFoundException;
import br.com.springEstudo.TodoList.infraestructure.entities.TodoEntity;
import br.com.springEstudo.TodoList.infraestructure.repositories.TodoRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class TodoService {

	private final TodoRepository todoRepository;
	private final TodoMapper todoMapper;

	public TodoService(TodoRepository todoRepository, TodoMapper todoMapper) {
		this.todoMapper = todoMapper;
		this.todoRepository = todoRepository;
	}

	@Transactional
	public TodoResponseDto createTodo(@Valid TodoRequestDto request) {
		TodoEntity entity = todoMapper.paraTodoEntity(request);
		TodoEntity entitySaved = todoRepository.save(entity);
		return todoMapper.paraTodoResponseDto(entitySaved);
	}

	public TodoResponseDto getTodoById(UUID id) {
		TodoEntity entity = todoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Id não encontrada: " + id));
		return todoMapper.paraTodoResponseDto(entity);
	}

	public List<TodoResponseDto> getListTodosOrdenado() {
		Sort sort = Sort.by("prioridade").descending().and(Sort.by("titulo").ascending());
		List<TodoEntity> lista = todoRepository.findAll(sort);
		return todoMapper.paraListResponseDto(lista);
	}

	@Transactional
	public TodoResponseDto updateTodo(UUID id, TodoUpdateDto request) {
		TodoEntity todo = todoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Id não encontrado" + id));
		todoMapper.update(request, todo);
		TodoEntity todoSalvo = todoRepository.save(todo);
		return todoMapper.paraTodoResponseDto(todoSalvo);
	}

	public boolean updateStatus(UUID id) {
		if (!todoRepository.existsById(id)) {
			throw new ResourceNotFoundException("Id não encontrado: " + id);
		}
		TodoEntity todo = todoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Id não encontrado" + id));
		if (todo.isRealizado() == true) {
			todo.setRealizado(false);
			return todo.isRealizado();
		} else {
			todo.setRealizado(true);
			return todo.isRealizado();
		}

	}
	
	@Transactional
	public void deleteById(UUID id) {
		if(!todoRepository.existsById(id)) {
			throw new ResourceNotFoundException("Id não encontrado: " + id);
		}
		try {
			todoRepository.deleteById(id);
		} catch (Exception e) {
			e.getMessage();
		}
	}
}
