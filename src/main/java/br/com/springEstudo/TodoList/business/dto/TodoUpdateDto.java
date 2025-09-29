package br.com.springEstudo.TodoList.business.dto;


public record TodoUpdateDto( String titulo, String descricao,Boolean realizado ,Integer prioridade) {

}
