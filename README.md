# Task Manager Java

Projeto desenvolvido para a disciplina de Linguagem de Programação II.

## Sobre

Sistema de gerenciamento de tarefas executado pelo terminal. Esta versão representa a evolução do projeto para a segunda unidade, com uma estrutura orientada a objetos mais completa.

## Funcionalidades

- Cadastro e edição de usuários
- Cadastro de tarefas simples
- Cadastro de tarefas recorrentes
- Associação de tarefas a usuários responsáveis
- Listagem de todas as tarefas
- Listagem por status
- Listagem por responsável
- Conclusão e cancelamento de tarefas
- Geração de próximas ocorrências para tarefas recorrentes
- Sistema de notificações
- Tratamento de exceções específicas
- Testes unitários com JUnit

## Estrutura

- `models/` — classes de domínio, enums e interface `Notificavel`
- `exceptions/` — exceções específicas do sistema
- `sistema/` — regras do sistema, controlador, interface de terminal e testes

## Conceitos de POO aplicados

- Encapsulamento
- Herança
- Classe abstrata
- Polimorfismo
- Interface
- Enum
- Coleções (`List` e `Map`)
- Exceções personalizadas
- Testes unitários

## Execução

A classe principal é:

`sistema.Main`

O projeto utiliza recursos disponíveis a partir do Java 11, como `String.isBlank()`.

## Autor

Manoel Vieira
