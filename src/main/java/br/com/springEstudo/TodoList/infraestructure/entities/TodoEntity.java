package br.com.springEstudo.TodoList.infraestructure.entities;


import java.util.Objects;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="tb_todo")
public class TodoEntity extends AuditableEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.UUID)
	private UUID id;
	
	@Column(name="titulo",nullable=false)
	private String titulo;
	
	private String descricao;
	
	private boolean realizado;

	@Column(name="titulo",nullable=false)
	private int prioridade;

	public TodoEntity() {
		super();
	}
	
	public TodoEntity(String titulo, String descricao, int prioridade) {
		super();
		this.titulo = titulo;
		this.descricao = descricao;
		this.prioridade = prioridade;
	}

	public TodoEntity(String titulo, String descricao, boolean realizado, int prioridade) {
		super();
		this.titulo = titulo;
		this.descricao = descricao;
		this.realizado = realizado;
		this.prioridade = prioridade;
	}

	public TodoEntity(UUID id, String titulo, String descricao, boolean realizado, int prioridade) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.descricao = descricao;
		this.realizado = realizado;
		this.prioridade = prioridade;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public boolean isRealizado() {
		return realizado;
	}

	public void setRealizado(boolean realizado) {
		this.realizado = realizado;
	}

	public int getPrioridade() {
		return prioridade;
	}

	public void setPrioridade(int prioridade) {
		this.prioridade = prioridade;
	}

	public UUID getId() {
		return id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, prioridade);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TodoEntity other = (TodoEntity) obj;
		return Objects.equals(id, other.id) && prioridade == other.prioridade;
	}
	
	
	
	
	
}
