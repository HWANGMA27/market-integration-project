package prj.blockchain.bithumb.handler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import prj.blockchain.bithumb.dto.PrivateTradeRequestDto;
import prj.blockchain.bithumb.util.ApiClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;

@Configuration
public class PrivateTradeRequestHandler {
    private final ApiClient apiClient;
    private final String USER_TRADE_PLACE_URL;
    private final String USER_ORDER_CANCEL_URL;

    public PrivateTradeRequestHandler(ApiClient apiClient,
                                 @Value("${url.bithumb.exchange.trade}") String tradePalaceUrl,
                                 @Value("${url.bithumb.exchange.trade-cancel}") String tradeCancelUrl) {
        this.apiClient = apiClient;
        this.USER_TRADE_PLACE_URL = tradePalaceUrl;
        this.USER_ORDER_CANCEL_URL = tradeCancelUrl;
    }

    public Mono<ServerResponse> placeOrder(ServerRequest request) {
        PrivateTradeRequestDto requestDto = PrivateTradeRequestDto.fromRequest(request);
        HashMap<String, String> params = new HashMap<>();
        params.put("order_currency", requestDto.getOrderCurrency());
        params.put("payment_currency", requestDto.getOrderCurrency());
        params.put("price", requestDto.getPrice());
        params.put("units", requestDto.getUnits());
        params.put("type", requestDto.getType());
        apiClient.setAccessInfo(requestDto.getApiKey(), requestDto.getSecretKey());
        String response = apiClient.callApi(USER_TRADE_PLACE_URL, params);
        return ServerResponse.ok().body(Mono.just(response), String.class);
    }

    public Mono<ServerResponse> cancelOrder(ServerRequest request) {
        PrivateTradeRequestDto requestDto = PrivateTradeRequestDto.fromRequest(request);
        HashMap<String, String> params = new HashMap<>();
        String orderId = request.pathVariable("orderid");
        params.put("type", requestDto.getType());
        params.put("order_id", orderId);
        params.put("order_currency", requestDto.getOrderCurrency());
        params.put("payment_currency", requestDto.getOrderCurrency());
        apiClient.setAccessInfo(requestDto.getApiKey(), requestDto.getSecretKey());
        String response = apiClient.callApi(USER_ORDER_CANCEL_URL, params);
        return ServerResponse.ok().body(Mono.just(response), String.class);
    }
}
