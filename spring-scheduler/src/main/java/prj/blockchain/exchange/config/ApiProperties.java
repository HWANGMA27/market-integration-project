package prj.blockchain.exchange.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "api.endpoint")
@Getter
@Setter
public class ApiProperties {
    private String price;
    private String networkStatus;
}