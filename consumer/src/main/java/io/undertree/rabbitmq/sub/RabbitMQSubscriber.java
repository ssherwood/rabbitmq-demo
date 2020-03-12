package io.undertree.rabbitmq.sub;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RabbitMQSubscriber {

    public static void main(String[] args) {
        SpringApplication.run(RabbitMQSubscriber.class, args);
    }

    /**
     * Register a custom MessageConverter to be used by the "convertAndSend"; this will convert JSON
     * to Objects via Jackson (note we don't have to explicitly wire this into the AmqpTemplate as suggested
     * in a lot of sample code).
     */
    @Bean
    MessageConverter customMessageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }
}
