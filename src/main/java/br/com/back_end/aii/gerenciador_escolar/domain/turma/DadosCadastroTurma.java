package br.com.back_end.aii.gerenciador_escolar.domain.turma;

import br.com.back_end.aii.gerenciador_escolar.domain.professor.Professor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCadastroTurma(
        Long id,
        @NotBlank
        String nome,
        @NotNull
        Long professor,
        @NotNull
        Long materia

) {
}
