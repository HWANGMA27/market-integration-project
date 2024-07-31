package prj.blockchain.bithumb.handler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

@Component
public class PublicRequestHandler {
    private final WebClient webClient;
    private final String API_TICKER_URL;
    private final String API_STATUS_URL;
    private final String API_NETWORK_INFO_URL;
    private final String API_ORDERBOOK_URL;
    private final String API_PRICE_URL;

    public PublicRequestHandler(WebClient.Builder webClientBuilder,
                                @Value("${url.base}") String baseUrl, @Value("${url.exchange.ticker}") String tickerUrl,
                                @Value("${url.exchange.status}") String statusUrl,
                                @Value("${url.exchange.network}") String networkInfoUrl,
                                @Value("${url.exchange.orderBook}") String orderBookUrl, @Value("${url.exchange.price}") String priceUrl) {
        this.API_TICKER_URL = tickerUrl;
        this.API_STATUS_URL = statusUrl;
        this.API_NETWORK_INFO_URL = networkInfoUrl;
        this.API_ORDERBOOK_URL = orderBookUrl;
        this.API_PRICE_URL = priceUrl;
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
    }

    public Mono<ServerResponse> getTicker(ServerRequest request) {
        String cryptocurrency = request.pathVariable("cryptocurrency");
        return Mono.defer(() -> this.webClient.get()
                .uri(API_TICKER_URL.concat("/{cryptocurrency}"), cryptocurrency)
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(response -> ServerResponse.ok().body(Mono.just(response), String.class))
                .onErrorResume(e -> ServerResponse.status(500).body(Mono.just("Error: " + e.getMessage()), String.class)))
                .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2))
                        .filter(throwable -> throwable instanceof RuntimeException));
    }

    public Mono<ServerResponse> getStatus(ServerRequest request) {
        String cryptocurrency = request.pathVariable("cryptocurrency");
        return Mono.defer(() -> this.webClient.get()
            .uri(API_STATUS_URL.concat("/{cryptocurrency}"), cryptocurrency)
            .retrieve()
            .bodyToMono(String.class)
            .flatMap(response -> ServerResponse.ok().body(Mono.just(response), String.class))
            .onErrorResume(e -> ServerResponse.status(500).body(Mono.just("Error: " + e.getMessage()), String.class)))
                .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2))
                        .filter(throwable -> throwable instanceof RuntimeException));
    }

    public Mono<ServerResponse> getOrderBook(ServerRequest request) {
        String cryptocurrency = request.pathVariable("cryptocurrency");
        String paymentCurrency = request.pathVariable("paymentCurrency");
        String requestUrl = API_ORDERBOOK_URL.concat("/").concat(cryptocurrency).concat("_").concat(paymentCurrency);
        return Mono.defer(() -> this.webClient.get()
                .uri(requestUrl)
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(response -> ServerResponse.ok().body(Mono.just(response), String.class))
                .onErrorResume(e -> ServerResponse.status(500).body(Mono.just("Error: " + e.getMessage()), String.class)))
                .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2))
                        .filter(throwable -> throwable instanceof RuntimeException));
    }

    public Mono<ServerResponse> getCurrencyPrice(ServerRequest request) {
        String cryptocurrency = request.pathVariable("cryptocurrency");
        String paymentCurrency = request.pathVariable("paymentCurrency");
        String requestUrl = API_PRICE_URL.concat("/").concat(cryptocurrency).concat("_").concat(paymentCurrency);
        return Mono.defer(() -> this.webClient.get()
                .uri(requestUrl)
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(response -> ServerResponse.ok().body(Mono.just(response), String.class))
                .onErrorResume(e -> ServerResponse.status(500).body(Mono.just("Error: " + e.getMessage()), String.class)))
                .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2))
                        .filter(throwable -> throwable instanceof RuntimeException));
    }

    public Mono<ServerResponse> getMinWithdrawAmount(ServerRequest request) {
        String cryptocurrency = request.pathVariable("cryptocurrency");
        String requestUrl = API_PRICE_URL.concat("/").concat(cryptocurrency);
        return Mono.defer(() -> this.webClient.get()
                .uri(requestUrl)
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(response -> ServerResponse.ok().body(Mono.just(response), String.class))
                .onErrorResume(e -> ServerResponse.status(500).body(Mono.just("Error: " + e.getMessage()), String.class)))
                .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2))
                        .filter(throwable -> throwable instanceof RuntimeException));
    }

    public Mono<ServerResponse> getNetworkInfo(ServerRequest request) {
        return Mono.defer(() -> this.webClient.get()
                .uri(API_NETWORK_INFO_URL)
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(response -> ServerResponse.ok().body(Mono.just(response), String.class))
                .onErrorResume(e -> ServerResponse.status(500).body(Mono.just("Error: " + e.getMessage()), String.class)))
                .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2))
                .filter(throwable -> throwable instanceof RuntimeException));
    }
}
