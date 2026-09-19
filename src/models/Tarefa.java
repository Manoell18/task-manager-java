package models;

import java.time.LocalDate;

import exceptions.StatusInvalidoException;

public abstract class Tarefa {
	private final String id;
	private String titulo;
	private String descricao;
	private LocalDate prazo;
	private Prioridade prioridade;
	private StatusTarefa status;
	private Usuario responsavel;

	public Tarefa(String id, String titulo, String descricao, LocalDate prazo, Prioridade prioridade, Usuario responsavel) {
		this.id = id;
		this.titulo = titulo;
		this.descricao = descricao;
		this.prazo = prazo;
		this.prioridade = prioridade;
		this.responsavel = responsavel;
		this.status = StatusTarefa.PENDENTE;
	}

	public String getId() {return id;}
	public String getTitulo() {return titulo;}
	public String getDescricao() {return descricao;}
	public LocalDate getPrazo() {return prazo;}
	public Prioridade getPrioridade() {return prioridade;}
	public StatusTarefa getStatus() {return status;}
	public Usuario getResponsavel() {return responsavel;}
	
	public void concluir() throws StatusInvalidoException {
		if (status == StatusTarefa.CONCLUIDA) {
			throw new StatusInvalidoException("A tarefa " + id + " já esta concluida");
		}
		
		if (status == StatusTarefa.CANCELADA) {
			throw new StatusInvalidoException("Não é possivel concluir uma tarefa cancelada");
		}

		this.status = StatusTarefa.CONCLUIDA;
	}

	public void cancelar() throws StatusInvalidoException {
		if (status == StatusTarefa.CANCELADA) {
			throw new StatusInvalidoException("A tarefa " + id + " já esta cancelada");
		}
		if (status == StatusTarefa.CONCLUIDA) {
			throw new StatusInvalidoException("Não é possivel cancelar uma tarefa concluida");
		}
		
		this.status = StatusTarefa.CANCELADA;
	}

	public abstract Tarefa gerarProximaOcorrencia();
	public abstract String getDetalhes();
}		