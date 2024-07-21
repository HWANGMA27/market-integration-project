package prj.blockchain.api.config.mq;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Data
@Configuration
@ConfigurationProperties(prefix = "queue")
public class QueueProperties {
    private String exchange;
    private List<Task> tasks;

    @Data
    public static class Task {
        private String name;
        private Map<String, String> routingKey;
    }

    public Optional<Task> getTaskByName(String taskName) {
        return tasks.stream()
                .filter(task -> taskName.equals(task.getName()))
                .findFirst();
    }
}