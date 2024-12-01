package br.com.back_end.aii.gerenciador_escolar.controller;

import br.com.back_end.aii.gerenciador_escolar.domain.universitario.DadosCadastroUniversitario;
import br.com.back_end.aii.gerenciador_escolar.domain.universitario.DadosListagemUniversitario;
import br.com.back_end.aii.gerenciador_escolar.domain.universitario.UniversitarioService;
import br.com.back_end.aii.gerenciador_escolar.infra.exception.RegraDeNegocioException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/universitarios")
public class UniversitarioController {

    private static final String PAGINA_LISTAGEM = "universitarios/listagem-universitario";
    private static final String PAGINA_CADASTRO = "universitarios/formulario-universitario";
    private static final String REDIRECT_LISTAGEM = "redirect:/universitarios?sucesso";

    private final UniversitarioService universitarioService;

    public UniversitarioController(UniversitarioService universitarioService) {
        this.universitarioService = universitarioService;
    }

    @GetMapping
    public String getPaginaListagem(@PageableDefault Pageable paginacao, Model model) {
        Page<DadosListagemUniversitario> dadosListagemUniversitario = universitarioService.listar(paginacao);
        model.addAttribute("universitarios", dadosListagemUniversitario);
        return PAGINA_LISTAGEM;
    }

    @GetMapping("/formulario")
    public String getPaginaCadastro(Long id, Model model) {
        if (id != null) {
            model.addAttribute("dados", universitarioService.carregarPorId(id));
        } else {
            model.addAttribute("dados", new DadosCadastroUniversitario(null, "", "", "", ""));
        }
        return PAGINA_CADASTRO;
    }

    @PostMapping
    public String cadastrar(@Valid @ModelAttribute("dados")DadosCadastroUniversitario dados, BindingResult result, Model model) {
        if(result.hasErrors()) {
            model.addAttribute("dados", dados);
        }
        try {
            universitarioService.cadastrarUniversitario(dados);
            return REDIRECT_LISTAGEM;
        } catch (RegraDeNegocioException e) {
            model.addAttribute("erro", e.getMessage());
            model.addAttribute("dados", dados);
            return PAGINA_CADASTRO;
        }
    }

    @DeleteMapping
    public String excluir(Long id) {
        universitarioService.excluir(id);
        return REDIRECT_LISTAGEM;
    }

}
