package br.com.back_end.aii.gerenciador_escolar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/", "/index", "home"})
public class IndexController {

    @GetMapping
    public String indexPage(Model model) {
        return "index";
    }

}
