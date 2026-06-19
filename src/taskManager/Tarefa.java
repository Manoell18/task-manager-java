package taskManager;

public class Tarefa {
	String titulo;
	String descricao;
	String data;
	boolean concluida = false;
	
	
	Tarefa(String titulo, String descricao, String data) {
		this.titulo = titulo;
		this.descricao = descricao;
		this.data = data;
	}
	
	
	void marcarConcluida() {
		concluida = true;
	}
	
	
	public String toString() {
		String status;
		
		if (concluida) {
	        status = "Concluída";
	    } else {
	        status = "Pendente";
	    }
		return "=== " + titulo + " ===" + "\n" +
				descricao + "\n" +
				data + "\n" +
				status;
	}
}