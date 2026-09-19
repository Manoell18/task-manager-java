package sistema;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import exceptions.PrazoInvalidoException;
import exceptions.StatusInvalidoException;
import exceptions.TarefaNaoEncontradaException;
import exceptions.UsuarioJaExisteException;
import exceptions.UsuarioNaoEncontradoException;
import models.Notificavel;
import models.Prioridade;
import models.StatusTarefa;
import models.Tarefa;

public class SistemaConsole {
	private final Scanner scanner = new Scanner(System.in);
	private final ControladorTarefas controlador = new ControladorTarefas(new SistemaTarefas());

	public void iniciar() {
		int opcao = -1;
		do {
			exibirMenu();
			opcao = lerOpcao();
			processarOpcao(opcao);
		} while (opcao != 0);

		System.out.println("Encerrando o sistema...");
		scanner.close();
	}

	private void exibirMenu() {
		System.out.println("=== Gerenciador de Tarefas ===");
		System.out.println("1 - Cadastrar usuario");
		System.out.println("2 - Cadastrar tarefa simples");
		System.out.println("3 - Cadastrar tarefa recorrente");
		System.out.println("4 - Listar todas as tarefas");
		System.out.println("5 - Listar tarefa por status");
		System.out.println("6 - Listar tarefas de um usuario");
		System.out.println("7 - Concluir tarefa");
		System.out.println("8 - Cancelar tarefa");
		System.out.println("9 - Listar notificações");
		System.out.println("10 - Editar usuario");
		System.out.println("0 - Sair");
		System.out.println("Escolha uma opcao:");
	}

	private int lerOpcao() {
		try {
			int opcao = scanner.nextInt();
			scanner.nextLine();
			return opcao;

		} catch (InputMismatchException e) {
			System.out.println("Opcao invalida");
			scanner.nextLine();
			return -1;
		}
	}

	private void processarOpcao(int opcao) {
		switch (opcao) {
		case 1:
			cadastrarUsuario();
			break;
		case 2:
			cadastrarTarefaSimples();
			break;
		case 3:
			cadastrarTarefaRecorrente();
			break;
		case 4:
			listarTodas();
			break;
		case 5:
			listarPorStatus();
			break;
		case 6:
			listarTarefasPorResponsavel();
			break;
		case 7:
			concluirTarefa();
			break;
		case 8:
			cancelarTarefa();
			break;
		case 9:
			listarNotificaveis();
			break;
		case 10:
			editarUsuario();
			break;
		case 0:
			break;
		case -1:
			break;
		default:
			System.out.println("Opcao invalida.");
		}
	}

	private void cadastrarUsuario() {
		System.out.println("Id do usuario: ");
		String id = scanner.nextLine();
		System.out.println("Nome: ");
		String nome = scanner.nextLine();
		System.out.println("Email: ");
		String email = scanner.nextLine();

		try {
			controlador.cadastrarUsuario(id, nome, email);
			System.out.println("Usuário cadastrado!");
		} catch (UsuarioJaExisteException e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}

	private void editarUsuario() {
		System.out.println("Id do usuario: ");
		String id = scanner.nextLine();
		System.out.println("Novo nome (deixe em branco para manter o atual): ");
		String nome = scanner.nextLine();
		System.out.println("Novo email (deixe em branco para manter o atual): ");
		String email = scanner.nextLine();

		try {
			controlador.editarUsuario(id, nome, email);
			System.out.println("Usuario atualizado!");
		} catch (UsuarioNaoEncontradoException e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}

	private void cadastrarTarefaSimples() {
		try {
			System.out.println("Id da tarefa: ");
			String id = scanner.nextLine();
			System.out.println("Titulo: ");
			String titulo = scanner.nextLine();
			System.out.println("Descricao: ");
			String descricao = scanner.nextLine();
			LocalDate prazo = lerData();
			Prioridade prioridade = lerPrioridade();
			System.out.println("Id do responsavel: ");
			String idResponsavel = scanner.nextLine();

			controlador.cadastrarTarefaSimples(id, titulo, descricao, prazo, prioridade, idResponsavel);
			System.out.println("Tarefa simples cadastrada!");
		} catch (PrazoInvalidoException | UsuarioNaoEncontradoException e) {
			System.out.println("Erro: " + e.getMessage());
		} catch (DateTimeParseException e) {
			System.out.println("Data invalida. Use AAAA-MM-DD");
		}
	}

	private void cadastrarTarefaRecorrente() {
		try {
			System.out.println("Id da tarefa: ");
			String id = scanner.nextLine();
			System.out.println("Titulo: ");
			String titulo = scanner.nextLine();
			System.out.println("Descricao: ");
			String descricao = scanner.nextLine();
			LocalDate prazo = lerData();
			Prioridade prioridade = lerPrioridade();
			System.out.println("Id do responsavel: ");
			String idResponsavel = scanner.nextLine();
			System.out.println("Intervalo de dias: ");
			int intervaloDias = Integer.parseInt(scanner.nextLine().trim());

			controlador.cadastrarTarefaRecorrente(id, titulo, descricao, prazo, prioridade, idResponsavel,
					intervaloDias);
			System.out.println("Tarefa recorrente cadastrada!");
		} catch (PrazoInvalidoException | UsuarioNaoEncontradoException e) {
			System.out.println("Erro: " + e.getMessage());
		} catch (DateTimeParseException e) {
			System.out.println("Data invalida. Use AAAA-MM-DD");
		} catch (NumberFormatException e) {
			System.out.println("Intervalo de dias invalido. Digite um numero inteiro.");
		}
	}

	private void listarTodas() {
		List<Tarefa> tarefas = controlador.listarTodas();

		if (tarefas.isEmpty()) {
			System.out.println("Nenhuma tarefa cadastrada");
			return;
		}

		for (Tarefa tarefa : tarefas) {
			System.out.println(tarefa.getDetalhes());
		}
	}

	private void listarPorStatus() {
		System.out.println("Status (PENDENTE, CONCLUIDA, CANCELADA): ");

		try {
			StatusTarefa status = StatusTarefa.valueOf(scanner.nextLine().trim().toUpperCase());

			for (Tarefa tarefa : controlador.listarPorStatus(status)) {
				System.out.println(tarefa.getDetalhes());
			}
		} catch (IllegalArgumentException e) {
			System.out.println("Status invalido");
		}
	}

	private void listarTarefasPorResponsavel() {
		System.out.println("Id do responsavel: ");
		String id = scanner.nextLine();

		List<Tarefa> tarefas = controlador.listarTarefasPorResponsavel(id);

		if (tarefas.isEmpty()) {
			System.out.println("Nenhuma tarefa deste usuario");
			return;
		}

		for (Tarefa tarefa : tarefas) {
			System.out.println(tarefa.getDetalhes());
		}
	}

	private void concluirTarefa() {
		System.out.println("Id da tarefa: ");
		String id = scanner.nextLine();

		try {
			controlador.concluirTarefa(id);
			System.out.println("Tarefa concluida!");

		} catch (TarefaNaoEncontradaException | StatusInvalidoException e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}

	private void cancelarTarefa() {
		System.out.println("Id da tarefa: ");
		String id = scanner.nextLine();

		try {
			controlador.cancelarTarefa(id);
			System.out.println("Tarefa cancelada");
		} catch (TarefaNaoEncontradaException | StatusInvalidoException e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}

	private void listarNotificaveis() {
		List<Notificavel> notificaveis = controlador.listarNotificaveis();

		if (notificaveis.isEmpty()) {
			System.out.println("Nenhuma notificação.");
			return;
		}

		for (Notificavel notificavel : notificaveis) {
			System.out.println(notificavel.gerarNotificacao());
		}
	}

	private LocalDate lerData() {
		System.out.println("Prazo (AAAA-MM-DD)");
		return LocalDate.parse(scanner.nextLine().trim());
	}

	private Prioridade lerPrioridade() {
		System.out.println("Prioridade (BAIXA, MEDIA, ALTA)");
		try {
			return Prioridade.valueOf(scanner.nextLine().trim().toUpperCase());
		} catch (IllegalArgumentException e) {
			System.out.println("Prioridade invalida, usando MEDIA.");
			return Prioridade.MEDIA;
		}
	}
}
