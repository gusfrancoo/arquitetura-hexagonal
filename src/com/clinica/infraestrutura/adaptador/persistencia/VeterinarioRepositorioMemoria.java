package com.clinica.infraestrutura.adaptador.persistencia;

import com.clinica.dominio.modelo.Veterinario;
import com.clinica.dominio.porta.saida.PortaVeterinarioRepositorio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class VeterinarioRepositorioMemoria implements PortaVeterinarioRepositorio {
    private final Map<Long, Veterinario> store = new HashMap<>();
    private long proximoId = 1L;

    @Override
    public void salvar(Veterinario vet) {
        if (vet == null) {
            throw new IllegalArgumentException("Veterinario nao pode ser nulo.");
        }
        if (vet.getId() == null) {
            vet.atribuirId(proximoId++);
        }
        store.put(vet.getId(), vet);
    }

    @Override
    public Optional<Veterinario> buscarPorId(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Veterinario> buscarDisponiveis() {
        List<Veterinario> resultado = new ArrayList<>();
        for (Veterinario vet : store.values()) {
            if (vet.estaDisponivel()) {
                resultado.add(vet);
            }
        }
        return resultado;
    }

    @Override
    public List<Veterinario> buscarPorEspecialidade(String especialidade) {
        if (especialidade == null) {
            return new ArrayList<>();
        }
        List<Veterinario> resultado = new ArrayList<>();
        for (Veterinario vet : store.values()) {
            if (vet.getEspecialidade() != null && vet.getEspecialidade().equalsIgnoreCase(especialidade)) {
                resultado.add(vet);
            }
        }
        return resultado;
    }
}
