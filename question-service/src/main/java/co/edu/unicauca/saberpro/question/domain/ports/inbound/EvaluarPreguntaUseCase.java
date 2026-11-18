package co.edu.unicauca.saberpro.question.domain.ports.inbound;

import co.edu.unicauca.saberpro.question.domain.model.Pregunta;

/** Puerto de entrada: envío a revisión. */
public interface EvaluarPreguntaUseCase {
    Pregunta enviarARevision(Long id);
}
