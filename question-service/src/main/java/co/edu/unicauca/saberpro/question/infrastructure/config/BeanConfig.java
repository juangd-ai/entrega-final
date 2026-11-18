package co.edu.unicauca.saberpro.question.infrastructure.config;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import co.edu.unicauca.saberpro.question.application.QuestionApplicationService;
import co.edu.unicauca.saberpro.question.domain.ports.outbound.NotificacionPort;
import co.edu.unicauca.saberpro.question.domain.ports.outbound.PreguntaRepositoryPort;
import co.edu.unicauca.saberpro.question.domain.ports.outbound.ValidacionPort;
import co.edu.unicauca.saberpro.question.infrastructure.http.ValidationHttpAdapter;
import co.edu.unicauca.saberpro.question.infrastructure.jpa.JpaQuestionAdapter;
import co.edu.unicauca.saberpro.question.infrastructure.jpa.QuestionDomainMapper;
import co.edu.unicauca.saberpro.question.infrastructure.jpa.SpringDataQuestionRepository;
import co.edu.unicauca.saberpro.question.infrastructure.memory.InMemoryQuestionAdapter;
import co.edu.unicauca.saberpro.question.infrastructure.messaging.RabbitMQPublisher;

/**
 * Composición hexagonal. Para intercambiar el adaptador de persistencia basta
 * cambiar saberpro.persistence (jpa | memory) en application.properties.
 * El dominio y la capa de aplicación NO se modifican.
 */
@Configuration
public class BeanConfig {

    @Bean
    public PreguntaRepositoryPort preguntaRepositoryPort(
            @Value("${saberpro.persistence:jpa}") String modo,
            SpringDataQuestionRepository springDataRepository) {
        if ("memory".equalsIgnoreCase(modo)) {
            System.out.println(">>> ADAPTADOR DE PERSISTENCIA ACTIVO: InMemoryQuestionAdapter (HashMap)");
            return new InMemoryQuestionAdapter();
        }
        System.out.println(">>> ADAPTADOR DE PERSISTENCIA ACTIVO: JpaQuestionAdapter (H2)");
        return new JpaQuestionAdapter(springDataRepository, new QuestionDomainMapper());
    }

    @Bean
    public NotificacionPort notificacionPort(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        return new RabbitMQPublisher(rabbitTemplate, objectMapper);
    }

    @Bean
    public ValidacionPort validacionPort(@Value("${saberpro.validation.url}") String url) {
        return new ValidationHttpAdapter(url);
    }

    @Bean
    public QuestionApplicationService questionApplicationService(PreguntaRepositoryPort repositorio,
                                                                 NotificacionPort notificacion,
                                                                 ValidacionPort validacion) {
        return new QuestionApplicationService(repositorio, notificacion, validacion);
    }
}
