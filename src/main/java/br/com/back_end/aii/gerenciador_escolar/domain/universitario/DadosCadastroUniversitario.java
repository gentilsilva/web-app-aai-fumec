package br.com.back_end.aii.gerenciador_escolar.domain.universitario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DadosCadastroUniversitario(
        Long id,
        @NotBlank
        String nome,
        @NotBlank
        String email,

        @NotBlank
        @Pattern(regexp = "(\\d{2}) \\d{5}\\-\\d{4}")
        String telefone,

        @NotBlank
        @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}")
        String cpf
) {
}
