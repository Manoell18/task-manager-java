# Task Manager Java

Projeto desenvolvido para a disciplina de Linguagem de Programação II.

## Descrição

O Task Manager é um sistema de gerenciamento de tarefas desenvolvido em Java para execução no terminal. O programa permite ao usuário cadastrar, buscar, listar, concluir e remover tarefas, facilitando a organização de atividades do dia a dia.

As tarefas são armazenadas em um vetor com capacidade definida pelo usuário no início da execução.

## Funcionalidades

- Cadastrar novas tarefas
- Buscar tarefas pelo título
- Listar todas as tarefas cadastradas
- Marcar tarefas como concluídas
- Remover tarefas específicas
- Remover todas as tarefas concluídas
- Controle de capacidade máxima de tarefas
- Identificação do usuário responsável pela lista

## Estrutura do Projeto

### Tarefa
Classe responsável por representar uma tarefa.

**Atributos:**
- Título
- Descrição
- Data
- Status (Pendente ou Concluída)

**Métodos:**
- `marcarConcluida()`
- `toString()`

### GerenciadorDeTarefas
Classe responsável pelo gerenciamento da lista de tarefas.

**Métodos principais:**
- `criarNovaTarefa()`
- `removerTarefa()`
- `buscarTarefa()`
- `marcarTarefaConcluida()`
- `removerTarefasConcluidas()`
- `listarTarefas()`

### Main
Classe principal responsável pela interação com o usuário através de um menu no terminal.

## Menu do Sistema

```text
1 - Cadastrar Tarefa
2 - Remover Tarefa
3 - Remover Tarefas Concluídas
4 - Marcar Tarefa Como Concluída
5 - Buscar Tarefa
6 - Listar Tarefas
0 - Encerrar Programa
```

## Tecnologias Utilizadas

- Java
- Eclipse IDE
- Git
- GitHub

## Como Executar

1. Clone o repositório:

```bash
git clone https://github.com/SEU-USUARIO/task-manager-java.git
```

2. Abra o projeto no Eclipse.

3. Execute a classe `Main.java`.

4. Informe:
   - Seu nome
   - A quantidade máxima de tarefas

5. Utilize o menu para gerenciar suas tarefas.

## Integrantes

- Manoel Vieira

## Conceitos Aplicados

- Programação Orientada a Objetos (POO)
- Classes e Objetos
- Encapsulamento
- Vetores
- Métodos
- Estruturas de Repetição
- Estruturas Condicionais
- Manipulação de Strings
