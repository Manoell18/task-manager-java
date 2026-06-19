package taskManager;

public class GerenciadorDeTarefas {
	Tarefa[] tarefas;
	String nomeUsuario;
	int quantidadeAtual;
	
	
	GerenciadorDeTarefas(int capacidadeMax, String nomeUsuario) {
		this.tarefas = new Tarefa[capacidadeMax];
		this.nomeUsuario = nomeUsuario;
		this.quantidadeAtual = 0;
	}
	
	
	boolean criarNovaTarefa(String titulo, String descricao, String data) {
		if (quantidadeAtual < tarefas.length) {
			tarefas[quantidadeAtual] = new Tarefa(titulo, descricao, data);
			
			quantidadeAtual++;
			
			return true;
		}
		
		return false;
	}
	
	
	boolean removerTarefa(String titulo) {
		for (int i = 0; i < quantidadeAtual; i++) {
			if (tarefas[i].titulo.equalsIgnoreCase(titulo)) {
				for (int j = i; j < quantidadeAtual - 1; j++) {
					tarefas[j] = tarefas[j + 1];
				}
				
				tarefas[quantidadeAtual - 1] = null;
				
				quantidadeAtual--;
				
				return true;
			}
		}
		
		return false;
	}
	
	
	Tarefa buscarTarefa(String titulo) {
		for (int i = 0; i < quantidadeAtual; i++) {
			if (tarefas[i].titulo.equalsIgnoreCase(titulo)) {
				return tarefas[i];
			}
		}
		
		return null;
	}
	
	
	boolean marcarTarefaConcluida(String titulo) {
	    Tarefa tarefa = buscarTarefa(titulo);

	    if (tarefa != null) {
	        tarefa.marcarConcluida();
	        return true;
	    }

	    return false;
	}
	
	
	boolean removerTarefasConcluidas() {
		
		boolean removeuAlguma = false;
		
		for (int i = quantidadeAtual - 1; i >= 0; i--) {
			if (tarefas[i].concluida) {
				removerTarefa(tarefas[i].titulo);
				removeuAlguma = true;
			}
		}
		
		return removeuAlguma;
	}
	
	
	boolean estaCheio() {
	    return quantidadeAtual >= tarefas.length;
	}
	
	
	boolean estaVazio() {
		return quantidadeAtual == 0;
	}
	
	
	void listarTarefas() {
	    if (quantidadeAtual == 0) {
	        System.out.println("Nenhuma Tarefa Na Lista!");
	        return;
	    }
	    
	    for (int i = 0; i < quantidadeAtual; i++) {
	        System.out.println(tarefas[i]);
	    }
	}
}