package br.com.back_end.aii.gerenciador_escolar.domain.professor;

import br.com.back_end.aii.gerenciador_escolar.domain.formacao.Formacao;
import br.com.back_end.aii.gerenciador_escolar.domain.usuario.Perfil;
import br.com.back_end.aii.gerenciador_escolar.domain.usuario.UsuarioService;
import br.com.back_end.aii.gerenciador_escolar.infra.exception.RegraDeNegocioException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfessorService {
    private final ProfessorRepository professorRepository;
    private final UsuarioService usuarioService;

    public ProfessorService(ProfessorRepository professorRepository, UsuarioService usuarioService) {
        this.professorRepository = professorRepository;
        this.usuarioService = usuarioService;
    }

    public void cadastrarProfessor(DadosCadastroProfessor dadosCadastroProfessor) {
        if(professorRepository.jaCadastrado(dadosCadastroProfessor.id(), dadosCadastroProfessor.cpf(),
                dadosCadastroProfessor.email())) {
            throw new RegraDeNegocioException("E-mail ou CPF já cadastrado para outro professor!");
        }

        if(dadosCadastroProfessor.id() == null) {
            Professor professor = new Professor(dadosCadastroProfessor);
            String matricula = professor.geradorMatricula();
            String emailUniversitario = professor.geradorEmailUniversitario(matricula);
            professor.setMatricula(matricula);
            professor.setEmailUniversitario(emailUniversitario);

            Long id = usuarioService.salvarUsuario(professor.getNome(), professor.getEmailUniversitario(),
                    professor.getCpf(), professor.getEmail(),Perfil.PROFESSOR);

            professor.setId(id);

            professorRepository.save(professor);
        }
    }

    public Page<DadosListagemProfessor> listar(Pageable paginacao) {
        return professorRepository.findAllByAtivoTrue(paginacao).map(DadosListagemProfessor::new);
    }

    public DadosCadastroProfessor carregarPorId(Long id) {
        Professor professor = professorRepository.findByIdAndAtivoTrue(id).orElseThrow();
        return new DadosCadastroProfessor(professor.getId(), professor.getNome(),
                professor.getEmailUniversitario(), professor.getCpf(), professor.getFormacao());
    }

    @Transactional
    public void excluir(Long id) {
        Professor professor = professorRepository.findByIdAndAtivoTrue(id).orElseThrow(
                () -> new RegraDeNegocioException("Usuário não encontrado")
        );
        professor.desativarProfessor();
    }

    public List<DadosListagemProfessor> listarPorFormacao(Formacao formacao) {
        return professorRepository.findByFormacao(formacao).stream().map(DadosListagemProfessor::new).toList();
    }
}
