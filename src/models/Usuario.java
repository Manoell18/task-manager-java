package models;

public class Usuario implements Notificavel {
	private final String id;
	private String nome;
	private String email;
	
	public Usuario(String id, String nome, String email) {
		this.id = id;
		this.nome = nome;
		this.email = email;
	}
	public String getNome() {return nome;}
	public String getId() {return id;}
	public String getEmail() {return email;}
	
	public void setNome(String nome) {this.nome = nome;}
	public void setEmail(String email) {this.email = email;}
	
	@Override
	public String gerarNotificacao() {
		return "Ola " + nome + ", Voce tem tarefas pendentes";
	}
	
	@Override
	public String toString() {
		return nome + " (" + id + ")";
	}
}