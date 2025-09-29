package br.com.springEstudo.TodoList.business;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import br.com.springEstudo.TodoList.business.dto.TodoRequestDto;
import br.com.springEstudo.TodoList.business.dto.TodoResponseDto;
import br.com.springEstudo.TodoList.business.dto.TodoUpdateDto;
import br.com.springEstudo.TodoList.business.mapstruct.TodoMapper;
import br.com.springEstudo.TodoList.exceptions.ResourceNotFoundException;
import br.com.springEstudo.TodoList.infraestructure.entities.TodoEntity;
import br.com.springEstudo.TodoList.infraestructure.repositories.TodoRepository;

@ExtendWith(MockitoExtension.class)
class TodoServiceTest {

	@Mock
	private TodoRepository todoRepository;

	@Mock
	private TodoMapper todoMapper;

	@InjectMocks
	private TodoService todoService;

	private TodoRequestDto todoRequestDto;
	private TodoResponseDto todoResponseDto;
	private TodoUpdateDto todoUpdateDto;

	@BeforeEach
	void setUp() {
		todoRequestDto = new TodoRequestDto("Title", "Description", 1);
		todoResponseDto = new TodoResponseDto(UUID.randomUUID(), "Title", "Description", false, 1, LocalDateTime.now(),
				LocalDateTime.now());
		todoUpdateDto = new TodoUpdateDto("Updated Title", "Updated Description", true, 2);
	}

	@Test
	void shouldCreateTodo() {
		TodoEntity entity = mock(TodoEntity.class);
		when(todoMapper.paraTodoEntity(todoRequestDto)).thenReturn(entity);
		when(todoRepository.save(entity)).thenReturn(entity);
		when(todoMapper.paraTodoResponseDto(entity)).thenReturn(todoResponseDto);

		// Action
		TodoResponseDto result = todoService.createTodo(todoRequestDto);

		// Verify
		verify(todoMapper).paraTodoEntity(todoRequestDto);
		verify(todoRepository).save(entity);
		verify(todoMapper).paraTodoResponseDto(entity);

		// Asserts
		assertEquals(todoResponseDto, result);
		assertNotNull(result.createdAt());
		assertFalse(result.realizado());
	}

	@Test
	void shouldReturnTodoWhenIdExists() {
		UUID id = UUID.randomUUID();
		TodoEntity entity = mock(TodoEntity.class);
		when(todoRepository.findById(id)).thenReturn(Optional.of(entity));
		when(todoMapper.paraTodoResponseDto(entity)).thenReturn(todoResponseDto);

		// Action
		TodoResponseDto result = todoService.getTodoById(id);

		// Verify
		verify(todoRepository).findById(id);
		verify(todoMapper).paraTodoResponseDto(entity);

		// Asserts
		assertEquals(todoResponseDto, result);
	}

	@Test
	void shouldThrowResourceNotFoundExceptionWhenIdNotExists() {
		UUID id = UUID.randomUUID();
		assertThrows(ResourceNotFoundException.class, () -> todoService.getTodoById(id));
	}

	@Test
	void shouldGetAListOfTodosOrderedByPriority() {
		List<TodoEntity> entities = List.of(mock(TodoEntity.class), mock(TodoEntity.class));
		List<TodoResponseDto> dtos = List.of(todoResponseDto, todoResponseDto);

		when(todoRepository.findAll(any(Sort.class))).thenReturn(entities);
		when(todoMapper.paraListResponseDto(entities)).thenReturn(dtos);

		List<TodoResponseDto> result = todoService.getListTodosOrdenado();

		// verify
		verify(todoRepository).findAll(any(Sort.class));
		verify(todoMapper).paraListResponseDto(entities);

		// Asserts
		assertEquals(dtos, result);
		assertEquals(2, result.size());
	}

	@Test
	void shouldGetAEmptyListOfTodos() {
		List<TodoEntity> entities = List.of();
		List<TodoResponseDto> dtos = List.of();

		when(todoRepository.findAll(any(Sort.class))).thenReturn(entities);
		when(todoMapper.paraListResponseDto(entities)).thenReturn(dtos);

		List<TodoResponseDto> result = todoService.getListTodosOrdenado();

		// verify
		verify(todoRepository).findAll(any(Sort.class));
		verify(todoMapper).paraListResponseDto(entities);

		// Asserts
		assertEquals(dtos, result);
		assertEquals(0, result.size());
	}

	@Test
	void testUpdateTodo() {
		UUID id = UUID.randomUUID();
		TodoEntity entity = mock(TodoEntity.class);
		
		// Mock updated response
	    TodoResponseDto updatedResponse = new TodoResponseDto(
	        id, "Updated Title", "Updated Description", true, 2, LocalDateTime.now(), LocalDateTime.now()
	    );
		when(todoRepository.findById(id)).thenReturn(Optional.of(entity));
		when(todoRepository.save(entity)).thenReturn(entity);
		when(todoMapper.paraTodoResponseDto(entity)).thenReturn(updatedResponse);
		
		TodoResponseDto result = todoService.updateTodo(id, todoUpdateDto);
		
		//Verify
		verify(todoRepository).findById(id);
		verify(todoMapper).update(todoUpdateDto, entity);
		verify(todoRepository).save(entity);
		verify(todoMapper).paraTodoResponseDto(entity);
		
		
		//Asserts	
		assertNotNull(result);
		assertEquals(updatedResponse,result);
		assertEquals("Updated Description",result.descricao());
		assertEquals(2,result.prioridade());
	}

	@Test
	void shouldChangeStatusToTrue() {
		UUID id = UUID.randomUUID();
		TodoEntity entity = mock(TodoEntity.class);
		AtomicBoolean realizado = new AtomicBoolean(false);
		when(todoRepository.findById(id)).thenReturn(Optional.of(entity));
		when(entity.isRealizado()).then(invocation -> realizado.get());
		doAnswer(invocation -> {
			realizado.set(true);
			return null;
		}).when(entity).setRealizado(true);
		
		boolean result = todoService.updateStatus(id);
		
		//Verify
		verify(todoRepository).findById(id);
		verify(entity).setRealizado(true);
		verify(todoRepository).save(entity);
		
		//Asserts
		assertTrue(result);
	}
	@Test
	void shouldThrowResourceNotFoundExceptionWhenIdNotExistUpdateStatus() {
		UUID id = UUID.randomUUID();
		assertThrows(ResourceNotFoundException.class, () -> todoService.updateStatus(id));
	}
	@Test
	void shouldDeleteByIdWhenExists() {
		UUID id = UUID.randomUUID();
		when(todoRepository.existsById(id)).thenReturn(true);
		todoService.deleteById(id);
		verify(todoRepository).existsById(id);
		verify(todoRepository).deleteById(id);
		
	}
	@Test
	void shouldThrowResourceNotFoundExceptionWhenIdNotExistsOnDelete() {
	    UUID id = UUID.randomUUID();
	    when(todoRepository.existsById(id)).thenReturn(false);

	    assertThrows(ResourceNotFoundException.class, () -> todoService.deleteById(id));
	    verify(todoRepository).existsById(id);
	}
}
