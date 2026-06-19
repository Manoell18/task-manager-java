package taskManager;

import java.util.Scanner;

public class Main {
	
	static Scanner leitor = new Scanner(System.in);
	
	static GerenciadorDeTarefas gerenciador;
	
	static int lerInteiro(String mensagem) {
	    System.out.println(mensagem);
	    int numero = leitor.nextInt();
	    leitor.nextLine();
	    return numero;
	}
	
	
	static String lerString(String mensagem) {
		System.out.println(mensagem);
		return leitor.nextLine();
	}
	
	
	static boolean adicionarTarefa() {
		if (gerenciador.estaCheio()) {
			System.out.println("A lista de tarefas esta lotada!");
			return false;
		}
		
		
		System.out.println("=== Adicionar Tarefa ===");
		String titulo = lerString("Digite o titulo da tarefa: ");
		String descricao = lerString("Informe a descricao da tarefa: ");
		String data = lerString("Informe a data da tarefa: ");
		
		gerenciador.criarNovaTarefa(titulo, descricao, data);
		
		System.out.println("Tarefa adicionada!");
		return true;
	}
	
	
	static boolean removerTarefa() {
		if (gerenciador.estaVazio()) {
			System.out.println("Nenhuma tarefa na lista!");
			return false;
		}
		
		System.out.println("=== Remover Tarefa ===");
		String titulo = lerString("Informe o titulo da tarefa: ");
		
		boolean resultado = gerenciador.removerTarefa(titulo);
		
		if (resultado) {
			System.out.println("Tarefa removida!");
			return true;
		}
		
		
		System.out.println("Tarefa não encontrada!");
		return false;
	}
	
	
	static boolean marcarTarefaConcluida() {
		if (gerenciador.estaVazio()) {
			System.out.println("Nenhuma tarefa na lista!");
			return false;
		}
		
	    System.out.println("=== Marcar Como Concluída ===");

	    String titulo = lerString("Informe o titulo da tarefa: ");

	    if (gerenciador.marcarTarefaConcluida(titulo)) {
	        System.out.println("Tarefa marcada como concluída!");
	        return true;
	        }
	    
	    System.out.println("Tarefa não encontrada!");
	    return false;
	}

	
	static boolean removerTarefasConcluidas() {
		if (gerenciador.estaVazio()) {
			System.out.println("Lista de tarefas está vazia!");
			return false;
		}
		
		gerenciador.removerTarefasConcluidas();
		System.out.println("Todas as tarefas concluidas foram removidas!");
		return true;
	}
	

	static void buscarTarefa() {
		if (gerenciador.estaVazio()) {
			System.out.println("Erro! Lista de tarefas esta vazia!");
			return;
		}
		
		
		System.out.println("=== Buscando Tarefa ===");
		String titulo = lerString("Informe o titulo da tarefa: ");
		
		Tarefa tarefa = gerenciador.buscarTarefa(titulo);
		
		if (tarefa != null) {
			System.out.println("Tarefa encontrada:\n");
	        System.out.println(tarefa);
	        return;
		}
		
		System.out.println("Tarefa não encontrada.");
	}
	
	static void listarTarefas() {
		gerenciador.listarTarefas();
	}
	
	static void menu() {
		System.out.println("\n=== Tarefas de " + gerenciador.nomeUsuario + " ===");
		System.out.println("1 - Cadastrar Tarefa");
		System.out.println("2 - Remover Tarefa");
		System.out.println("3 - Remover Tarefas concluidas: ");
		System.out.println("4 - Marcar Tarefa Como Concluida: ");
		System.out.println("5 - Buscar Tarefa: ");
		System.out.println("6 - Listar Tarefas:");
		System.out.println("0 - Encerrar Programa");
	}
	
	public static void main(String[] args) {
		int resposta = -1;
		
		String nomeUsuario = lerString("Informe Seu Nome: ");
		int capacidadeMax = lerInteiro("Informe a quantidade maxima de tarefas: ");
		gerenciador = new GerenciadorDeTarefas(capacidadeMax, nomeUsuario);
		
		
		do {
			menu();
			resposta = lerInteiro("Escolha Uma Opcao: ");
			
			switch(resposta) {
			case 1:
				adicionarTarefa();
				break;
			
			case 2:
				removerTarefa();
				break;
				
			case 3:
				removerTarefasConcluidas();
				break;
				
			case 4:
				marcarTarefaConcluida();
				break;
				
			case 5:
				buscarTarefa();
				break;
				
			case 6:
				listarTarefas();
				break;
			
			case 0:
				System.out.println("Programa Encerrado!");
				break;
				
				default:
					System.out.println("Opcão Invalida");
			}
		} while(resposta != 0);
		
		leitor.close();
	}
	
}