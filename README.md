# Sistema de Gerenciamento de Clinica Veterinaria

Atividade pratica de Arquitetura Hexagonal com Java.

## Objetivo

Este projeto modela o agendamento de consultas veterinarias com foco em:

- baixo acoplamento;
- separacao entre dominio e infraestrutura;
- troca de adaptadores sem alterar regras de negocio.

## Arquitetura

- `dominio`: nucleo da aplicacao (entidades, regras e casos de uso).
- `dominio/porta/entrada`: contratos dos casos de uso expostos.
- `dominio/porta/saida`: contratos das dependencias externas do dominio.
- `infraestrutura/adaptador/persistencia`: implementacoes em memoria com `HashMap`.
- `infraestrutura/adaptador/notificacao`: implementacoes de notificacao via console e CSV.
- `apresentacao/Main.java`: composicao dos adaptadores e execucao do fluxo.

## Decisoes Arquiteturais

- O dominio nao importa classes de infraestrutura.
- O dominio nao importa classes de apresentacao.
- Entidades concentram regras de estado e validacoes.
- `ServicoAgendaConsulta` orquestra casos de uso e depende apenas de portas.
- Repositorios retornam `Optional<T>` em buscas por ID.
- Adaptadores de notificacao sao intercambiaveis (`Console` e `CSV`).

## Portas Implementadas

- `PortaAgendaConsulta`: casos de uso de agenda (agendar, realizar, cancelar, historico, agenda).
- `PortaAnimalRepositorio`: persistencia/consulta de animais.
- `PortaVeterinarioRepositorio`: persistencia/consulta de veterinarios.
- `PortaConsultaRepositorio`: persistencia/consulta de consultas.
- `PortaNotificacaoTutor`: notificacao de agendamento e cancelamento.

## Adaptadores Implementados

- `AnimalRepositorioMemoria`
- `VeterinarioRepositorioMemoria`
- `ConsultaRepositorioMemoria`
- `NotificacaoConsole`
- `NotificacaoCsv`

## Como Compilar

Comandos no PowerShell (a partir da raiz do projeto):

```powershell
Get-ChildItem -Recurse -Filter *.java src | ForEach-Object { $_.FullName } > sources.txt
javac -d out @sources.txt
```

## Como Executar

```powershell
java -cp out com.clinica.apresentacao.Main
```

## Testes Opcionais

Os testes com fakes eram opcionais no enunciado e nao foram implementados nesta versao.

## Verificacao de Regra Inviolavel (Dominio sem imports proibidos)

```powershell
$matches = Select-String -Path src\com\clinica\dominio\**\*.java `
  -Pattern "com\.clinica\.infraestrutura|com\.clinica\.apresentacao|org\.springframework|jakarta\.persistence|javax\.persistence|com\.fasterxml" `
  -AllMatches
if ($matches) { "ERRO: dependencia proibida encontrada" } else { "OK: dominio sem dependencias proibidas" }
```
