package br.com.springEstudo.TodoList.business.dto;

import jakarta.validation.constraints.NotBlank;

public record TodoRequestDto( @NotBlank String titulo, String descricao, @NotBlank int prioridade) {

}
