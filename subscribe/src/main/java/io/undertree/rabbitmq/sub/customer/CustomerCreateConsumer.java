package io.undertree.rabbitmq.sub.customer;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Service;

import java.util.Map;

import static io.undertree.rabbitmq.sub.RabbitMQSubscriber.CUSTOMER_CREATE_QUEUE;

@Service
public class CustomerCreateConsumer {

    /**
     * A simple consumer of the customer.create queue
     *
     * @param customer - the Message as it was converted by the custom message converter (JSON->Object)
     * @param message  - optional; ask Spring AMQP to provide the actual Message
     * @param headers  - optional; ask Spring AMQP to provide the headers as a Map
     * @param header   - optional; ask Spring AMQP to provide a specific header value
     */
    @RabbitListener(queues = {CUSTOMER_CREATE_QUEUE})
    public void receiveCustomerCreateEvent(CustomerEvent customer, Message message, @Headers Map<String, Object> headers, @Header("foo") String header) {
        System.out.println("Customer Created: " + customer);
        System.out.println("   ContentType = " + message.getMessageProperties().getContentType());
        System.out.println("   Headers = " + headers);
        System.out.println("   Header 'foo' = " + header);

        // TODO: presumably you'll do something with the customer object...
    }
}
