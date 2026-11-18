package co.edu.unicauca.saberpro.question.domain;

import co.edu.unicauca.saberpro.question.application.QuestionApplicationService;
import co.edu.unicauca.saberpro.question.domain.exception.ReglaNegocioException;
import co.edu.unicauca.saberpro.question.domain.model.*;
import co.edu.unicauca.saberpro.question.infrastructure.memory.InMemoryQuestionAdapter;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PreguntaTest {

    private List<OpcionRespuesta> cuatroOpciones(int correctas) {
        return List.of(
                new OpcionRespuesta("A", correctas >= 1),
                new OpcionRespuesta("B", correctas >= 2),
                new OpcionRespuesta("C", false),
                new OpcionRespuesta("D", false));
    }

    private Pregunta nueva(List<OpcionRespuesta> opciones) {
        return Pregunta.crear(new Enunciado("¿Cuál es la capital de Colombia?"),
                new Justificacion("Bogotá es la capital desde 1538"), opciones);
    }

    @Test
    void justificacionCortaLanzaExcepcion() {
        assertThrows(ReglaNegocioException.class, () -> new Justificacion("muy corta"));
    }

    @Test
    void enunciadoVacioLanzaExcepcion() {
        assertThrows(ReglaNegocioException.class, () -> new Enunciado("  "));
    }

    @Test
    void enviarARevisionConReglasValidasCambiaEstado() {
        Pregunta p = nueva(cuatroOpciones(1));
        p.enviarARevision();
        assertEquals(EstadoPregunta.PENDIENTE_REVISION, p.getEstado());
    }

    @Test
    void sinExactamenteCuatroOpcionesNoSePuedeEnviar() {
        Pregunta p = nueva(List.of(new OpcionRespuesta("A", true)));
        assertThrows(ReglaNegocioException.class, p::enviarARevision);
        assertEquals(EstadoPregunta.BORRADOR, p.getEstado());
    }

    @Test
    void conDosCorrectasNoSePuedeEnviar() {
        Pregunta p = nueva(cuatroOpciones(2));
        assertThrows(ReglaNegocioException.class, p::enviarARevision);
    }

    @Test
    void casoDeUsoFuncionaConAdaptadorInMemory() {
        QuestionApplicationService servicio = new QuestionApplicationService(
                new InMemoryQuestionAdapter(), pregunta -> {}, (e, j) -> {});
        Pregunta creada = servicio.crear("¿Cuál es la capital de Colombia?",
                "Bogotá es la capital desde 1538", cuatroOpciones(1));
        assertNotNull(creada.getId());
        Pregunta enviada = servicio.enviarARevision(creada.getId());
        assertEquals(EstadoPregunta.PENDIENTE_REVISION, enviada.getEstado());
    }
}
