package com.clinica.dominio.modelo;

import java.time.LocalDate;
import java.time.Period;

public class Animal {
    private Long id;
    private final String nome;
    private final String especie;
    private final String raca;
    private final LocalDate dataNascimento;
    private final String tutor;

    public Animal(Long id, String nome, String especie, String raca, LocalDate dataNascimento, String tutor) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do animal e obrigatorio.");
        }
        if (especie == null || especie.trim().isEmpty()) {
            throw new IllegalArgumentException("Especie do animal e obrigatoria.");
        }
        if (tutor == null || tutor.trim().isEmpty()) {
            throw new IllegalArgumentException("Tutor do animal e obrigatorio.");
        }
        if (dataNascimento == null) {
            throw new IllegalArgumentException("Data de nascimento do animal e obrigatoria.");
        }

        this.id = id;
        this.nome = nome;
        this.especie = especie;
        this.raca = raca;
        this.dataNascimento = dataNascimento;
        this.tutor = tutor;
    }

    public int calcularIdadeEmAnos() {
        return Period.between(dataNascimento, LocalDate.now()).getYears();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEspecie() {
        return especie;
    }

    public String getRaca() {
        return raca;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public String getTutor() {
        return tutor;
    }

    public void atribuirId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Id do animal deve ser maior que zero.");
        }
        this.id = id;
    }
}
