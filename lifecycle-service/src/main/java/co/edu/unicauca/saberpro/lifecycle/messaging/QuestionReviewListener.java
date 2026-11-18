package co.edu.unicauca.saberpro.lifecycle.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/** Consumidor del evento "pregunta enviada a revisión". */
@Component
public class QuestionReviewListener {

    private static final Logger log = LoggerFactory.getLogger(QuestionReviewListener.class);

    @RabbitListener(queues = RabbitConfig.QUEUE)
    public void onPreguntaEnviadaARevision(String mensaje) {
        log.info("[LIFECYCLE] Evento recibido en {} -> {}", RabbitConfig.QUEUE, mensaje);
        log.info("[LIFECYCLE] Pregunta registrada para revisión por un experto.");
    }
}
