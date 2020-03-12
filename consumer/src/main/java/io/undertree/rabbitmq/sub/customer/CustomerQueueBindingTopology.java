package io.undertree.rabbitmq.sub.customer;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 *
 */
@Component
public class CustomerQueueBindingTopology {
    public static final String CUSTOMER_CREATE_QUEUE = "customer.create";
    public static final String CUSTOMER_CREATE_ROUTING_KEY = "customer.create";
    public static final DirectExchange CUSTOMER_DIRECT_EXCHANGE = new DirectExchange("customer.direct");

    // x-dead-letter-exchange
    // x-dead-letter-routing-key
    // https://www.compose.com/articles/configuring-rabbitmq-exchanges-queues-and-bindings-part-2/

    @Bean
    Queue customerCreateQueue() {
        return new Queue(CUSTOMER_CREATE_QUEUE,
                true, // durable 'true'; queue survives broker restarts
                false, // exclusive 'false'; can be used by more than this client's channel
                false, // autoDelete 'false'; won't be removed when not in use
                new HashMap<String, Object>() {{
                    put("x-queue-type", "classic");
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
