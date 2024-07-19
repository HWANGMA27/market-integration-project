package prj.blockchain.api.config;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.rabbit.config.DirectRabbitListenerContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Configuration
public class RabbitConfig {

    private final QueueProperties queueProperties;

    @Bean
    public List<Queue> queues() {
        List<Queue> queues = new ArrayList<>();
        for (QueueProperties.Task task : queueProperties.getTasks()) {
            queues.add(new Queue(task.getName(), false));
        }
        return queues;
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(queueProperties.getExchange());
    }

    @Bean
    public List<Binding> bindings() {
        List<Binding> bindings = new ArrayList<>();
        for (QueueProperties.Task task : queueProperties.getTasks()) {
            Queue queue = new Queue(task.getName(), false);
            for (String key : task.getRoutingKey().keySet()) {
                bindings.add(BindingBuilder.bind(queue).to(exchange()).with(task.getRoutingKey().get(key)));
            }
        }
        return bindings;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public DefaultMessageHandlerMethodFactory messageHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
        factory.setValidator(validator());
        return factory;
    }

    @Bean
    public LocalValidatorFactoryBean validator() {
        return new LocalValidatorFactoryBean();
    }

    @Bean
    public DirectRabbitListenerContainerFactory directRabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        DirectRabbitListenerContainerFactory factory = new DirectRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(messageConverter());
        return factory;
    }
}