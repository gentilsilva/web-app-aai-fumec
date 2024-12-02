package br.com.back_end.aii.gerenciador_escolar.infra.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

public class TratadorDeException {

    @ExceptionHandler(NoSuchElementException.class)
    public String tratarErro404(Exception e) {
        System.out.println(e.getMessage());
        return "erro/404";
    }

    @ExceptionHandler(Exception.class)
    public String tratarErro500(Exception e) {
        System.out.println(e.getMessage());
        return "erro/500";
    }

}
