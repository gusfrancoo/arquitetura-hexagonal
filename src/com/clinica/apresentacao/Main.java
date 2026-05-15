package com.clinica.apresentacao;

import com.clinica.dominio.modelo.Animal;
import com.clinica.dominio.modelo.Consulta;
import com.clinica.dominio.modelo.TipoConsulta;
import com.clinica.dominio.modelo.Veterinario;
import com.clinica.dominio.porta.entrada.PortaAgendaConsulta;
import com.clinica.dominio.porta.saida.PortaAnimalRepositorio;
import com.clinica.dominio.porta.saida.PortaConsultaRepositorio;
import com.clinica.dominio.porta.saida.PortaNotificacaoTutor;
import com.clinica.dominio.porta.saida.PortaVeterinarioRepositorio;
import com.clinica.dominio.servico.ServicoAgendaConsulta;
import com.clinica.infraestrutura.adaptador.notificacao.NotificacaoConsole;
import com.clinica.infraestrutura.adaptador.notificacao.NotificacaoCsv;
import com.clinica.infraestrutura.adaptador.persistencia.AnimalRepositorioMemoria;
import com.clinica.infraestrutura.adaptador.persistencia.ConsultaRepositorioMemoria;
import com.clinica.infraestrutura.adaptador.persistencia.VeterinarioRepositorioMemoria;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Main {
    private static final DateTimeFormatter DATA_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter HORA_FORMAT = DateTimeFormatter.ofPattern("HH:mm");

    public static void main(String[] args) {
        PortaAnimalRepositorio animais = new AnimalRepositorioMemoria();
        PortaVeterinarioRepositorio vets = new VeterinarioRepositorioMemoria();
        PortaConsultaRepositorio consultas = new ConsultaRepositorioMemoria();

        PortaNotificacaoTutor notif = new NotificacaoConsole();
        PortaAgendaConsulta agenda = new ServicoAgendaConsulta(animais, vets, consultas, notif);

        PortaNotificacaoTutor notifCsv = new NotificacaoCsv("notificacoes.csv");
        PortaAgendaConsulta agendaCsv = new ServicoAgendaConsulta(animais, vets, consultas, notifCsv);

        Animal thor = new Animal(null, "Thor", "Cachorro", "Golden Retriever",
                LocalDate.of(2020, 5, 10), "Joao Silva");
        Animal luna = new Animal(null, "Luna", "Gato", "Siames",
                LocalDate.of(2021, 3, 20), "Maria Souza");
        animais.salvar(thor);
        animais.salvar(luna);

        Veterinario draBeatriz = new Veterinario(null, "Dra. Beatriz", "CRMV-GO 12345",
                "Clinica Geral", Veterinario.SituacaoVeterinario.DISPONIVEL);
        Veterinario drMarcos = new Veterinario(null, "Dr. Marcos", "CRMV-GO 67890",
                "Emergencia", Veterinario.SituacaoVeterinario.DISPONIVEL);
        vets.salvar(draBeatriz);
        vets.salvar(drMarcos);

        Consulta consultaRotina = agenda.agendarConsulta(
                thor.getId(),
                draBeatriz.getId(),
                LocalDate.of(2025, 7, 15),
                LocalTime.of(14, 30),
                TipoConsulta.ROTINA
        );

        Consulta consultaEmergencia = agendaCsv.agendarConsulta(
                luna.getId(),
                drMarcos.getId(),
                LocalDate.of(2025, 7, 15),
                LocalTime.of(16, 0),
                TipoConsulta.EMERGENCIA
        );

        Consulta consultaRealizada = agenda.realizarConsulta(
                consultaRotina.getId(),
                "Exame de rotina sem alteracoes."
        );
        System.out.println("[CONSULTA REALIZADA] Animal: " + consultaRealizada.getAnimal().getNome()
                + " | Obs.: " + consultaRealizada.getObservacoes());

        agenda.cancelarConsulta(consultaEmergencia.getId());
        System.out.println("Veterinario " + drMarcos.getNome() + " disponivel apos cancelamento: "
                + drMarcos.estaDisponivel());

        System.out.println();
        System.out.println("=== Historico de Thor ===");
        List<Consulta> historicoThor = agenda.obterHistoricoAnimal(thor.getId());
        for (Consulta consulta : historicoThor) {
            System.out.println(formatarConsulta(consulta));
        }

        System.out.println();
        System.out.println("=== Agenda de Dra. Beatriz ===");
        List<Consulta> agendaBeatriz = agenda.obterAgendaVeterinario(draBeatriz.getId());
        for (Consulta consulta : agendaBeatriz) {
            System.out.println(formatarConsulta(consulta));
        }
    }

    private static String formatarConsulta(Consulta consulta) {
        return "Consulta #" + consulta.getId()
                + " - " + consulta.getData().format(DATA_FORMAT)
                + " " + consulta.getHora().format(HORA_FORMAT)
                + " | " + consulta.getTipo()
                + " | " + consulta.getSituacao();
    }
}
