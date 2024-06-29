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
    private final String API_NETWORK_INFO_URL;
    private final String API_ORDERBOOK_URL;
    private final String API_PRICE_URL;

    public PublicRequestHandler(WebClient.Builder webClientBuilder,
                                @Value("${url.bithumb.base}") String baseUrl, @Value("${url.bithumb.exchange.ticker}") String tickerUrl,
                                @Value("${url.bithumb.exchange.status}") String statusUrl, @Value("${url.bithumb.exchange.status}") String networkUrl,
                                @Value("${url.bithumb.exchange.network}") String networkInfoUrl,
                                @Value("${url.bithumb.exchange.orderBook}") String orderBookUrl, @Value("${url.bithumb.exchange.price}") String priceUrl) {
        this.API_TICKER_URL = tickerUrl;
        this.API_STATUS_URL = statusUrl;
        this.API_NETWORK_URL = networkUrl;
        this.API_NETWORK_INFO_URL = networkInfoUrl;
        this.API_ORDERBOOK_URL = orderBookUrl;
        this.API_PRICE_URL = priceUrl;
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

    public Mono<ServerResponse> getOrderBook(ServerRequest request) {
        String cryptocurrency = request.pathVariable("cryptocurrency");
        String paymentCurrency = request.pathVariable("paymentCurrency");
        String requestUrl = API_ORDERBOOK_URL.concat("/").concat(cryptocurrency).concat("_").concat(paymentCurrency);
        return this.webClient.get()
                .uri(requestUrl)
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(response -> ServerResponse.ok().body(Mono.just(response), String.class))
                .onErrorResume(e -> ServerResponse.status(500).body(Mono.just("Error: " + e.getMessage()), String.class));
    }

    public Mono<ServerResponse> getCurrencyPrice(ServerRequest request) {
        String cryptocurrency = request.pathVariable("cryptocurrency");
        String paymentCurrency = request.pathVariable("paymentCurrency");
        String requestUrl = API_PRICE_URL.concat("/").concat(cryptocurrency).concat("_").concat(paymentCurrency);
        return this.webClient.get()
                .uri(requestUrl)
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(response -> ServerResponse.ok().body(Mono.just(response), String.class))
                .onErrorResume(e -> ServerResponse.status(500).body(Mono.just("Error: " + e.getMessage()), String.class));
    }

    public Mono<ServerResponse> getMinWithdrawAmount(ServerRequest request) {
        String cryptocurrency = request.pathVariable("cryptocurrency");
        String requestUrl = API_PRICE_URL.concat("/").concat(cryptocurrency);
        return this.webClient.get()
                .uri(requestUrl)
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(response -> ServerResponse.ok().body(Mono.just(response), String.class))
                .onErrorResume(e -> ServerResponse.status(500).body(Mono.just("Error: " + e.getMessage()), String.class));
    }

    public Mono<ServerResponse> getNetworkInfo(ServerRequest request) {
        return this.webClient.get()
                .uri(API_NETWORK_INFO_URL)
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(response -> ServerResponse.ok().body(Mono.just(response), String.class))
                .onErrorResume(e -> ServerResponse.status(500).body(Mono.just("Error: " + e.getMessage()), String.class));
    }
}
