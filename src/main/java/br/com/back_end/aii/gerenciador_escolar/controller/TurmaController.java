package br.com.back_end.aii.gerenciador_escolar.controller;

import br.com.back_end.aii.gerenciador_escolar.domain.materia.DadosListagemMateria;
import br.com.back_end.aii.gerenciador_escolar.domain.materia.MateriaService;
import br.com.back_end.aii.gerenciador_escolar.domain.professor.DadosCadastroProfessor;
import br.com.back_end.aii.gerenciador_escolar.domain.professor.DadosListagemProfessor;
import br.com.back_end.aii.gerenciador_escolar.domain.professor.ProfessorService;
import br.com.back_end.aii.gerenciador_escolar.domain.turma.DadosCadastroTurma;
import br.com.back_end.aii.gerenciador_escolar.domain.turma.DadosListagemTurma;
import br.com.back_end.aii.gerenciador_escolar.domain.turma.TurmaService;
import br.com.back_end.aii.gerenciador_escolar.domain.usuario.Usuario;
import br.com.back_end.aii.gerenciador_escolar.infra.exception.RegraDeNegocioException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/turmas")
public class TurmaController {

    private static final String PAGINA_LISTAGEM = "turmas/listagem-turma";
    private static final String PAGINA_CADASTRO = "turmas/formulario-turma";
    private static final String REDIRECT_LISTAGEM = "redirect:/turmas?sucesso";

    private final ProfessorService professorService;
    private final TurmaService turmaService;
    private final MateriaService materiaService;

    public TurmaController(ProfessorService professorService, TurmaService turmaService, MateriaService materiaService) {
        this.professorService = professorService;
        this.turmaService = turmaService;
        this.materiaService = materiaService;
    }

    @ModelAttribute("professores")
    public List<DadosListagemProfessor> professores() {
        List<DadosListagemProfessor> professores = professorService.carregaTudo();
        return professores;
    }

    @ModelAttribute("materias")
    public List<DadosListagemMateria> materias() {
        List<DadosListagemMateria> materias = materiaService.carregaTudo();
        return materias;
    }

    @GetMapping
    public String getPaginaListagem(@PageableDefault Pageable paginacao, Model model, @AuthenticationPrincipal Usuario usuarioLogado) {
        Page<DadosListagemTurma> dadosListagemTurmas = turmaService.listar(paginacao, usuarioLogado);
        model.addAttribute("turmas", dadosListagemTurmas);
        return PAGINA_LISTAGEM;
    }

    @GetMapping("/formulario")
    public String getPaginaCadastro(Long id, Model model) {
        if (id != null) {
            model.addAttribute("dados", turmaService.carregarPorId(id));
        } else {
            model.addAttribute("dados", new DadosCadastroTurma(null, "", null,null));
        }
        return PAGINA_CADASTRO;
    }

    @PostMapping
    public String cadastrar(@Valid @ModelAttribute("dados") DadosCadastroTurma dados, BindingResult result, Model model) {
        if(result.hasErrors()) {
            model.addAttribute("dados", dados);
        }
        try {
            turmaService.cadastrarTurma(dados);
            return REDIRECT_LISTAGEM;
        } catch (RegraDeNegocioException e) {
            model.addAttribute("erro", e.getMessage());
            model.addAttribute("dados", dados);
            return PAGINA_CADASTRO;
        }
    }

    @DeleteMapping
    public String excluir(Long id) {
        turmaService.excluir(id);
        return REDIRECT_LISTAGEM;
    }

}
