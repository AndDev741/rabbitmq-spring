package com.anddev.ampq.domain;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "email")
public class emailReceiver {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RabbitHandler
    public void receive(String in) {
        //WIll generate a random number between 0 and 100, if less than 50, go to dead queue
        if (Math.random() * 100 < 50) {
            System.out.println("[x] Dead letter '" + in + "'");
            this.rabbitTemplate.convertAndSend("deadQueue", in);
        }else{
            System.out.println("[x] Received '" + in + "'");
        }
    }
}
