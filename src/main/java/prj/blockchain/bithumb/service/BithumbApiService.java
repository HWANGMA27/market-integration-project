package service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class BithumbApiService {

    private final WebClient webClient;

    public BithumbApiService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.bithumb.com").build();
    }

    public Mono<String> getTicker(String cryptocurrency) {
        return this.webClient.get()
                .uri("/public/ticker/{cryptocurrency}", cryptocurrency)
                .retrieve()
                .bodyToMono(String.class);
    }
}