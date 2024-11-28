package br.com.back_end.aii.gerenciador_escolar.domain.universitario;

public record DadosListagemUniversitario(
        Long id,
        String matricula,
        String nome,
        String emailUniversitario,
        String telefone,
        String cpf
) {
    public DadosListagemUniversitario(Universitario universitario) {
        this(universitario.getId(), universitario.getMatricula(), universitario.getNome(),
                universitario.getEmailUniversitario(),universitario.getTelefone(), universitario.getCpf());
    }
}
