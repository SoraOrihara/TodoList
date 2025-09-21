package br.com.springEstudo.TodoList.business.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record TodoResponseDto(UUID id, String titulo, String descricao, boolean realizado, int prioridade, LocalDateTime createdAt, LocalDateTime updatedAt) {

}
