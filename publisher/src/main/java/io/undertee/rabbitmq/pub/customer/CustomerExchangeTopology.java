package io.undertee.rabbitmq.pub.customer;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * A Publisher should own it's own Exchange topology configuration
 */
@Component
public class CustomerExchangeTopology {

    public static final String CUSTOMER_TOPIC_EXCHANGE = "customer.topic";
    public static final String CUSTOMER_FANOUT_EXCHANGE = "customer.fanout";
    public static final String CUSTOMER_ALT_FANOUT_EXCHANGE = "customer.alt.fanout";
    public static final String CUSTOMER_DIRECT_EXCHANGE = "customer.direct";

    /**
     * A Topic Exchange routes messages to bound queues based on wildcard matching
     * the routing key and the routing pattern of the queue; if none is matched, the
     * message is dropped.
     * <p>
     * durable = true ; exchanges survive broker restart
     * autoDelete = false ; exchange is *not* deleted when last queue is unbound from it
     */
    @Bean
    Exchange topicExchange() {
        return new TopicExchange(CUSTOMER_TOPIC_EXCHANGE,
                true,
                false,
                Collections.emptyMap());
    }

    /**
     * A Fanout Exchange routes messages to all bound queues (routing keys are ignored)
     */
    @Bean
    Exchange fanoutExchange() {
        return new FanoutExchange(CUSTOMER_FANOUT_EXCHANGE,
                true,
                false,
                Collections.emptyMap());
    }

    /**
     *
     */
    @Bean
    Exchange fanoutAltExchange() {
        return new FanoutExchange(CUSTOMER_ALT_FANOUT_EXCHANGE,
                true,
                false,
                Collections.emptyMap());
    }

    /**
     * Direct Exchange is an exchange which forwards messages to queues based on the messages’ routing key.
     * The additional "alternate-exchange" routes messages that do not match any bound routing keys to the
     * other exchange
     */
    @Bean
    Exchange directExchange() {
        return new DirectExchange(CUSTOMER_DIRECT_EXCHANGE,
                true,
                false,
                Collections.singletonMap("alternate-exchange", "customer.alt.fanout"));
    }
}
