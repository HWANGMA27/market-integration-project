package prj.blockchain.bithumb.handler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import prj.blockchain.bithumb.dto.PrivateRequestDto;
import prj.blockchain.bithumb.util.ApiClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;
import java.util.function.BiFunction;

@Component
public class PrivateRequestHandler {
    private final ApiClient apiClient;
    private final String USER_TRANSACTIONS_URL;
    private final String USER_INFO_URL;
    private final String USER_BALANCE_URL;
    private final String USER_ORDER_URL;
    private final String USER_ORDER_DETAIl_URL;

    @Autowired
    private BiFunction<String, HashMap<String, String>, Mono<String>> apiCaller;

    public PrivateRequestHandler(ApiClient apiClient,
                                 @Value("${url.user.transactions}") String userTransactionsUrl,
                                 @Value("${url.user.info}") String userInfoUrl,
                                 @Value("${url.user.balance}") String userBalanceUrl,
                                 @Value("${url.user.orders}") String userOrderUrl,
                                 @Value("${url.user.order-detail}") String userOrderDetailUrl) {
        this.apiClient = apiClient;
        this.USER_TRANSACTIONS_URL = userTransactionsUrl;
        this.USER_ORDER_URL = userOrderUrl;
        this.USER_BALANCE_URL = userBalanceUrl;
        this.USER_ORDER_DETAIl_URL = userOrderDetailUrl;
        this.USER_INFO_URL = userInfoUrl;
    }

    public Mono<ServerResponse> getAccountInfo(ServerRequest request) {
        PrivateRequestDto requestDto = PrivateRequestDto.fromRequest(request);
        HashMap<String, String> params = new HashMap<>();
        params.put("order_currency", requestDto.getOrderCurrency());
        apiClient.setAccessInfo(requestDto.getApiKey(), requestDto.getSecretKey());
        return apiCaller.apply(USER_INFO_URL, params)
                .flatMap(response -> ServerResponse.ok().body(Mono.just(response), String.class))
                .onErrorResume(e -> ServerResponse.status(500).body(Mono.just("Error: " + e.getMessage()), String.class));
    }


    public Mono<ServerResponse> getTransactions(ServerRequest request) {
        PrivateRequestDto requestDto = PrivateRequestDto.fromRequest(request);
        HashMap<String, String> params = new HashMap<>();
        params.put("order_currency", requestDto.getOrderCurrency());
        params.put("payment_currency", requestDto.getOrderCurrency());
        apiClient.setAccessInfo(requestDto.getApiKey(), requestDto.getSecretKey());
        return apiCaller.apply(USER_TRANSACTIONS_URL, params)
                .flatMap(response -> ServerResponse.ok().body(Mono.just(response), String.class))
                .onErrorResume(e -> ServerResponse.status(500).body(Mono.just("Error: " + e.getMessage()), String.class));
    }

    public Mono<ServerResponse> getOrderList(ServerRequest request) {
        PrivateRequestDto requestDto = PrivateRequestDto.fromRequest(request);
        String cryptocurrency = request.pathVariable("cryptocurrency");
        HashMap<String, String> params = new HashMap<>();
        params.put("order_currency", cryptocurrency);
        apiClient.setAccessInfo(requestDto.getApiKey(), requestDto.getSecretKey());
        return apiCaller.apply(USER_ORDER_URL, params)
                .flatMap(response -> ServerResponse.ok().body(Mono.just(response), String.class))
                .onErrorResume(e -> ServerResponse.status(500).body(Mono.just("Error: " + e.getMessage()), String.class));
    }

    public Mono<ServerResponse> getOrderDetail(ServerRequest request) {
        PrivateRequestDto requestDto = PrivateRequestDto.fromRequest(request);
        String cryptocurrency = request.pathVariable("cryptocurrency");
        String orderId = request.pathVariable("orderid");
        HashMap<String, String> params = new HashMap<>();
        params.put("order_id", orderId);
        params.put("order_currency", cryptocurrency);
        apiClient.setAccessInfo(requestDto.getApiKey(), requestDto.getSecretKey());
        return apiCaller.apply(USER_ORDER_DETAIl_URL, params)
                .flatMap(response -> ServerResponse.ok().body(Mono.just(response), String.class))
                .onErrorResume(e -> ServerResponse.status(500).body(Mono.just("Error: " + e.getMessage()), String.class));
    }

    public Mono<ServerResponse> getBalanceInfo(ServerRequest request) {
        PrivateRequestDto requestDto = PrivateRequestDto.fromRequest(request);
        String cryptocurrency = request.pathVariable("cryptocurrency");
        HashMap<String, String> params = new HashMap<>();
        params.put("currency", cryptocurrency);
        apiClient.setAccessInfo(requestDto.getApiKey(), requestDto.getSecretKey());
        return apiCaller.apply(USER_BALANCE_URL, params)
                .flatMap(response -> ServerResponse.ok().body(Mono.just(response), String.class))
                .onErrorResume(e -> ServerResponse.status(500).body(Mono.just("Error: " + e.getMessage()), String.class));
    }
}
