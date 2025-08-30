package com.anddev.ampq.domain;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "deadQueue")
public class deadQueueReceiver {

    @RabbitHandler
    public void receive(String in) {
        System.out.println("[x] Dead letter [RECEIVED] '" + in + "'");
    }
}
