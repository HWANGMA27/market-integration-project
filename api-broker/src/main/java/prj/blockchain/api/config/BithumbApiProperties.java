package prj.blockchain.api.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "api")
@Getter
@Setter
public class BithumbApiProperties {
    private Endpoint endpoint;

    @Getter
    @Setter
    public static class Endpoint {
        private String price;
        private String network;
        private String balance;
        private String currency;
    }
}