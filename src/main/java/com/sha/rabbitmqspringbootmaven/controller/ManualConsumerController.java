package com.sha.rabbitmqspringbootmaven.controller;

import com.sha.rabbitmqspringbootmaven.consumer.ManualConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sa
 * @date 14.08.2021
 * @time 17:21
 */
@RestController
public class ManualConsumerController
{
    @Autowired
    private ManualConsumer manualConsumer;

    @PostMapping("consume/{queueName}")
    public ResponseEntity<?> consumeAllMessagesOfQueue(@PathVariable String queueName)
    {
        return ResponseEntity.ok(manualConsumer.receiveMessages(queueName));
    }
}
