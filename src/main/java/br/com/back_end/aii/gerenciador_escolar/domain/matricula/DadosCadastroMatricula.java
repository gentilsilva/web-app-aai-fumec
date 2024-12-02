package br.com.back_end.aii.gerenciador_escolar.domain.matricula;

import jakarta.validation.constraints.NotNull;

public record DadosCadastroMatricula(
        Long id,
        @NotNull
        Long turma
) {
}
