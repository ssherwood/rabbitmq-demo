package io.undertee.rabbitmq.pub.customer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Random;


/**
 * RabbitMQ Publisher "Simulator"
 * <p>
 * This component is a basic event simulator that creates fake customer events and
 * publishes them to an specific exchange using a randomly selected routingKey/topic.
 * TODO
 * More flexibility of randomness (timing & volumes)
 * Could make it more generic and pass in exchanges, queues, event creator, etc.
 */
@Component
public class CustomerEventSimulator {

    Logger logger = LoggerFactory.getLogger(CustomerEventSimulator.class);

    private final Random randomize = new Random();
    private final List<String> exchanges = Arrays.asList("customer.direct", "customer.fanout", "customer.topic");
    private final List<String> routingKeys = Arrays.asList("customer.create", "customer.update", "customer.delete", "customer.login", "customer.logout");
    private final RabbitTemplate rabbitTemplate;


    /**
     * @param rabbitTemplate
     */
    public CustomerEventSimulator(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Scheduled(fixedRate = 1500L, initialDelay = 500L)
    public void send() {
        for (int i = 0; i < randomize.nextInt(5); i++) {
            logger.info("Sending Customer Event...");
            rabbitTemplate.convertAndSend(
                    getRandomExchange(),
                    getRandomRoutingKey(),
                    new CustomerEvent(),
                    msg -> {
                        // msg.getMessageProperties().setPriority(0);
                        // msg.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                        msg.getMessageProperties().setHeader("foo", "bar");
                        return msg;
                    });
        }
    }

    private String getRandomExchange() {
        return exchanges.get(randomize.nextInt(exchanges.size()));
    }

    private String getRandomRoutingKey() {
        return routingKeys.get(randomize.nextInt(routingKeys.size()));
    }
}
