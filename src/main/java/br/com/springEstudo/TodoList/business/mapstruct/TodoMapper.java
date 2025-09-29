package br.com.springEstudo.TodoList.business.mapstruct;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import br.com.springEstudo.TodoList.business.dto.TodoRequestDto;
import br.com.springEstudo.TodoList.business.dto.TodoResponseDto;
import br.com.springEstudo.TodoList.business.dto.TodoUpdateDto;
import br.com.springEstudo.TodoList.infraestructure.entities.TodoEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface TodoMapper {

	@Mapping(target="id",ignore=true)
	@Mapping(target="realizado",ignore=true)
	TodoEntity paraTodoEntity(TodoRequestDto request);


	TodoEntity paraTodoEntity(TodoUpdateDto request);
	
	TodoResponseDto paraTodoResponseDto(TodoEntity entity);
	
	List<TodoResponseDto> paraListResponseDto(List<TodoEntity> entity);
	
	@Mapping(target = "createdAt", ignore = true)
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void update(TodoUpdateDto request , @MappingTarget TodoEntity entity);
	
}
