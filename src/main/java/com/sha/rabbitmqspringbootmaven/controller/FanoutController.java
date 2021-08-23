package com.sha.rabbitmqspringbootmaven.controller;

import com.sha.rabbitmqspringbootmaven.model.QueueObject;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @author sa
 * @date 14.08.2021
 * @time 15:54
 */
@RestController
public class FanoutController
{
    @Autowired
    private AmqpTemplate fanoutExchange;

    @PostMapping("fanout")
    public ResponseEntity<?> sendMessageWithFanoutExchange()
    {
        QueueObject object = new QueueObject("fanout", LocalDateTime.now());
        fanoutExchange.convertAndSend(object);

        return ResponseEntity.ok(true);
    }
}
