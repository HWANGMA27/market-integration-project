package prj.blockchain.bithumb.handler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import prj.blockchain.bithumb.dto.PrivateRequestDto;
import prj.blockchain.bithumb.util.ApiClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;

@Component
public class PrivateRequestHandler {
    private final ApiClient apiClient;
    private final String USER_TRANSACTIONS_URL;
    private final String USER_INFO_URL;

    public PrivateRequestHandler(ApiClient apiClient,
                                 @Value("${url.bithumb.user.transactions}") String userTransactionsUrl,
                                 @Value("${url.bithumb.user.info}") String userInfoUrl) {
        this.apiClient = apiClient;
        this.USER_TRANSACTIONS_URL = userTransactionsUrl;
        this.USER_INFO_URL = userInfoUrl;
    }

    public Mono<ServerResponse> getAccountInfo(ServerRequest request) {
        PrivateRequestDto requestDto = PrivateRequestDto.fromRequest(request);
        HashMap<String, String> params = new HashMap<>();
        params.put("order_currency", requestDto.getOrderCurrency());
        apiClient.setAccessInfo(requestDto.getApiKey(), requestDto.getSecretKey());
        String response = apiClient.callApi(USER_INFO_URL, params);
        return ServerResponse.ok().body(Mono.just(response), String.class);
    }


    public Mono<ServerResponse> getTransactions(ServerRequest request) {
        PrivateRequestDto requestDto = PrivateRequestDto.fromRequest(request);
        HashMap<String, String> params = new HashMap<>();
        params.put("order_currency", requestDto.getOrderCurrency());
        params.put("payment_currency", requestDto.getOrderCurrency());
        apiClient.setAccessInfo(requestDto.getApiKey(), requestDto.getSecretKey());
        String response = apiClient.callApi(USER_TRANSACTIONS_URL, params);
        return ServerResponse.ok().body(Mono.just(response), String.class);
    }
}
