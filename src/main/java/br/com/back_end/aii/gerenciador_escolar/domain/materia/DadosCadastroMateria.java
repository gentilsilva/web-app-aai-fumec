package br.com.back_end.aii.gerenciador_escolar.domain.materia;

import br.com.back_end.aii.gerenciador_escolar.domain.formacao.Formacao;

public record DadosCadastroMateria(
        Long id,
        String nome,
        Integer semestre,
        String codigo,
        Integer horasSemestre,
        Formacao formacao
) {
}
