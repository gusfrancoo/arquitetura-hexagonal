package com.clinica.dominio.excecao;

public class VeterinarioIndisponivelException extends RuntimeException {
    public VeterinarioIndisponivelException(String mensagem) {
        super(mensagem);
    }
}
