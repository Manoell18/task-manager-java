package exceptions;

public class UsuarioJaExisteException extends Exception {
	public UsuarioJaExisteException(String id) {
		super("Ja existe um usuario cadastrado com o ID: " + id);
	}
}