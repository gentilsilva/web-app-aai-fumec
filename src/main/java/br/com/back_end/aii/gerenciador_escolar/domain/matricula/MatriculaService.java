package br.com.back_end.aii.gerenciador_escolar.domain.matricula;

import br.com.back_end.aii.gerenciador_escolar.domain.turma.*;
import br.com.back_end.aii.gerenciador_escolar.domain.universitario.Universitario;
import br.com.back_end.aii.gerenciador_escolar.domain.universitario.UniversitarioRepository;
import br.com.back_end.aii.gerenciador_escolar.domain.usuario.Perfil;
import br.com.back_end.aii.gerenciador_escolar.domain.usuario.Usuario;
import br.com.back_end.aii.gerenciador_escolar.infra.exception.RegraDeNegocioException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MatriculaService {

    private final MatriculaRepository matriculaRepository;
    private final TurmaService turmaService;
    private final UniversitarioRepository universitarioRepository;
    private final TurmaRepository turmaRepository;


    public MatriculaService(MatriculaRepository matriculaRepository, TurmaService turmaService, UniversitarioRepository universitarioRepository, TurmaRepository turmaRepository) {
        this.matriculaRepository = matriculaRepository;
        this.turmaService = turmaService;
        this.universitarioRepository = universitarioRepository;
        this.turmaRepository = turmaRepository;
    }

    public Page<DadosListagemMatricula> listar(Pageable paginacao, Usuario usuarioLogado) {
        if(usuarioLogado.getPerfil() == Perfil.GESTOR) {
            return matriculaRepository.findAll(paginacao).map(DadosListagemMatricula::new);
        }
        return matriculaRepository.buscaPersonalizadaTurma(usuarioLogado.getId(), paginacao);
    }


    @Transactional
    public void excluir(Long id) {
        Matricula matricula = matriculaRepository.findById(id).orElseThrow(
                () -> new RegraDeNegocioException("Matricula não encontrada")
        );
        matricula.setStatus(Status.TRANCADO);
    }

    public void cadastrarMatricula(DadosCadastroMatricula dados, Usuario usuarioLogado) {
        Universitario universitario = universitarioRepository.findByIdAndAtivoTrue(usuarioLogado.getId()).orElseThrow(
                () -> new RegraDeNegocioException("Universitário não encontrado.")
        );
        Turma turma = turmaRepository.findById(dados.turma()).orElseThrow(
                () -> new RegraDeNegocioException("Turma não encontrada")
        );

        if(dados.id() == null) {
            Matricula matricula = new Matricula(universitario, turma);
            matriculaRepository.save(matricula);
        }
    }

}
