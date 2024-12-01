package br.com.back_end.aii.gerenciador_escolar.domain.universitario;

import br.com.back_end.aii.gerenciador_escolar.infra.exception.RegraDeNegocioException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class UniversitarioService {

    private final UniversitarioRepository universitarioRepository;

    public UniversitarioService(UniversitarioRepository universitarioRepository) {
        this.universitarioRepository = universitarioRepository;
    }

    public Universitario cadastrarUniversitario(DadosCadastroUniversitario dadosCadastroUniversitario) {
        Universitario universitario = new Universitario(dadosCadastroUniversitario);
        String matricula = universitario.geradorMatricula();
        String emailUniversitario = universitario.geradorEmailUniversitario(matricula);
        universitario.setMatricula(matricula);
        universitario.setEmailUniversitario(emailUniversitario);

        universitarioRepository.save(universitario);

        return universitario;
    }

    public Page<DadosListagemUniversitario> listar(Pageable paginacao) {
        return universitarioRepository.findAllByAtivoTrue(paginacao).map(DadosListagemUniversitario::new);
    }

    public DadosCadastroUniversitario carregarPorId(Long id) {
        Universitario universitario = universitarioRepository.findByIdAndAtivoTrue(id).orElseThrow();
        return new DadosCadastroUniversitario(universitario.getId(), universitario.getNome(),
                universitario.getEmailUniversitario(), universitario.getTelefone(), universitario.getCpf());
    }

    @Transactional
    public void excluir(Long id) {
        Universitario universitario = universitarioRepository.findByIdAndAtivoTrue(id).orElseThrow(
                () -> new RegraDeNegocioException("Usuário não encontrado")
        );
        universitario.desativarUniversitario();
    }
}
