package br.com.springEstudo.TodoList.business.dto;

public record TodoRequestDto(String titulo, String descricao, boolean realizado, int prioridade) {

}
