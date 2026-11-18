package co.edu.unicauca.saberpro.question.infrastructure.messaging;

import co.edu.unicauca.saberpro.question.domain.model.Pregunta;
import co.edu.unicauca.saberpro.question.domain.ports.outbound.NotificacionPort;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.LinkedHashMap;
import java.util.Map;

/** Adaptador secundario: publica el evento en RabbitMQ como JSON (texto). */
public class RabbitMQPublisher implements NotificacionPort {

    private static final Logger log = LoggerFactory.getLogger(RabbitMQPublisher.class);

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public RabbitMQPublisher(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public void notificarEnvioARevision(Pregunta pregunta) {
        Map<String, Object> evento = new LinkedHashMap<>();
        evento.put("evento", "PREGUNTA_ENVIADA_A_REVISION");
        evento.put("preguntaId", pregunta.getId());
        evento.put("enunciado", pregunta.getEnunciado().getTexto());
        evento.put("estado", pregunta.getEstado().name());
        try {
            String json = objectMapper.writeValueAsString(evento);
            rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE, RabbitConfig.ROUTING_KEY, json);
            log.info("[QUESTION] Evento publicado en RabbitMQ: {}", json);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("No se pudo serializar el evento", e);
        }
    }
}
