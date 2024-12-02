package br.com.back_end.aii.gerenciador_escolar.controller;

import br.com.back_end.aii.gerenciador_escolar.domain.matricula.DadosCadastroMatricula;
import br.com.back_end.aii.gerenciador_escolar.domain.matricula.DadosListagemMatricula;
import br.com.back_end.aii.gerenciador_escolar.domain.matricula.MatriculaService;
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
@RequestMapping("/matriculas")
public class MatriculaController {

    private static final String PAGINA_LISTAGEM = "matriculas/listagem-matricula";
    private static final String PAGINA_CADASTRO = "matriculas/formulario-matricula";
    private static final String REDIRECT_LISTAGEM = "redirect:/matriculas?sucesso";

    private final TurmaService turmaService;
    private final MatriculaService matriculaService;

    public MatriculaController(TurmaService turmaService, MatriculaService matriculaService) {
        this.turmaService = turmaService;
        this.matriculaService = matriculaService;
    }

    @ModelAttribute("turmas")
    public List<DadosListagemTurma> turmas() {
        List<DadosListagemTurma> turmas = turmaService.carregaTudo();
        return turmas;
    }

    @GetMapping
    public String getPaginaListagem(@PageableDefault Pageable paginacao, Model model, @AuthenticationPrincipal Usuario usuarioLogado) {
        Page<DadosListagemMatricula> dadosListagemMatriculas = matriculaService.listar(paginacao, usuarioLogado);
        model.addAttribute("matriculas", dadosListagemMatriculas);
        return PAGINA_LISTAGEM;
    }

    @GetMapping("/formulario")
    public String getPaginaCadastro(Long id, Model model) {
        model.addAttribute("dados", new DadosCadastroMatricula(null, null));
        return PAGINA_CADASTRO;
    }

    @PostMapping
    public String cadastrar(@Valid @ModelAttribute("dados") DadosCadastroMatricula dados, BindingResult result, Model model, @AuthenticationPrincipal Usuario usuarioLogado) {
        if(result.hasErrors()) {
            model.addAttribute("dados", dados);
        }
        try {
            matriculaService.cadastrarMatricula(dados, usuarioLogado);
            return REDIRECT_LISTAGEM;
        } catch (RegraDeNegocioException e) {
            model.addAttribute("erro", e.getMessage());
            model.addAttribute("dados", dados);
            return PAGINA_CADASTRO;
        }
    }

    @DeleteMapping
    public String excluir(Long id) {
        matriculaService.excluir(id);
        return REDIRECT_LISTAGEM;
    }

}
