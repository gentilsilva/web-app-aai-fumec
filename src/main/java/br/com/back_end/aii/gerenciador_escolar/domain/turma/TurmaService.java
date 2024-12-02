package br.com.back_end.aii.gerenciador_escolar.domain.turma;

import br.com.back_end.aii.gerenciador_escolar.domain.materia.Materia;
import br.com.back_end.aii.gerenciador_escolar.domain.materia.MateriaRepository;
import br.com.back_end.aii.gerenciador_escolar.domain.professor.DadosCadastroProfessor;
import br.com.back_end.aii.gerenciador_escolar.domain.professor.Professor;
import br.com.back_end.aii.gerenciador_escolar.domain.professor.ProfessorRepository;
import br.com.back_end.aii.gerenciador_escolar.domain.usuario.Perfil;
import br.com.back_end.aii.gerenciador_escolar.domain.usuario.Usuario;
import br.com.back_end.aii.gerenciador_escolar.infra.exception.RegraDeNegocioException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TurmaService {

    private final TurmaRepository turmaRepository;
    private final MateriaRepository materiaRepository;
    private final ProfessorRepository professorRepository;

    public TurmaService(TurmaRepository turmaRepository, MateriaRepository materiaRepository, ProfessorRepository professorRepository) {
        this.turmaRepository = turmaRepository;
        this.materiaRepository = materiaRepository;
        this.professorRepository = professorRepository;
    }

    public Page<DadosListagemTurma> listar(Pageable paginacao, Usuario usuarioLogado) {
        if(usuarioLogado.getPerfil() == Perfil.GESTOR) {
            return turmaRepository.findAll(paginacao).map(DadosListagemTurma::new);
        }
        return turmaRepository.buscaPersonalizadaTurma(usuarioLogado.getId(), paginacao);
    }

    public void excluir(Long id) {
        materiaRepository.deleteById(id);
    }

    public void cadastrarTurma(DadosCadastroTurma dados) {
        Professor professor = professorRepository.findByIdAndAtivoTrue(dados.professor()).orElseThrow(
                () -> new RegraDeNegocioException("Professor não encontrado.")
        );
        Materia materia = materiaRepository.findById(dados.materia()).orElseThrow(
                () -> new RegraDeNegocioException("Materia não encontrada")
        );

        if(dados.id() == null) {
            Turma turma = new Turma(dados, professor, materia);
            turmaRepository.save(turma);
        }
    }

    public List<DadosListagemTurma> carregaTudo() {
        return turmaRepository.findAll().stream().map(DadosListagemTurma::new).toList();
    }
}
