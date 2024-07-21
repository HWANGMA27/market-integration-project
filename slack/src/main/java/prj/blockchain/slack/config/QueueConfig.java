package prj.blockchain.slack.config;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Configuration
public class QueueConfig {

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
}
