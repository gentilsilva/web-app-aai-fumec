package br.com.back_end.aii.gerenciador_escolar.controller;

import br.com.back_end.aii.gerenciador_escolar.domain.professor.DadosCadastroProfessor;
import br.com.back_end.aii.gerenciador_escolar.domain.professor.DadosListagemProfessor;
import br.com.back_end.aii.gerenciador_escolar.domain.professor.Formacao;
import br.com.back_end.aii.gerenciador_escolar.domain.professor.ProfessorService;
import br.com.back_end.aii.gerenciador_escolar.domain.universitario.DadosCadastroUniversitario;
import br.com.back_end.aii.gerenciador_escolar.infra.exception.RegraDeNegocioException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/professores")
public class ProfessorController {

    private static final String PAGINA_LISTAGEM = "professores/listagem-professor";
    private static final String PAGINA_CADASTRO = "professores/formulario-professor";
    private static final String REDIRECT_LISTAGEM = "redirect:/professores?sucesso";

    private final ProfessorService professorService;

    public ProfessorController(ProfessorService professorService) {
        this.professorService = professorService;
    }

    @ModelAttribute("formacoes")
    public Formacao[] especialidades() {
        return Formacao.values();
    }

    @GetMapping
    public String getPaginaListagem(@PageableDefault Pageable paginacao, Model model) {
        Page<DadosListagemProfessor> dadosListagemProfessorPage = professorService.listar(paginacao);
        model.addAttribute("professores", dadosListagemProfessorPage);
        return PAGINA_LISTAGEM;
    }

    @GetMapping("/formulario")
    public String getPaginaCadastro(Long id, Model model) {
        if (id != null) {
            model.addAttribute("dados", professorService.carregarPorId(id));
        } else {
            model.addAttribute("dados", new DadosCadastroProfessor(null, "", "", "", null));
        }
        return PAGINA_CADASTRO;
    }

    @PostMapping
    public String cadastrar(@Valid @ModelAttribute("dados")DadosCadastroProfessor dados, BindingResult result, Model model) {
        if(result.hasErrors()) {
            model.addAttribute("dados", dados);
        }
        try {
            professorService.cadastrarProfessor(dados);
            return REDIRECT_LISTAGEM;
        } catch (RegraDeNegocioException e) {
            model.addAttribute("erro", e.getMessage());
            model.addAttribute("dados", dados);
            return PAGINA_CADASTRO;
        }
    }

    @GetMapping("{formacao}")
    @ResponseBody
    public List<DadosListagemProfessor> listarProfessoresPorEspecialidade(@PathVariable String formacao) {
        return professorService.listarPorFormacao(Formacao.valueOf(formacao));
    }

    @DeleteMapping
    public String excluir(Long id) {
        professorService.excluir(id);
        return REDIRECT_LISTAGEM;
    }

}
