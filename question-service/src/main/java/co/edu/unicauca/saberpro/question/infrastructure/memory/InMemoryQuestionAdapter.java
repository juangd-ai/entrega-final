package co.edu.unicauca.saberpro.question.infrastructure.memory;

import co.edu.unicauca.saberpro.question.domain.model.Pregunta;
import co.edu.unicauca.saberpro.question.domain.ports.outbound.PreguntaRepositoryPort;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/** Adaptador secundario alterno: implementa el MISMO puerto con un HashMap. */
public class InMemoryQuestionAdapter implements PreguntaRepositoryPort {

    private final Map<Long, Pregunta> almacen = new ConcurrentHashMap<>();
    private final AtomicLong secuencia = new AtomicLong(0);

    @Override
    public Pregunta guardar(Pregunta pregunta) {
        if (pregunta.getId() == null) {
            pregunta.asignarId(secuencia.incrementAndGet());
        }
        almacen.put(pregunta.getId(), pregunta);
        return pregunta;
    }

    @Override
    public Optional<Pregunta> buscarPorId(Long id) {
        return Optional.ofNullable(almacen.get(id));
    }

    @Override
    public List<Pregunta> listar() {
        return new ArrayList<>(almacen.values());
    }
}
