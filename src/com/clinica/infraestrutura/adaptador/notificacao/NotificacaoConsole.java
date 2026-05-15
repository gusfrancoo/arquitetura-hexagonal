package com.clinica.infraestrutura.adaptador.notificacao;

import com.clinica.dominio.modelo.Animal;
import com.clinica.dominio.modelo.Consulta;
import com.clinica.dominio.porta.saida.PortaNotificacaoTutor;

import java.time.format.DateTimeFormatter;

public class NotificacaoConsole implements PortaNotificacaoTutor {
    private static final DateTimeFormatter DATA_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter HORA_FORMAT = DateTimeFormatter.ofPattern("HH:mm");

    @Override
    public void notificarAgendamento(String tutor, Animal animal, Consulta consulta) {
        String racaOuEspecie = animal.getRaca() != null && !animal.getRaca().trim().isEmpty()
                ? animal.getRaca()
                : animal.getEspecie();

        System.out.println("[AGENDAMENTO] Tutor: " + tutor
                + " | Animal: " + animal.getNome() + " (" + racaOuEspecie + ")");
        System.out.println("             Veterinario: " + consulta.getVeterinario().getNome()
                + " (" + consulta.getVeterinario().getEspecialidade() + ")");
        System.out.println("             Data: " + consulta.getData().format(DATA_FORMAT)
                + " as " + consulta.getHora().format(HORA_FORMAT)
                + " | Tipo: " + consulta.getTipo());
    }

    @Override
    public void notificarCancelamento(String tutor, Animal animal, String motivo) {
        System.out.println("[CANCELAMENTO] Tutor: " + tutor
                + " | Animal: " + animal.getNome()
                + " | Motivo: " + motivo);
    }
}
