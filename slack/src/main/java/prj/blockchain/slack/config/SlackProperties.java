package prj.blockchain.slack.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Data
@Configuration
@ConfigurationProperties(prefix = "slack")
public class SlackProperties {
    private Channel channel;

    @Data
    public static class Channel {
        private String noti;
    }
}