package ar.dev.maxisandoval.nextfix.controller;

import ar.dev.maxisandoval.nextfix.model.Usuario;
import ar.dev.maxisandoval.nextfix.service.CustomUserDetailsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.h2.engine.Mode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
@Slf4j
public class AuthController {

    private final CustomUserDetailsService customUserDetailsService;

    @GetMapping("/")
    public String redireccionarPaginaPrincipal() {
        log.info("---Entrando a root---");
        return "redirect:/peliculas";
    }

    @GetMapping("/login")
    public String login() {
        log.info("---Entrando a login---");
        return "login";
    }

    @GetMapping("/registro")
    public String mostrarFormularioRegistro(Model model) {
        log.info("---Entrando a registro---");
        model.addAttribute("usuario", new Usuario());
        return "registro";
    }

    @PostMapping("/registro")
    public String registrarUsuario(Usuario usuario) {
        customUserDetailsService.guardarUsuario(usuario);
        return "redirect:/login";
    }
}