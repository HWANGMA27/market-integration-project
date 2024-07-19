package prj.blockchain.api.config;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.config.DirectRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.listener.MethodRabbitListenerEndpoint;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import prj.blockchain.api.dto.CustomMessage;
import prj.blockchain.api.task.ScheduledTasks;

import java.lang.reflect.Method;

@RequiredArgsConstructor
@Configuration
public class RabbitListenerConfig implements RabbitListenerConfigurer {

    private final QueueProperties queueProperties;
    private final DirectRabbitListenerContainerFactory factory;
    private final DefaultMessageHandlerMethodFactory messageHandlerMethodFactory;
    @Lazy
    private final ScheduledTasks scheduledTasks;

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
        for (QueueProperties.Task task : queueProperties.getTasks()) {
            registerListener(registrar, task);
        }
    }

    private void registerListener(RabbitListenerEndpointRegistrar registrar, QueueProperties.Task task) {
        for (String key : task.getRoutingKey().keySet()) {
            String queueName = task.getName();

            Method method;
            try {
                method = scheduledTasks.getClass().getMethod("receiveMessage", CustomMessage.class, String.class);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }

            MethodRabbitListenerEndpoint endpoint = new MethodRabbitListenerEndpoint();
            endpoint.setId(queueName + "-" + key);
            endpoint.setMethod(method);
            endpoint.setBean(scheduledTasks);
            endpoint.setQueueNames(queueName);
            endpoint.setExclusive(false);
            endpoint.setMessageHandlerMethodFactory(messageHandlerMethodFactory);

            registrar.registerEndpoint(endpoint, factory);
        }
    }
}
