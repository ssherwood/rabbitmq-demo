package io.undertree.rabbitmqpublish;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Random;


/**
 *
 */
@Component
public class CustomerEventSimulator {
    private final Random random = new Random();
    private final List<String> routingKeys = Arrays.asList("customer.create", "customer.update", "customer.delete");
    private final RabbitTemplate rabbitTemplate;

    /**
     * @param rabbitTemplate
     */
    public CustomerEventSimulator(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitTemplate.setExchange("customer.direct");
    }

    @Scheduled(fixedRate = 3000L)
    public void send() {
        for (int i = 0; i < random.nextInt(3); i++) {
            rabbitTemplate.convertAndSend(getRandomRoutingKey(), new CustomerEvent(), msg -> {
                msg.getMessageProperties().setHeader("foo", "bar");
                //msg.getMessageProperties().setPriority(0);
                return msg;
            });
        }
    }

    private String getRandomRoutingKey() {
        return routingKeys.get(random.nextInt(routingKeys.size()));
    }
}
