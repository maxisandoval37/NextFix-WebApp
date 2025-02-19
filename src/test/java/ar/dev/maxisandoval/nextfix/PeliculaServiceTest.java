package ar.dev.maxisandoval.nextfix;

import ar.dev.maxisandoval.nextfix.model.*;
import ar.dev.maxisandoval.nextfix.service.*;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class PeliculaServiceTest {

    private final PeliculaService peliculaService;
    private final DirectorService directorService;
    private final PlataformaService plataformaService;
    private Director directorGuardado;
    private Plataforma plataformaGuardada;
    private Pelicula peliculaTemp;

    @BeforeEach
    void setup() {
        Director director = new Director();
        director.setNacionalidad("argentina");
        director.setFechaNacimiento(LocalDate.now());
        director.setEmail("juandiaz@example.com");

        directorGuardado = directorService.guardarDirector(director);

        Plataforma plataforma = new Plataforma();
        plataforma.setNombre("Netflix");
        plataforma.setPrecio(new BigDecimal("5"));
        plataforma.setMoneda("ars");
        plataforma.setEnlace("www.netflix.com");

        plataformaGuardada = plataformaService.guardarPlataforma(plataforma);

        peliculaTemp = new Pelicula();
        peliculaTemp.setTitulo("Robocop");
        peliculaTemp.setGenero("acción");
        peliculaTemp.setFechaEstreno(LocalDate.now());
    }

    @Test
    void testGuardarPeliculaSinPlataforma() {
        peliculaTemp.setDirector(directorGuardado);
        Pelicula peliculaGuardada = peliculaService.guardarPelicula(
                peliculaTemp, directorGuardado.getId(), null);

        assertNotNull(peliculaGuardada.getId());
        assertEquals("Robocop", peliculaGuardada.getTitulo());
        assertEquals("acción", peliculaGuardada.getGenero());
        assertEquals(directorGuardado.getId(), peliculaGuardada.getDirector().getId());
    }

    @Test
    void testGuardarPeliculaConPlataformas() {
        List<Long> idsPlaformasDisponibles = new ArrayList<>();
        idsPlaformasDisponibles.add(plataformaGuardada.getId());

        Pelicula peliculaGuardada = peliculaService.guardarPelicula(peliculaTemp, directorGuardado.getId(), idsPlaformasDisponibles);

        assertNotNull(peliculaGuardada.getId());
        assertEquals("Robocop", peliculaGuardada.getTitulo());
        assertEquals("acción", peliculaGuardada.getGenero());
        assertEquals(directorGuardado.getId(), peliculaGuardada.getDirector().getId());
        assertFalse(peliculaGuardada.getPlataformasDisponibles().isEmpty());
    }

    @Test
    void testListarPeliculas() {
        assertFalse(peliculaService.listarPeliculas().isEmpty());
    }

    @Test
    void testObtenerPeliculaPorId() {
        Long idPelicula = 1L;
        Pelicula pelicula = peliculaService.obtenerPeliculaPorId(idPelicula);

        assertNotNull(pelicula);
        assertEquals(idPelicula, pelicula.getId());
    }

    @Test
    void testActualizarPeliculaSinPlataformas() {
        Long idPelicula = 1L;

        Pelicula peliculaActualizada = new Pelicula();
        peliculaActualizada.setTitulo("Robocop");
        peliculaActualizada.setGenero("Suspenso");
        peliculaActualizada.setFechaEstreno(LocalDate.now());
        peliculaActualizada.setDirector(directorGuardado);

        peliculaService.actualizarPelicula(idPelicula, peliculaActualizada, directorGuardado.getId(), null);

        Pelicula peliculaDespuesDeActualizar = peliculaService.obtenerPeliculaPorId(idPelicula);

        assertEquals(peliculaActualizada.getGenero(), peliculaDespuesDeActualizar.getGenero());
        assertEquals(peliculaActualizada.getTitulo(), peliculaDespuesDeActualizar.getTitulo());
        assertEquals(peliculaActualizada.getDirector().getId(), peliculaDespuesDeActualizar.getDirector().getId());
    }

    @Test
    void testEliminarPelicula() {
        List<Pelicula> peliculas = peliculaService.listarPeliculas();
        assertFalse(peliculas.isEmpty());
        Long idPeliculaAEliminar = peliculas.get(peliculas.size()-1).getId();

        peliculaService.eliminarPelicula(idPeliculaAEliminar);

        Exception exception = assertThrows(RuntimeException.class, () -> peliculaService.obtenerPeliculaPorId(idPeliculaAEliminar));

        String expectedMessage = "No se encontró la pelicula con el id: " + idPeliculaAEliminar;
        String actualMessage = exception.getMessage();

        //Corroboramos que efectivamente fue eliminada
        assertTrue(actualMessage.contains(expectedMessage));
    }
}