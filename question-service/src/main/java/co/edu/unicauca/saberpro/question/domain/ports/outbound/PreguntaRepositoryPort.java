package co.edu.unicauca.saberpro.question.domain.ports.outbound;

import co.edu.unicauca.saberpro.question.domain.model.Pregunta;

import java.util.List;
import java.util.Optional;

/** Puerto de salida: persistencia. */
public interface PreguntaRepositoryPort {
    Pregunta guardar(Pregunta pregunta);
    Optional<Pregunta> buscarPorId(Long id);
    List<Pregunta> listar();
}
