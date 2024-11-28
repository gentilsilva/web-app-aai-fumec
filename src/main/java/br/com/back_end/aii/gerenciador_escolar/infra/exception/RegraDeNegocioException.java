package br.com.back_end.aii.gerenciador_escolar.infra.exception;

public class RegraDeNegocioException extends RuntimeException{

    public RegraDeNegocioException(String mensagem) {
        super(mensagem);
    }

}
