package sistema;

import java.time.LocalDate;
import java.util.List;

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

public class ControladorTarefas {

    private final SistemaTarefas sistemaTarefas;

    public ControladorTarefas(SistemaTarefas sistemaTarefas) {
        this.sistemaTarefas = sistemaTarefas;
    }

    public Usuario cadastrarUsuario(String id, String nome, String email) throws UsuarioJaExisteException {
        return sistemaTarefas.cadastrarUsuario(id, nome, email);
    }
    
    public void editarUsuario(String id, String novoNome, String novoEmail) throws UsuarioNaoEncontradoException {
        sistemaTarefas.editarUsuario(id, novoNome, novoEmail);
    }

    public Tarefa cadastrarTarefaSimples(String id, String titulo, String descricao, LocalDate prazo,
            Prioridade prioridade, String idResponsavel) throws PrazoInvalidoException, UsuarioNaoEncontradoException {
        return sistemaTarefas.cadastrarTarefaSimples(id, titulo, descricao, prazo, prioridade, idResponsavel);
    }

    public Tarefa cadastrarTarefaRecorrente(String id, String titulo, String descricao, LocalDate prazo,
            Prioridade prioridade, String idResponsavel, int intervaloDias) throws PrazoInvalidoException, UsuarioNaoEncontradoException {
        return sistemaTarefas.cadastrarTarefaRecorrente(id, titulo, descricao, prazo, prioridade, idResponsavel, intervaloDias);
    }
    
    public Tarefa buscarTarefa(String id) throws TarefaNaoEncontradaException {
    	return sistemaTarefas.buscarTarefa(id);
    }
    
    public void concluirTarefa(String id) throws TarefaNaoEncontradaException, StatusInvalidoException {
    	sistemaTarefas.concluirTarefa(id);
    }
    
    public void cancelarTarefa(String id) throws TarefaNaoEncontradaException, StatusInvalidoException {
    	sistemaTarefas.cancelarTarefa(id);
    }
    
    public List<Tarefa> listarTodas() {
    	return sistemaTarefas.listarTodas();
    }
    
    public List<Tarefa> listarPorStatus(StatusTarefa status) {
    	return sistemaTarefas.listarPorStatus(status);
    }
    
    public List<Tarefa> listarTarefasPorResponsavel(String idUsuario) {
    	return sistemaTarefas.listarTarefasPorResponsavel(idUsuario);
    }
    
    public List<Notificavel> listarNotificaveis() {
    	return sistemaTarefas.listarNotificaveis();
    }
}