package io.undertee.rabbitmq.pub;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Collections;

@SpringBootApplication
@EnableScheduling
public class RabbitMQPublisher {

    public static void main(String[] args) {
        SpringApplication.run(RabbitMQPublisher.class, args);
    }

    /**
     * Register a custom MessageConverter to be used by the "convertAndSend"; this will convert Objects
     * to JSON via Jackson (note we don't have to explicitly wire this into the AmqpTemplate as suggested
     * in a lot of sample code).
     */
    @Bean
    MessageConverter customMessageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    // now create the exchanges this publisher will use...

    /**
     * A Topic Exchange routes messages to bound queues based on wildcard matching
     * the routing key and the routing pattern of the queue; if none is matched, the
     * message is dropped.
     * <p>
     * durable = true ; the exchange persists across broker restarts
     * autoDelete = false ; don't remove the exchange if there are no consuming queues
     */
    @Bean
    Exchange topicExchange() {
        return new TopicExchange("customer.topic", true, false, Collections.emptyMap());
    }

    /**
     * A Fanout Exchange routes messages to all bound queues (routing keys are ignored)
     */
    @Bean
    Exchange fanoutExchange() {
        return new FanoutExchange("customer.fanout", true, false, Collections.emptyMap());
    }

    /**
     *
     */
    @Bean
    Exchange fanoutAltExchange() {
        return new FanoutExchange("customer.alt.fanout", true, false, Collections.emptyMap());
    }

    /**
     * Direct Exchange is an exchange which forwards messages to queues based on the messages’ routing key.
     * The additional "alternate-exchange" routes messages that do not match any bound routing keys to the
     * other exchange
     */
    @Bean
    Exchange directExchange() {
        return new DirectExchange("customer.direct", true, false, Collections.singletonMap("alternate-exchange", "customer.alt.fanout"));
    }
}
