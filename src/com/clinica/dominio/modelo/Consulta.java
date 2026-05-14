package com.clinica.dominio.modelo;

import java.time.LocalDate;
import java.time.LocalTime;

public class Consulta {
    private Long id;
    private final Animal animal;
    private final Veterinario veterinario;
    private final LocalDate data;
    private final LocalTime hora;
    private final TipoConsulta tipo;
    private SituacaoConsulta situacao;
    private String observacoes;

    public Consulta(Long id, Animal animal, Veterinario veterinario, LocalDate data, LocalTime hora, TipoConsulta tipo) {
        if (animal == null) {
            throw new IllegalArgumentException("Animal da consulta e obrigatorio.");
        }
        if (veterinario == null) {
            throw new IllegalArgumentException("Veterinario da consulta e obrigatorio.");
        }
        if (data == null) {
            throw new IllegalArgumentException("Data da consulta e obrigatoria.");
        }
        if (hora == null) {
            throw new IllegalArgumentException("Hora da consulta e obrigatoria.");
        }
        if (tipo == null) {
            throw new IllegalArgumentException("Tipo da consulta e obrigatorio.");
        }

        this.id = id;
        this.animal = animal;
        this.veterinario = veterinario;
        this.data = data;
        this.hora = hora;
        this.tipo = tipo;
        this.situacao = SituacaoConsulta.AGENDADA;
    }

    public void realizar(String observacoes) {
        if (this.situacao != SituacaoConsulta.AGENDADA) {
            throw new IllegalStateException(
                    "Apenas consultas AGENDADAS podem ser realizadas. Situacao atual: " + this.situacao);
        }
        this.situacao = SituacaoConsulta.REALIZADA;
        this.observacoes = observacoes;
    }

    public void cancelar() {
        if (this.situacao == SituacaoConsulta.CANCELADA) {
            throw new IllegalStateException("Consulta ja esta CANCELADA.");
        }
        this.situacao = SituacaoConsulta.CANCELADA;
    }

    public Long getId() {
        return id;
    }

    public Animal getAnimal() {
        return animal;
    }

    public Veterinario getVeterinario() {
        return veterinario;
    }

    public LocalDate getData() {
        return data;
    }

    public LocalTime getHora() {
        return hora;
    }

    public TipoConsulta getTipo() {
        return tipo;
    }

    public SituacaoConsulta getSituacao() {
        return situacao;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void atribuirId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Id da consulta deve ser maior que zero.");
        }
        this.id = id;
    }
}
