package ar.dev.maxisandoval.nextfix.service;

import ar.dev.maxisandoval.nextfix.model.Director;
import ar.dev.maxisandoval.nextfix.model.Pelicula;
import ar.dev.maxisandoval.nextfix.repository.DirectorRepository;
import ar.dev.maxisandoval.nextfix.repository.PeliculaRepository;
import ar.dev.maxisandoval.nextfix.repository.PlataformaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PeliculaService {

    private PeliculaRepository peliculaRepository;
    private DirectorRepository directorRepository;
    private PlataformaRepository plataformaRepository;

    public Pelicula obtenerPeliculaPorId(Long id) {
        return peliculaRepository.findById(id).orElseThrow(
                () -> new RuntimeException("No se encontró la pelicula con el id:" + id));
    }

    public List<Pelicula> listarPeliculas() {
        return peliculaRepository.findByAllByOrderByTituloIgnoreCaseAsc();
    }

    public Pelicula guardarPelicula(Pelicula pelicula, Long idDirector, List<Long> idPlataformas) {
        Director director = directorRepository.findById(idDirector)
                .orElseThrow(() -> new RuntimeException("No se encontró el director con el id:"
                +idDirector+" al momento de guardar la pelicula"));

        pelicula.setDirector(director);

        if (idPlataformas != null) {
            pelicula.setPlataformasDisponibles(plataformaRepository.findAllById(idPlataformas));
        }

        return peliculaRepository.save(pelicula);
    }

    public void actualizarPelicula(Long idPelicula, Pelicula peliculaActualizada, Long idDirector, List<Long> idPlataformas) {
        Optional<Pelicula> peliculaOptional = peliculaRepository.findById(idPelicula);

        Director director = directorRepository.findById(idDirector)
                .orElseThrow(() -> new RuntimeException("No se encontró el director con el id: "+
                        idDirector+" al momento de actualizar la pelicula"));

        peliculaActualizada.setDirector(director);

        if (idPlataformas != null) {
            peliculaActualizada.setPlataformasDisponibles(plataformaRepository.findAllById(idPlataformas));
        }

        Pelicula peliculaExistente = construirPelicula(peliculaActualizada, peliculaOptional);
        peliculaRepository.save(peliculaExistente);
    }

    private Pelicula construirPelicula(Pelicula peliculaActualizada, Optional<Pelicula> peliculaOptional) {
        Pelicula.PeliculaBuilder peliculaBuilder = Pelicula.builder();

        peliculaOptional.ifPresent(peliculaExistente -> { //Si peliculaOptional esta presente, se le asigna el contenido
            peliculaBuilder
                    .id(peliculaExistente.getId())
                    .titulo(peliculaActualizada.getTitulo())
                    .genero(peliculaActualizada.getGenero())
                    .fechaEstreno(peliculaActualizada.getFechaEstreno())
                    .director(peliculaActualizada.getDirector())
                    .plataformasDisponibles(peliculaActualizada.getPlataformasDisponibles());
        });


        return peliculaBuilder.build();
    }

    public void eliminarPelicula(Long id) {
        peliculaRepository.deleteById(id);
    }
}
