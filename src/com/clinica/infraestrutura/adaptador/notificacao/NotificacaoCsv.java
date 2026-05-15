package com.clinica.infraestrutura.adaptador.notificacao;

import com.clinica.dominio.modelo.Animal;
import com.clinica.dominio.modelo.Consulta;
import com.clinica.dominio.porta.saida.PortaNotificacaoTutor;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;

public class NotificacaoCsv implements PortaNotificacaoTutor {
    private static final String CABECALHO = "timestamp,tipo_evento,tutor,animal,veterinario,data_consulta";
    private final Path caminhoArquivo;

    public NotificacaoCsv(String caminhoArquivo) {
        if (caminhoArquivo == null || caminhoArquivo.trim().isEmpty()) {
            throw new IllegalArgumentException("Caminho do arquivo CSV e obrigatorio.");
        }
        this.caminhoArquivo = Path.of(caminhoArquivo);
    }

    @Override
    public void notificarAgendamento(String tutor, Animal animal, Consulta consulta) {
        String dataConsulta = LocalDateTime.of(consulta.getData(), consulta.getHora()).toString();
        registrarLinha(
                LocalDateTime.now().toString(),
                "AGENDAMENTO",
                tutor,
                animal.getNome(),
                consulta.getVeterinario().getNome(),
                dataConsulta
        );
    }

    @Override
    public void notificarCancelamento(String tutor, Animal animal, String motivo) {
        registrarLinha(
                LocalDateTime.now().toString(),
                "CANCELAMENTO",
                tutor,
                animal.getNome(),
                "NAO_INFORMADO",
                "NAO_INFORMADO"
        );
    }

    private void registrarLinha(String timestamp, String tipoEvento, String tutor, String animal,
                                String veterinario, String dataConsulta) {
        garantirCabecalho();
        String linha = String.join(",",
                sanitizar(timestamp),
                sanitizar(tipoEvento),
                sanitizar(tutor),
                sanitizar(animal),
                sanitizar(veterinario),
                sanitizar(dataConsulta)
        );

        try (FileWriter writer = new FileWriter(caminhoArquivo.toFile(), true)) {
            writer.write(linha);
            writer.write(System.lineSeparator());
        } catch (IOException e) {
            throw new RuntimeException("Falha ao registrar notificacao em CSV.", e);
        }
    }

    private void garantirCabecalho() {
        try {
            boolean precisaCabecalho = !Files.exists(caminhoArquivo) || Files.size(caminhoArquivo) == 0;
            if (!precisaCabecalho) {
                return;
            }

            try (FileWriter writer = new FileWriter(caminhoArquivo.toFile(), true)) {
                writer.write(CABECALHO);
                writer.write(System.lineSeparator());
            }
        } catch (IOException e) {
            throw new RuntimeException("Falha ao inicializar arquivo CSV de notificacoes.", e);
        }
    }

    private String sanitizar(String valor) {
        if (valor == null) {
            return "NAO_INFORMADO";
        }
        return valor.replace(",", " ");
    }
}
