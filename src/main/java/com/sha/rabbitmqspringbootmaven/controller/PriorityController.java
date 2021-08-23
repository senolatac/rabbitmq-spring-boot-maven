package com.sha.rabbitmqspringbootmaven.controller;

import com.sha.rabbitmqspringbootmaven.model.QueueObject;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @author sa
 * @date 15.08.2021
 * @time 14:55
 */
@RestController
public class PriorityController
{
    @Autowired
    private AmqpTemplate defaultPriorityExchange;

    @PostMapping("priority")
    public ResponseEntity<?> sendPriorityMessage(
            @RequestParam(value = "priority", required = false, defaultValue = "0") int priority)
    {
        QueueObject object = new QueueObject("priority: " + priority, LocalDateTime.now());

        defaultPriorityExchange.convertAndSend(object, (message) -> {
            message.getMessageProperties().setPriority(priority);
            return message;
        });

        return ResponseEntity.ok(true);
    }
}
