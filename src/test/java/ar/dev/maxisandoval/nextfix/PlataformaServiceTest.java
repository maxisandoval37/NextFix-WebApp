package ar.dev.maxisandoval.nextfix;

import ar.dev.maxisandoval.nextfix.model.Plataforma;
import ar.dev.maxisandoval.nextfix.service.PlataformaService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.math.BigDecimal;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class PlataformaServiceTest extends BaseTest {

    private final PlataformaService plataformaService;
    private Plataforma plataformaGuardada;

    @BeforeEach
    void setup() {
        Plataforma plataforma = new Plataforma();
        plataforma.setNombre("Netflix");
        plataforma.setPrecio(new BigDecimal("5"));
        plataforma.setMoneda("ars");
        plataforma.setEnlace("www.netflix.com");

        plataformaGuardada = plataformaService.guardarPlataforma(plataforma);
    }

    @Test
    void testGuardarPlataforma() {
        assertNotNull(plataformaGuardada.getId());
        assertEquals("Netflix", plataformaGuardada.getNombre());
        assertEquals(new BigDecimal("5"), plataformaGuardada.getPrecio());
        assertEquals("ars", plataformaGuardada.getMoneda());
        assertEquals("www.netflix.com", plataformaGuardada.getEnlace());
    }

    @Test
    void testListartPlataformas() {
        assertFalse(plataformaService.listarPlataformas().isEmpty());
    }

    @Test
    void testObtenerPlataformaPorId() {
        Long plataformaId = 1L;
        Plataforma plataforma = plataformaService.obtenerPlataformaPorId(plataformaId);

        assertNotNull(plataforma);
        assertEquals(plataformaId, plataforma.getId());
    }

    @Test
    void testEliminarPlataforma() {
        List<Plataforma> plataformaList = plataformaService.listarPlataformas();
        assertFalse(plataformaList.isEmpty());
        Long idPlaformaAEliminar = plataformaList.get(plataformaList.size()-1).getId();

        plataformaService.eliminarPlataforma(idPlaformaAEliminar);

        Exception exception = assertThrows(RuntimeException.class, () ->
                plataformaService.obtenerPlataformaPorId(idPlaformaAEliminar));

        String mensajeEsperado = "No se encontró la plataforma con el id: " + idPlaformaAEliminar;
        String mensajeActual = exception.getMessage();

        // Corroboramos que efectivamente fue eliminado
        assertTrue(mensajeActual.contains(mensajeEsperado));
    }
}