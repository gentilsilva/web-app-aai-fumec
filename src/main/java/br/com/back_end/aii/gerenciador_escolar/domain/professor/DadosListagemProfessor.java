package br.com.back_end.aii.gerenciador_escolar.domain.professor;

public record DadosListagemProfessor(
        Long id,
        String matricula,
        String nome,
        String emailUniversitario,
        String cpf,
        Formacao formacao
) {
    public DadosListagemProfessor(Professor professor) {
        this(professor.getId(), professor.getMatricula(), professor.getNome(),
                professor.getEmailUniversitario(), professor.getCpf(), professor.getFormacao());
    }
}
