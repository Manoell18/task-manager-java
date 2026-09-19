package sistema;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import exceptions.PrazoInvalidoException;
import exceptions.StatusInvalidoException;
import exceptions.TarefaNaoEncontradaException;
import exceptions.UsuarioJaExisteException;
import exceptions.UsuarioNaoEncontradoException;
import models.Notificavel;
import models.Prioridade;
import models.StatusTarefa;
import models.Tarefa;
import models.TarefaRecorrente;
import models.TarefaSimples;
import models.Usuario;

public class SistemaTarefas {
	private final List<Tarefa> tarefas = new ArrayList<>();
	private final Map<String, Usuario> usuarios = new HashMap<>();
	private final List<Notificavel> notificaveis = new ArrayList<>();

	public Usuario cadastrarUsuario(String id, String nome, String email) throws UsuarioJaExisteException {
		if (usuarios.containsKey(id)) {
			throw new UsuarioJaExisteException(id);
		}

		Usuario usuario = new Usuario(id, nome, email);
		usuarios.put(id, usuario);
		notificaveis.add(usuario);
		return usuario;
	}

	private Usuario buscarUsuario(String id) {
		return usuarios.get(id);
	}

	public void editarUsuario(String id, String novoNome, String novoEmail) throws UsuarioNaoEncontradoException {
		Usuario usuario = usuarios.get(id);
		if (usuario == null) {
			throw new UsuarioNaoEncontradoException(id);
		}

		if (novoNome != null && !novoNome.isBlank()) {
			usuario.setNome(novoNome);
		}
		if (novoEmail != null && !novoEmail.isBlank()) {
			usuario.setEmail(novoEmail);
		}
	}

	public Tarefa cadastrarTarefaSimples(String id, String titulo, String descricao, LocalDate prazo,
			Prioridade prioridade, String idResponsavel) throws PrazoInvalidoException, UsuarioNaoEncontradoException {
		validarPrazo(prazo);

		Usuario usuarioResponsavel = buscarUsuario(idResponsavel);
		if (usuarioResponsavel == null) {
			throw new UsuarioNaoEncontradoException(idResponsavel);
		}

		TarefaSimples tarefa = new TarefaSimples(id, titulo, descricao, prazo, prioridade, usuarioResponsavel);
		tarefas.add(tarefa);
		return tarefa;
	}

	public Tarefa cadastrarTarefaRecorrente(String id, String titulo, String descricao, LocalDate prazo,
			Prioridade prioridade, String idResponsavel, int intervaloDias)
			throws PrazoInvalidoException, UsuarioNaoEncontradoException {
		validarPrazo(prazo);

		Usuario usuarioResponsavel = buscarUsuario(idResponsavel);
		if (usuarioResponsavel == null) {
			throw new UsuarioNaoEncontradoException(idResponsavel);
		}

		TarefaRecorrente tarefa = new TarefaRecorrente(id, titulo, descricao, prazo, prioridade, usuarioResponsavel,
				intervaloDias);
		tarefas.add(tarefa);
		notificaveis.add(tarefa);
		return tarefa;
	}

	private void validarPrazo(LocalDate prazo) throws PrazoInvalidoException {
		if (prazo == null || prazo.isBefore(LocalDate.now())) {
			throw new PrazoInvalidoException("O prazo informado nao pode estar no passado.");
		}
	}

	public Tarefa buscarTarefa(String id) throws TarefaNaoEncontradaException {
		for (Tarefa tarefa : tarefas) {
			if (tarefa.getId().equals(id)) {
				return tarefa;
			}
		}
		throw new TarefaNaoEncontradaException(id);
	}

	public void concluirTarefa(String id) throws TarefaNaoEncontradaException, StatusInvalidoException {
		Tarefa tarefa = buscarTarefa(id);
		tarefa.concluir();

		if (tarefa instanceof Notificavel notificavelConcluida) {
			notificaveis.remove(notificavelConcluida);
		}

		Tarefa proxima = tarefa.gerarProximaOcorrencia();

		if (proxima != null) {
			tarefas.add(proxima);
			if (proxima instanceof Notificavel notificavel) {
				notificaveis.add(notificavel);
			}
		}
	}

	public void cancelarTarefa(String id) throws TarefaNaoEncontradaException, StatusInvalidoException {
		Tarefa tarefa = buscarTarefa(id);
		tarefa.cancelar();

		if (tarefa instanceof Notificavel notificavel) {
			notificaveis.remove(notificavel);
		}
	}

	public List<Tarefa> listarTodas() {
		return new ArrayList<>(tarefas);
	}

	public List<Tarefa> listarPorStatus(StatusTarefa status) {
		List<Tarefa> resultado = new ArrayList<>();

		for (Tarefa tarefa : tarefas) {
			if (tarefa.getStatus() == status) {
				resultado.add(tarefa);
			}
		}

		return resultado;
	}

	public List<Tarefa> listarTarefasPorResponsavel(String idUsuario) {
		List<Tarefa> resultado = new ArrayList<>();

		for (Tarefa tarefa : tarefas) {
			if (tarefa.getResponsavel().getId().equals(idUsuario)) {
				resultado.add(tarefa);
			}
		}

		return resultado;
	}

	public List<Notificavel> listarNotificaveis() {
		return new ArrayList<>(notificaveis);
	}
}