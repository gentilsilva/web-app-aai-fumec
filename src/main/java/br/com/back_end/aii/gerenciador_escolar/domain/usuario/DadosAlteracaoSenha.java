package br.com.back_end.aii.gerenciador_escolar.domain.usuario;

import jakarta.validation.constraints.NotBlank;

public record DadosAlteracaoSenha(
        @NotBlank
        String senhaAtual,
        @NotBlank
        String novaSenha,
        @NotBlank
        String novaSenhaConfirmacao

) {
}
