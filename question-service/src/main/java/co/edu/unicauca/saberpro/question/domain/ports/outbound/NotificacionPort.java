package co.edu.unicauca.saberpro.question.domain.ports.outbound;

import co.edu.unicauca.saberpro.question.domain.model.Pregunta;

/** Puerto de salida: notificación asíncrona (implementado con RabbitMQ). */
public interface NotificacionPort {
    void notificarEnvioARevision(Pregunta pregunta);
}
