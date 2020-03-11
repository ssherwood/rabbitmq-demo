package io.undertree.rabbitmq.sub;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;

@SpringBootApplication
public class RabbitMQSubscriber {

    public static final DirectExchange CUSTOMER_DIRECT_EXCHANGE = new DirectExchange("customer.direct");
    public static final String CUSTOMER_CREATE_QUEUE = "customer.create";
    public static final String CUSTOMER_CREATE_ROUTING_KEY = "customer.create";

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

    // now create the queues this subscriber will use...

    @Bean
    Queue customerCreate() {
        // arguments ; used by plugins and broker-specific features such as message TTL, queue length limit, etc)
        // x-message-ttl -
        // x-dead-letter-exchange
        // x-dead-letter-routing-key
        // https://www.compose.com/articles/configuring-rabbitmq-exchanges-queues-and-bindings-part-2/

        return new Queue(CUSTOMER_CREATE_QUEUE,
                true, // durable 'true'; queue survives broker restarts
                false, // exclusive 'false'; can be used by more than this client's channel
                false, // autoDelete 'false'; won't be removed when not in use
                new HashMap<String, Object>() {{
                    put("x-message-ttl", 5000); // remove unconsumed messages after 5 seconds
                }});
    }

    @Bean
    Binding customerCreateDirectBinding(Queue customerCreateQueue) {
        return BindingBuilder.bind(customerCreateQueue)
                .to(CUSTOMER_DIRECT_EXCHANGE)
                .with(CUSTOMER_CREATE_ROUTING_KEY);
    }
}
