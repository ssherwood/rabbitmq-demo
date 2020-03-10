package io.undertree.rabbitmqpublish;

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

    /*
     * Register a custom MessageConverter to be used by the "convertAndSend"; this will convert Objects
     * to JSON via Jackson (note we don't have to explicitly wire this into the AmqpTemplate as suggested
     * in a lot of sample code).
     */
    @Bean
    public MessageConverter customMessageConverter(ObjectMapper objectMapper) {
        // if you need to customize the objectMapper differently that the default,
        // you could just create and configure it here...
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    /*
     * This creates the Exchange if one doesn't already exist
     * If it exists, this isn't technically required
     */
    @Bean
    Exchange topicExchange() {
        return new TopicExchange("customer.topic", true, false, Collections.emptyMap());
    }

    /**
     *
     */
    @Bean
    Exchange fanoutExchange() {
        return new FanoutExchange("customer.alt.fanout", true, false, Collections.emptyMap());
    }

    /**
     * Direct Exchange is an exchange which forwards messages to queues based on the messages’ routing key.
	 * The additional "alternate-exchange" routes messages that do not match any bound routing keys to the
	 * other exchange
     */
    @Bean
    Exchange directExchange() {
    	// durable 'true' exchanges survive broker restarts
		// autoDelete
		// exchanges are removed when the queues are finished using it (what exactly does that mean?)

        return new DirectExchange("customer.direct", true, false, Collections.singletonMap("alternate-exchange", "customer.alt.fanout"));
    }
}
