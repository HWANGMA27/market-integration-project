package prj.blockchain.exchange.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "queue")
public class QueueProperties {

    private String name;
    private String exchange;
    private RoutingKey routingKey;

    @Data
    public static class RoutingKey {
        private String condition;
        private String balance;
        private String network;
    }
}
