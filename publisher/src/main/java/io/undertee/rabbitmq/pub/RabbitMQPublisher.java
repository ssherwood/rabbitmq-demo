package io.undertee.rabbitmq.pub;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RabbitMQPublisher {

    public static void main(String[] args) {
        SpringApplication.run(RabbitMQPublisher.class, args);
    }

    /**
     * Register a custom MessageConverter to be used by the "convertAndSend"; this will convert Objects
     * to JSON via Jackson2 (note we don't have to explicitly wire this into the AmqpTemplate as suggested
     * in a lot of sample code, Spring Boot does that for you!).
     */
    @Bean
    MessageConverter customMessageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }
}
