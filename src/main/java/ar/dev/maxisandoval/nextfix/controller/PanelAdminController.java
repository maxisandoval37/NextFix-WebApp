package ar.dev.maxisandoval.nextfix.controller;

import ar.dev.maxisandoval.nextfix.model.Director;
import ar.dev.maxisandoval.nextfix.service.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

@Controller
@AllArgsConstructor
public class PanelAdminController {

    private final CustomUserDetailsService customUserDetailsService;
    private final DirectorService directorService;

    @GetMapping("/gestorRoles")
    public String gestorRoles(Model model) {
        model.addAttribute("usuarios", customUserDetailsService.listarUsuariosRegistrados());
        return "gestorRoles";
    }

    @GetMapping("/actualizarRolUsuario/{id}")
    public String mostrarFormularioActualizarUsuario(@PathVariable Long id, Model model) {
        model.addAttribute("usuario", customUserDetailsService.obtenerUsuarioPorId(id));

        return "actualizarRolUsuarioForm";
    }

    @PostMapping("/actualizarRolUsuario/{id}")
    public String actualizarRolUsuario(@PathVariable Long id, @RequestParam(required = false) String rol, @RequestParam(required = false) String nacionalidad, @RequestParam(required = false) String email, @RequestParam(required = false) LocalDate fechaNacimiento) {
        if (rol.equals("ROL_DIRECTOR") && nacionalidad != null && email !=null) {
            Director directorNuevo = new Director();
            directorNuevo.setNacionalidad(nacionalidad);
            directorNuevo.setEmail(email);
            directorNuevo.setFechaNacimiento(fechaNacimiento);
            directorNuevo.setUsuario(customUserDetailsService.obtenerUsuarioPorId(id));

            Director directorGuardado = directorService.guardarDirector(directorNuevo);
            customUserDetailsService.actualizarRolUsuarioDirector(id, directorGuardado);
        }
        else {
            customUserDetailsService.actualizarRolUsuario(id, rol);
        }
        return "redirect:/gestorRoles";
    }

    @GetMapping("/eliminarUsuario/{id}")
    public String eliminarUsuario(@PathVariable Long id) {
        customUserDetailsService.eliminarUsuario(id);
        return "redirect:/gestorRoles";
    }
}