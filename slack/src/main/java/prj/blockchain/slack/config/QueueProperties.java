package prj.blockchain.slack.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

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
}