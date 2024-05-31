package prj.blockchain.bithumb.handler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class PublicRequestHandler {
    private final WebClient webClient;
    private final String API_TICKER_URL;
    private final String API_STATUS_URL;
    private final String API_NETWORK_URL;

    public PublicRequestHandler(WebClient.Builder webClientBuilder,
                                @Value("${url.bithumb.base}") String baseUrl, @Value("${url.bithumb.exchange.ticker}") String tickerUrl,
                                @Value("${url.bithumb.exchange.status}") String statusUrl, @Value("${url.bithumb.exchange.status}") String networkUrl) {
        this.API_TICKER_URL = tickerUrl;
        this.API_STATUS_URL = statusUrl;
        this.API_NETWORK_URL = networkUrl;
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
    }

    public Mono<ServerResponse> getTicker(ServerRequest request) {
        String cryptocurrency = request.pathVariable("cryptocurrency");
        return this.webClient.get()
                .uri(API_TICKER_URL.concat("/{cryptocurrency}"), cryptocurrency)
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(response -> ServerResponse.ok().body(Mono.just(response), String.class))
                .onErrorResume(e -> ServerResponse.status(500).body(Mono.just("Error: " + e.getMessage()), String.class));
    }

    public Mono<ServerResponse> getStatus(ServerRequest request) {
        String cryptocurrency = request.pathVariable("cryptocurrency");
        return this.webClient.get()
                .uri(API_STATUS_URL.concat("/{cryptocurrency}"), cryptocurrency)
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(response -> ServerResponse.ok().body(Mono.just(response), String.class))
                .onErrorResume(e -> ServerResponse.status(500).body(Mono.just("Error: " + e.getMessage()), String.class));
    }

    public Mono<ServerResponse> getNetworkStatus(ServerRequest request) {
        return this.webClient.get()
                .uri(API_NETWORK_URL)
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(response -> ServerResponse.ok().body(Mono.just(response), String.class))
                .onErrorResume(e -> ServerResponse.status(500).body(Mono.just("Error: " + e.getMessage()), String.class));
    }
}
