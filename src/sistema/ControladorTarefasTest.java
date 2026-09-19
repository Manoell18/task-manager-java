package sistema;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.Test;

import exceptions.PrazoInvalidoException;
import exceptions.StatusInvalidoException;
import exceptions.TarefaNaoEncontradaException;
import exceptions.UsuarioJaExisteException;
import exceptions.UsuarioNaoEncontradoException;
import models.Notificavel;
import models.Prioridade;
import models.StatusTarefa;
import models.Tarefa;
import models.Usuario;

public class ControladorTarefasTest {

	private static final LocalDate PRAZO_FUTURO = LocalDate.now().plusDays(10);

	@Test(expected = PrazoInvalidoException.class)
	public void testCadastrarTarefaSimplesComPrazoNoPassado() throws Exception {
		ControladorTarefas controlador = new ControladorTarefas(new SistemaTarefas());
		controlador.cadastrarUsuario("u1", "Maria", "maria@teste.com");

		LocalDate ontem = LocalDate.now().minusDays(1);
		controlador.cadastrarTarefaSimples("t1", "Titulo", "Descricao", ontem, Prioridade.BAIXA, "u1");
	}

	@Test(expected = UsuarioNaoEncontradoException.class)
	public void testCadastrarTarefaComResponsavelInexistente() throws Exception {
		ControladorTarefas controlador = new ControladorTarefas(new SistemaTarefas());

		controlador.cadastrarTarefaSimples("t1", "Titulo", "Descricao", PRAZO_FUTURO, Prioridade.BAIXA, "naoexiste");
	}

	@Test(expected = UsuarioJaExisteException.class)
	public void testCadastrarUsuarioComCodigoDuplicado() throws Exception {
		ControladorTarefas controlador = new ControladorTarefas(new SistemaTarefas());

		controlador.cadastrarUsuario("u1", "Maria", "maria@teste.com");
		controlador.cadastrarUsuario("u1", "Outro Nome", "outro@teste.com");
	}

	@Test(expected = UsuarioNaoEncontradoException.class)
	public void testEditarUsuarioInexistente() throws Exception {
		ControladorTarefas controlador = new ControladorTarefas(new SistemaTarefas());

		controlador.editarUsuario("naoexiste", "Nome", "email@teste.com");
	}

	@Test(expected = TarefaNaoEncontradaException.class)
	public void testConsultarTarefaInexistente() throws Exception {
		ControladorTarefas controlador = new ControladorTarefas(new SistemaTarefas());

		controlador.buscarTarefa("naoexiste");
	}

	@Test(expected = StatusInvalidoException.class)
	public void testConcluirTarefaJaConcluida() throws Exception {
		ControladorTarefas controlador = new ControladorTarefas(new SistemaTarefas());
		controlador.cadastrarUsuario("u1", "Maria", "maria@teste.com");
		controlador.cadastrarTarefaSimples("t1", "Titulo", "Descricao", PRAZO_FUTURO, Prioridade.BAIXA, "u1");

		controlador.concluirTarefa("t1");
		controlador.concluirTarefa("t1");
	}

	@Test(expected = StatusInvalidoException.class)
	public void testCancelarTarefaJaConcluida() throws Exception {
		ControladorTarefas controlador = new ControladorTarefas(new SistemaTarefas());
		controlador.cadastrarUsuario("u1", "Maria", "maria@teste.com");
		controlador.cadastrarTarefaSimples("t1", "Titulo", "Descricao", PRAZO_FUTURO, Prioridade.BAIXA, "u1");

		controlador.concluirTarefa("t1");
		controlador.cancelarTarefa("t1");
	}

	@Test
	public void testCadastrarUsuarioValido() throws Exception {
		ControladorTarefas controlador = new ControladorTarefas(new SistemaTarefas());

		Usuario usuario = controlador.cadastrarUsuario("u1", "Maria", "maria@teste.com");

		assertNotNull(usuario);
		assertEquals("Maria", usuario.getNome());
	}

	@Test
	public void testCadastrarTarefaSimplesValida() throws Exception {
		ControladorTarefas controlador = new ControladorTarefas(new SistemaTarefas());
		controlador.cadastrarUsuario("u1", "Maria", "maria@teste.com");

		Tarefa tarefa = controlador.cadastrarTarefaSimples("t1", "Estudar Java", "Revisar POO", PRAZO_FUTURO,
				Prioridade.ALTA, "u1");

		assertEquals(StatusTarefa.PENDENTE, tarefa.getStatus());
		assertEquals(1, controlador.listarTodas().size());
	}

	@Test
	public void testCadastrarTarefaRecorrenteValida() throws Exception {
		ControladorTarefas controlador = new ControladorTarefas(new SistemaTarefas());
		controlador.cadastrarUsuario("u1", "Maria", "maria@teste.com");

		Tarefa tarefa = controlador.cadastrarTarefaRecorrente("t1", "Regar planta", "Descricao", PRAZO_FUTURO,
				Prioridade.MEDIA, "u1", 7);

		assertEquals(StatusTarefa.PENDENTE, tarefa.getStatus());
		assertEquals(1, controlador.listarTodas().size());
	}

	@Test
	public void testListarTarefasSemCadastros() {
		ControladorTarefas controlador = new ControladorTarefas(new SistemaTarefas());

		List<Tarefa> tarefas = controlador.listarTodas();

		assertTrue(tarefas.isEmpty());
	}

	@Test
	public void testListarTodasAsTarefas() throws Exception {
		ControladorTarefas controlador = new ControladorTarefas(new SistemaTarefas());
		controlador.cadastrarUsuario("u1", "Maria", "maria@teste.com");
		controlador.cadastrarTarefaSimples("t1", "Titulo 1", "Descricao", PRAZO_FUTURO, Prioridade.BAIXA, "u1");
		controlador.cadastrarTarefaRecorrente("t2", "Titulo 2", "Descricao", PRAZO_FUTURO, Prioridade.ALTA, "u1", 5);

		List<Tarefa> tarefas = controlador.listarTodas();

		assertEquals(2, tarefas.size());
	}

	@Test
	public void testConsultarTarefaExistente() throws Exception {
		ControladorTarefas controlador = new ControladorTarefas(new SistemaTarefas());
		controlador.cadastrarUsuario("u1", "Maria", "maria@teste.com");
		controlador.cadastrarTarefaSimples("t1", "Titulo", "Descricao", PRAZO_FUTURO, Prioridade.BAIXA, "u1");

		Tarefa tarefa = controlador.buscarTarefa("t1");

		assertEquals("t1", tarefa.getId());
		assertEquals("Titulo", tarefa.getTitulo());
	}

	@Test
	public void testConcluirTarefaAtualizaStatus() throws Exception {
		ControladorTarefas controlador = new ControladorTarefas(new SistemaTarefas());
		controlador.cadastrarUsuario("u1", "Maria", "maria@teste.com");
		controlador.cadastrarTarefaSimples("t1", "Titulo", "Descricao", PRAZO_FUTURO, Prioridade.BAIXA, "u1");

		controlador.concluirTarefa("t1");

		assertEquals(StatusTarefa.CONCLUIDA, controlador.buscarTarefa("t1").getStatus());
	}

	@Test
	public void testConcluirTarefaRecorrenteGeraProximaOcorrencia() throws Exception {
		ControladorTarefas controlador = new ControladorTarefas(new SistemaTarefas());
		controlador.cadastrarUsuario("u1", "Maria", "maria@teste.com");
		controlador.cadastrarTarefaRecorrente("t1", "Regar planta", "Descricao", PRAZO_FUTURO, Prioridade.MEDIA,
				"u1", 7);

		controlador.concluirTarefa("t1");
		Tarefa proxima = controlador.buscarTarefa("t1-R");

		assertEquals(PRAZO_FUTURO.plusDays(7), proxima.getPrazo());
		assertEquals(StatusTarefa.PENDENTE, proxima.getStatus());
	}

	@Test
	public void testListarNotificaveisContemUsuarioETarefaRecorrente() throws Exception {
		ControladorTarefas controlador = new ControladorTarefas(new SistemaTarefas());
		controlador.cadastrarUsuario("u1", "Maria", "maria@teste.com");
		controlador.cadastrarTarefaRecorrente("t1", "Regar planta", "Descricao", PRAZO_FUTURO, Prioridade.MEDIA,
				"u1", 7);
		controlador.cadastrarTarefaSimples("t2", "Titulo simples", "Descricao", PRAZO_FUTURO, Prioridade.BAIXA, "u1");

		List<Notificavel> notificaveis = controlador.listarNotificaveis();

		assertEquals(2, notificaveis.size());
	}

	@Test
	public void testTarefaConcluidaSaiDasNotificacoes() throws Exception {
		ControladorTarefas controlador = new ControladorTarefas(new SistemaTarefas());
		controlador.cadastrarUsuario("u1", "Maria", "maria@teste.com");
		controlador.cadastrarTarefaRecorrente("t1", "Regar planta", "Descricao", PRAZO_FUTURO, Prioridade.MEDIA,
				"u1", 7);

		controlador.concluirTarefa("t1");

		long comTitulo = controlador.listarNotificaveis().stream()
				.filter(n -> n.gerarNotificacao().contains("Regar planta")).count();

		assertEquals(1, comTitulo);
	}
}