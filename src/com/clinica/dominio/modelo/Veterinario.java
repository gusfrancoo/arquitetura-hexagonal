package com.clinica.dominio.modelo;

import com.clinica.dominio.excecao.VeterinarioIndisponivelException;

public class Veterinario {
    public enum SituacaoVeterinario {
        DISPONIVEL,
        OCUPADO
    }

    private Long id;
    private final String nome;
    private final String crmv;
    private final String especialidade;
    private SituacaoVeterinario situacao;

    public Veterinario(Long id, String nome, String crmv, String especialidade, SituacaoVeterinario situacao) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do veterinario e obrigatorio.");
        }
        if (crmv == null || crmv.trim().isEmpty()) {
            throw new IllegalArgumentException("CRMV do veterinario e obrigatorio.");
        }
        if (especialidade == null || especialidade.trim().isEmpty()) {
            throw new IllegalArgumentException("Especialidade do veterinario e obrigatoria.");
        }

        this.id = id;
        this.nome = nome;
        this.crmv = crmv;
        this.especialidade = especialidade;
        this.situacao = situacao == null ? SituacaoVeterinario.DISPONIVEL : situacao;
    }

    public boolean estaDisponivel() {
        return this.situacao == SituacaoVeterinario.DISPONIVEL;
    }

    public void ocupar() {
        if (!estaDisponivel()) {
            throw new VeterinarioIndisponivelException(
                    "Veterinario " + nome + " esta indisponivel para atendimento.");
        }
        this.situacao = SituacaoVeterinario.OCUPADO;
    }

    public void liberar() {
        this.situacao = SituacaoVeterinario.DISPONIVEL;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCrmv() {
        return crmv;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public SituacaoVeterinario getSituacao() {
        return situacao;
    }

    public void atribuirId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Id do veterinario deve ser maior que zero.");
        }
        this.id = id;
    }
}
