package br.com.back_end.aii.gerenciador_escolar.controller;

import br.com.back_end.aii.gerenciador_escolar.domain.usuario.DadosRecuperacaoConta;
import br.com.back_end.aii.gerenciador_escolar.domain.usuario.UsuarioService;
import br.com.back_end.aii.gerenciador_escolar.infra.exception.RegraDeNegocioException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EsqueciMinhaSenhaController {
    public static final String FORMULARIO_RECUPERACAO_SENHA = "autenticacao/formulario-recuperacao-senha";
    public static final String FORMULARIO_RECUPERACAO_CONTA = "autenticacao/formulario-recuperacao-conta";
    private final UsuarioService service;

    public EsqueciMinhaSenhaController(UsuarioService service) {
        this.service = service;
    }

    @GetMapping("/esqueci-minha-senha")
    public String carregarPaginaEsqueciMinhaSenha() {
        return FORMULARIO_RECUPERACAO_SENHA;
    }

    @PostMapping("/esqueci-minha-senha")
    public String enviarTokenEmail(@ModelAttribute("email") String email, Model model){
        try {
            service.enviarToken(email);
            return "redirect:esqueci-minha-senha?verificar";
        } catch (RegraDeNegocioException e){
            model.addAttribute("erro", e.getMessage());
            return FORMULARIO_RECUPERACAO_SENHA;
        }
    }

    @GetMapping("/recuperar-conta")
    public String carregarPaginaAlterarSenhaEsquecida(@RequestParam(name = "codigo", required = false) String codigo, Model model) {
        if(codigo != null)
            model.addAttribute("codigo", codigo);
        return FORMULARIO_RECUPERACAO_CONTA;
    }

    @PostMapping("/recuperar-conta")
    public String carregarPaginaAlterarSenhaEsquecida(@RequestParam(name = "codigo") String codigo, Model model, DadosRecuperacaoConta dados) {
        try {
            service.recuperarConta(codigo, dados);
            return "redirect:login";
        } catch (RegraDeNegocioException e){
            model.addAttribute("error", e.getMessage());
            return FORMULARIO_RECUPERACAO_CONTA;
        }
    }
}