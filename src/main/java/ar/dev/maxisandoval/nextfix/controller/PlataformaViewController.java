package ar.dev.maxisandoval.nextfix.controller;

import ar.dev.maxisandoval.nextfix.model.Plataforma;
import ar.dev.maxisandoval.nextfix.service.PlataformaService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
public class PlataformaViewController {

    private final PlataformaService plataformaService;

    @GetMapping("/plataformas")
    public String listarPlataformas(Model model) {
        model.addAttribute("plataformas", plataformaService.listarPlataformas());

        return "listarPlataformas";
    }

    @GetMapping("/agregarPlataforma")
    public String mostrarFormularioNuevaPlataforma(Model model) {
        model.addAttribute("plataforma", new Plataforma());

        return "agregarPlataformaForm";
    }

    @PostMapping("/guardarPlataforma")
    public String guardarPlataforma(@ModelAttribute Plataforma plataforma) {
        plataformaService.guardarPlataforma(plataforma);
        return "redirect:/plataformas";
    }
}