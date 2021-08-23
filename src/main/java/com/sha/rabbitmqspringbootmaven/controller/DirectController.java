package com.sha.rabbitmqspringbootmaven.controller;

import com.sha.rabbitmqspringbootmaven.model.QueueObject;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @author sa
 * @date 14.08.2021
 * @time 15:34
 */
@RestController
public class DirectController
{
    @Autowired
    private AmqpTemplate directExchange;

    @Value("${rabbitmq.direct.routing-key-1}")
    private String DIRECT_ROUTING_KEY_1;

    @Value("${rabbitmq.direct.routing-key-2}")
    private String DIRECT_ROUTING_KEY_2;

    @PostMapping("direct/{key}")
    public ResponseEntity<?> sendMessageWithDirectExchange(@PathVariable int key)
    {
        QueueObject object = new QueueObject("direct", LocalDateTime.now());
        String routingKey = key == 1 ? DIRECT_ROUTING_KEY_1 : DIRECT_ROUTING_KEY_2;

        directExchange.convertAndSend(routingKey, object);

        return ResponseEntity.ok(true);
    }
}
