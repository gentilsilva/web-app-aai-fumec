package br.com.back_end.aii.gerenciador_escolar.controller;

import br.com.back_end.aii.gerenciador_escolar.domain.usuario.DadosAlteracaoSenha;
import br.com.back_end.aii.gerenciador_escolar.domain.usuario.Usuario;
import br.com.back_end.aii.gerenciador_escolar.domain.usuario.UsuarioService;
import br.com.back_end.aii.gerenciador_escolar.infra.exception.RegraDeNegocioException;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    private final UsuarioService usuarioService;
    private static final String FORMULARIO_ALTERACAO_SENHA = "autenticacao/formulario-alteracao-senha";

    public LoginController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/login")
    public String carregaPaginaLogin(){
        return "autenticacao/login";
    }

    @GetMapping("/alterar-senha")
    public String carregaPaginaAlteracao() {
        return "autenticacao/formulario-alteracao-senha";
    }

    @PostMapping("/alterar-senha")
    public String alterarSenha(@Valid @ModelAttribute("dados") DadosAlteracaoSenha dados, BindingResult result, Model model, @AuthenticationPrincipal Usuario usuarioLogado) {
        if (result.hasErrors()) {
            model.addAttribute("dados", dados);
            return FORMULARIO_ALTERACAO_SENHA;
        }

        try {
            usuarioService.alterarSenha(dados, usuarioLogado);
            return "redirect:home";
        } catch (RegraDeNegocioException e) {
            model.addAttribute("erro", e.getMessage());
            model.addAttribute("dados", dados);
            return FORMULARIO_ALTERACAO_SENHA;
        }
    }

}
