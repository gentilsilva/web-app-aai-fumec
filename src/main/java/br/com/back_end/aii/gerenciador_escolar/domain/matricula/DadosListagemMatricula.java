package br.com.back_end.aii.gerenciador_escolar.domain.matricula;

import br.com.back_end.aii.gerenciador_escolar.domain.turma.Turma;
import br.com.back_end.aii.gerenciador_escolar.domain.universitario.Universitario;

import java.time.LocalDate;

public record DadosListagemMatricula(
    Long id,
    Universitario universitario,
    Turma turma,
    LocalDate dataMatricula,
    Status status
){
    public DadosListagemMatricula(Matricula matricula) {
        this(matricula.getId(), matricula.getUniversitario(), matricula.getTurma(), matricula.getDataMatricula(), matricula.getStatus());
    }
}
