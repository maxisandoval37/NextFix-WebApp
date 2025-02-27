package ar.dev.maxisandoval.nextfix.controller;

import ar.dev.maxisandoval.nextfix.model.Director;
import ar.dev.maxisandoval.nextfix.model.Pelicula;
import ar.dev.maxisandoval.nextfix.repository.UsuarioRepository;
import ar.dev.maxisandoval.nextfix.service.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@AllArgsConstructor
@Slf4j
public class PeliculaViewController {

    private final UsuarioRepository usuarioRepository;
    private final PeliculaService peliculaService;
    private final DirectorService directorService;
    private final PlataformaService plataformaService;
    private final CustomUserDetailsService customUserDetailsService;

    @GetMapping("/peliculas")
    public String listarPeliculas(Model model) {
        List<Pelicula> peliculas;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        log.info(username);
        Director director = usuarioRepository.findByUsername(username).getDirector();

        if (director != null) {
            peliculas = director.getPeliculasDirigidas();
        }
        else {
            peliculas = peliculaService.listarPeliculas();
        }

        mostrarRolesUsuarioActual();

        model.addAttribute("peliculas", peliculas);
        model.addAttribute("userService", customUserDetailsService);

        return "listaPeliculas";
    }

    private void mostrarRolesUsuarioActual() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        for (GrantedAuthority authority : authentication.getAuthorities()) {
            log.info("Rol actual: " + authority.getAuthority());
        }
    }

    @GetMapping("/agregarPelicula")
    public String mostrarFormularioNuevaPelicula(Model model) {
        model.addAttribute("plataformas", plataformaService.listarPlataformas());
        model.addAttribute("usuariosConDirector", customUserDetailsService.listarUsuariosRegistradosConDirectores());
        model.addAttribute("pelicula", new Pelicula());

        return "agregarPeliculaForm";
    }

    @PostMapping("/guardarPelicula")
    public String guardarPelicula(@ModelAttribute Pelicula pelicula, @RequestParam Long idDirector, @RequestParam(required = false) List<Long> idPlataformas) {
        peliculaService.guardarPelicula(pelicula, idDirector, idPlataformas);
        return "redirect:/peliculas";
    }

    @GetMapping("/actualizarPelicula/{id}")
    public String mostrarFormularioActualizarPelicula(@PathVariable Long id, Model model) {
        model.addAttribute("pelicula", peliculaService.obtenerPeliculaPorId(id));
        model.addAttribute("plataformas", plataformaService.listarPlataformas());
        model.addAttribute("usuariosConDirector", customUserDetailsService.listarUsuariosRegistradosConDirectores());

        return "actualizarPeliculaForm";
    }

    @PostMapping("/actualizarPelicula/{idPelicula}")
    public String actualizarPelicula(@PathVariable Long idPelicula, @ModelAttribute Pelicula peliculaActualizada,  @RequestParam Long idDirector, @RequestParam(required = false) List<Long> idPlataformas) {
        peliculaService.actualizarPelicula(idPelicula, peliculaActualizada, idDirector, idPlataformas);
        return "redirect:/peliculas";
    }

    @GetMapping("eliminarPelicula/{id}")
    public String eliminarPelicula(@PathVariable Long id) {
        peliculaService.eliminarPelicula(id);
        return "redirect:/peliculas";
    }
}