package models;

import java.time.LocalDate;

public class TarefaSimples extends Tarefa {

	public TarefaSimples(String id, String titulo, String descricao, LocalDate prazo, Prioridade prioridade,
			Usuario responsavel) {
		super(id, titulo, descricao, prazo, prioridade, responsavel);
	}

	@Override
	public Tarefa gerarProximaOcorrencia() {
		return null;
	}

	@Override
	public String getDetalhes() {
		return String.format("[Simples] %s | Prazo: %s | Prioridade: %s | Status: %s | Responsável: %s", getTitulo(),
				getPrazo(), getPrioridade(), getStatus(), getResponsavel());
	}

}
