package models;

import java.time.LocalDate;

public class TarefaRecorrente extends Tarefa implements Notificavel {

	private final int intervaloDias;

	public TarefaRecorrente(String id, String titulo, String descricao, LocalDate prazo, Prioridade prioridade,
			Usuario responsavel, int intervaloDias) {
		super(id, titulo, descricao, prazo, prioridade, responsavel);
		this.intervaloDias = intervaloDias;
	}

	@Override
	public Tarefa gerarProximaOcorrencia() {
		if (getStatus() != StatusTarefa.CONCLUIDA) {
			return null;
		}
		LocalDate proximoPrazo = getPrazo().plusDays(intervaloDias);
		return new TarefaRecorrente(getId() + "-R", getTitulo(), getDescricao(), proximoPrazo, getPrioridade(),
				getResponsavel(), intervaloDias);
	}

	@Override
	public String getDetalhes() {
		return String.format(
				"[Recorrente a cada %d dias] %s | Próximo prazo: %s | Prioridade: %s | Status: %s | Responsavel: %s",
				intervaloDias, getTitulo(), getPrazo(), getPrioridade(), getStatus(), getResponsavel());
	}

	@Override
	public String gerarNotificacao() {
		return "Lembrete: a tarefa \"" + getTitulo() + "\" se repete a cada " + intervaloDias + " dias.";
	}

}