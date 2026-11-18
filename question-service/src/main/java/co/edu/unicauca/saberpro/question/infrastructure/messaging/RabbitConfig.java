package co.edu.unicauca.saberpro.question.infrastructure.messaging;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    public static final String EXCHANGE = "pregunta.exchange";
    public static final String QUEUE = "pregunta.revisar.queue";
    public static final String ROUTING_KEY = "pregunta.revisar";

    @Bean
    public DirectExchange preguntaExchange() {
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    public Queue revisarQueue() {
        return new Queue(QUEUE, true);
    }

    @Bean
    public Binding revisarBinding(Queue revisarQueue, DirectExchange preguntaExchange) {
        return BindingBuilder.bind(revisarQueue).to(preguntaExchange).with(ROUTING_KEY);
    }
}
