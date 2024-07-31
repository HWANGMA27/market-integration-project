package prj.blockchain.bithumb.config;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import prj.blockchain.bithumb.util.ApiClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;
import java.util.function.BiFunction;
import java.util.function.Function;

@RequiredArgsConstructor
@Configuration
public class ApiClientConfig {

    private final WebClient webClient;
    @Value("${api.maxAttempts}")
    private int maxAttempts;

    @Bean
    public BiFunction<String, HashMap<String, String>, Mono<String>> apiCaller(ApiClient apiClient) {
        return (url, params) -> Mono.fromCallable(() -> apiClient.callApi(url, params)).retryWhen(Retry.fixedDelay(maxAttempts, Duration.ofSeconds(2))
                .filter(throwable -> throwable instanceof TimeoutException));
    }

    @Bean
    public Function<String, Mono<String>> webClientCaller() {
        return url -> Mono.defer(() -> webClient.get()
                        .uri(url)
                        .retrieve()
                        .bodyToMono(String.class))
                .retryWhen(Retry.fixedDelay(maxAttempts, Duration.ofSeconds(2))
                        .filter(throwable -> throwable instanceof TimeoutException));
    }
}