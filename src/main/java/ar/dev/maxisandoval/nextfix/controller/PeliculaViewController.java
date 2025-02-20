package ar.dev.maxisandoval.nextfix.controller;

import ar.dev.maxisandoval.nextfix.model.Pelicula;
import ar.dev.maxisandoval.nextfix.service.DirectorService;
import ar.dev.maxisandoval.nextfix.service.PeliculaService;
import ar.dev.maxisandoval.nextfix.service.PlataformaService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@AllArgsConstructor
@Slf4j
public class PeliculaViewController {

    private final PeliculaService peliculaService;
    private final DirectorService directorService;
    private final PlataformaService plataformaService;

    @GetMapping("/peliculas")
    public String listarPeliculas(Model model) {
        model.addAttribute("peliculas", peliculaService.listarPeliculas());
        return "listaPeliculas";
    }

    @GetMapping("/agregarPelicula")
    public String mostrarFormularioNuevaPelicula(Model model) {
        model.addAttribute("directores", directorService.listarDirectores());
        model.addAttribute("plataformas", plataformaService.listarPlataformas());
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
        model.addAttribute("directores", directorService.listarDirectores());

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