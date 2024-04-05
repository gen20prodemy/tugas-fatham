package com.spring.pos2.controller;

import com.spring.pos2.consumer.MessageConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Controller;

@Controller
public class KafkaConsumerController {

    @Autowired
    private MessageConsumer messageConsumer;

    @KafkaListener(topics = "test", groupId = "my-group-id")
    public void listenMessage(String message) {
        messageConsumer.listen(message);
    }
}