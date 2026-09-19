package exceptions;

public class TarefaNaoEncontradaException extends Exception {
	public TarefaNaoEncontradaException(String id) {
		super("Tarefa nao encontrada com o ID: " + id);
	}
}