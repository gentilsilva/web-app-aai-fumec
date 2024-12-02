package br.com.back_end.aii.gerenciador_escolar.domain.universitario;

import br.com.back_end.aii.gerenciador_escolar.domain.usuario.Perfil;
import br.com.back_end.aii.gerenciador_escolar.domain.usuario.UsuarioService;
import br.com.back_end.aii.gerenciador_escolar.infra.exception.RegraDeNegocioException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class UniversitarioService {

    private final UniversitarioRepository universitarioRepository;
    private final UsuarioService usuarioService;

    public UniversitarioService(UniversitarioRepository universitarioRepository, UsuarioService usuarioService) {
        this.universitarioRepository = universitarioRepository;
        this.usuarioService = usuarioService;
    }

    public Page<DadosListagemUniversitario> listar(Pageable paginacao) {
        return universitarioRepository.findAllByAtivoTrue(paginacao).map(DadosListagemUniversitario::new);
    }

    public void cadastrarUniversitario(DadosCadastroUniversitario dadosCadastroUniversitario) {
        if(universitarioRepository.jaCadastrado(dadosCadastroUniversitario.id(), dadosCadastroUniversitario.cpf(),
                dadosCadastroUniversitario.email())) {
            throw new RegraDeNegocioException("E-mail ou CPF já cadastrado para outro universitario!");
        }

        if(dadosCadastroUniversitario.id() == null) {
            Universitario universitario = new Universitario(dadosCadastroUniversitario);
            String matricula = universitario.geradorMatricula();
            String emailUniversitario = universitario.geradorEmailUniversitario(matricula);
            universitario.setMatricula(matricula);
            universitario.setEmailUniversitario(emailUniversitario);

            Long id = usuarioService.salvarUsuario(universitario.getNome(), universitario.getEmailUniversitario(), universitario.getEmail(),
                    universitario.getCpf(), Perfil.UNIVERSITARIO);

            universitario.setId(id);

            universitarioRepository.save(universitario);

        }
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
