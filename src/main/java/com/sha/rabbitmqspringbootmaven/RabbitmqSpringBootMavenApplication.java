package com.sha.rabbitmqspringbootmaven;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RabbitmqSpringBootMavenApplication {

    @Value("${spring.rabbitmq.host}")
    private String HOST;

    @Value("${spring.rabbitmq.port}")
    private int PORT;

    @Value("${spring.rabbitmq.virtual-host}")
    private String VIRTUAL_HOST;

    @Value("${spring.rabbitmq.username}")
    private String USERNAME;

    @Value("${spring.rabbitmq.password}")
    private String PASSWORD;

    @Bean
    public ConnectionFactory connectionFactory()
    {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setUsername(USERNAME);
        connectionFactory.setPassword(PASSWORD);
        connectionFactory.setPort(PORT);
        connectionFactory.setHost(HOST);
        connectionFactory.setVirtualHost(VIRTUAL_HOST);

        return connectionFactory;
    }

    @Bean
    public MessageConverter messageConverter()
    {
        ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    public SimpleRabbitListenerContainerFactory listenerContainerFactory()
    {
        SimpleRabbitListenerContainerFactory containerFactory = new SimpleRabbitListenerContainerFactory();
        containerFactory.setConnectionFactory(connectionFactory());
        containerFactory.setMessageConverter(messageConverter());
        containerFactory.setMaxConcurrentConsumers(10);
        containerFactory.setConcurrentConsumers(5);
        containerFactory.setAutoStartup(true);
        containerFactory.setPrefetchCount(10);

        /*
        containerFactory.setAdviceChain(RetryInterceptorBuilder.stateless()
                .maxAttempts(3)
                .recoverer(new RejectAndDontRequeueRecoverer())
                .build());

         */

        //If there is a problem, don't re-queue. send them to DLX.
        //containerFactory.setDefaultRequeueRejected(false);

        return containerFactory;
    }

	public static void main(String[] args) {
		SpringApplication.run(RabbitmqSpringBootMavenApplication.class, args);
	}

}
