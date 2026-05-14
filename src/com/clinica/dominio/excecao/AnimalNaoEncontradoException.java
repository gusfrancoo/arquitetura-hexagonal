package com.clinica.dominio.excecao;

public class AnimalNaoEncontradoException extends RuntimeException {
    public AnimalNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
