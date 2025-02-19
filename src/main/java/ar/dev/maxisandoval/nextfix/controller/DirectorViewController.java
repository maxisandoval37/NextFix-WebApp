package ar.dev.maxisandoval.nextfix.controller;

import ar.dev.maxisandoval.nextfix.service.DirectorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class DirectorViewController {

    private final DirectorService directorService;

    @GetMapping("/directores")
    public String listarDirectores(Model model) {
        model.addAttribute("directores", directorService.listarDirectores());

        return "listaDirectores";
    }
}