package br.com.back_end.aii.gerenciador_escolar.domain.turma;

import br.com.back_end.aii.gerenciador_escolar.domain.materia.Materia;
import br.com.back_end.aii.gerenciador_escolar.domain.professor.Professor;

public record DadosListagemTurma(
        Long id,
        Professor professor,
        String nome,
        Materia materia
) {
    public DadosListagemTurma(Turma turma) {
        this(turma.getId(), turma.getProfessor(), turma.getNome(), turma.getMateria());
    }

}
