package com.sha.rabbitmqspringbootmaven.queue;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author sa
 * @date 15.08.2021
 * @time 14:17
 */
@Configuration
public class MaxLengthByteConfig
{
    @Autowired
    private AmqpAdmin amqpAdmin;

    @Value("${rabbitmq.max-length-byte.queue}")
    private String QUEUE_NAME;

    Queue createMaxLengthByteQueue()
    {
        return QueueBuilder.durable(QUEUE_NAME)
                .maxLengthBytes(10)
                .build();
    }

    @Bean
    public AmqpTemplate defaultMaxLengthByteExchange(ConnectionFactory connectionFactory,
                                                     MessageConverter messageConverter)
    {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        rabbitTemplate.setRoutingKey(QUEUE_NAME);

        return rabbitTemplate;
    }

    @PostConstruct
    public void init()
    {
        amqpAdmin.declareQueue(createMaxLengthByteQueue());
    }
}
