package br.com.back_end.aii.gerenciador_escolar.controller;

import br.com.back_end.aii.gerenciador_escolar.domain.materia.DadosCadastroMateria;
import br.com.back_end.aii.gerenciador_escolar.domain.materia.DadosListagemMateria;
import br.com.back_end.aii.gerenciador_escolar.domain.materia.MateriaService;
import br.com.back_end.aii.gerenciador_escolar.domain.formacao.Formacao;
import br.com.back_end.aii.gerenciador_escolar.infra.exception.RegraDeNegocioException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/materias")
public class MateriaController {

    private static final String PAGINA_LISTAGEM = "materias/listagem-materia";
    private static final String PAGINA_CADASTRO = "materias/formulario-materia";
    private static final String REDIRECT_LISTAGEM = "redirect:/materias?sucesso";

    private final MateriaService materiaService;

    public MateriaController(MateriaService materiaService) {
        this.materiaService = materiaService;
    }

    @ModelAttribute("formacoes")
    public Formacao[] especialidades() {
        return Formacao.values();
    }

    @GetMapping
    public String getPaginaListagem(@PageableDefault Pageable paginacao, Model model) {
        Page<DadosListagemMateria> dadosListagemMaterias = materiaService.listar(paginacao);
        model.addAttribute("materias", dadosListagemMaterias);
        return PAGINA_LISTAGEM;
    }

    @GetMapping("/formulario")
    public String getPaginaCadastro(Long id, Model model) {
        if (id != null) {
            model.addAttribute("dados", materiaService.carregarPorId(id));
        } else {
            model.addAttribute("dados", new DadosCadastroMateria(null, "", null, "", null, null));
        }
        return PAGINA_CADASTRO;
    }

    @PostMapping
    public String cadastrar(@Valid @ModelAttribute("dados") DadosCadastroMateria dados, BindingResult result, Model model) {
        if(result.hasErrors()) {
            model.addAttribute("dados", dados);
        }
        try {
            materiaService.cadastrarMateria(dados);
            return REDIRECT_LISTAGEM;
        } catch (RegraDeNegocioException e) {
            model.addAttribute("erro", e.getMessage());
            model.addAttribute("dados", dados);
            return PAGINA_CADASTRO;
        }
    }

    @GetMapping("{formacao}")
    @ResponseBody
    public List<DadosListagemMateria> listarMateriasPorFormacao(@PathVariable String formacao) {
        return materiaService.listarPorFormacao(Formacao.valueOf(formacao));
    }

    @DeleteMapping
    public String excluir(Long id) {
        materiaService.excluir(id);
        return REDIRECT_LISTAGEM;
    }

}
