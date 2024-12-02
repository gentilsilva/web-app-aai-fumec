package br.com.back_end.aii.gerenciador_escolar.domain.materia;

import br.com.back_end.aii.gerenciador_escolar.domain.formacao.Formacao;

public record DadosListagemMateria(
        Long id,
        String nome,
        Integer semestre,
        String codigo,
        Integer horasSemestre,
        Formacao formacao
) {
    public DadosListagemMateria(Materia materia) {
        this(materia.getId(), materia.getNome(), materia.getSemestre(), materia.getCodigo(), materia.getHorasSemestre(), materia.getFormacao());
    }
}
