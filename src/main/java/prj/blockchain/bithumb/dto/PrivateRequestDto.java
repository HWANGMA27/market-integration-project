package prj.blockchain.bithumb.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.reactive.function.server.ServerRequest;

@Data
public class PrivateRequestDto {
    private String apiKey;
    private String secretKey;
    private String orderCurrency;
    private String paymentCurrency;

    @Builder
    public PrivateRequestDto(String apiKey, String secretKey, String orderCurrency, String paymentCurrency) {
        this.apiKey = apiKey;
        this.secretKey = secretKey;
        this.orderCurrency = orderCurrency;
        this.paymentCurrency = paymentCurrency;
    }

    public static PrivateRequestDto fromRequest(ServerRequest request) {
        return PrivateRequestDto.builder()
                .apiKey(request.headers().firstHeader("api-key"))
                .secretKey(request.headers().firstHeader("api-secret"))
                .orderCurrency(request.queryParam("order-currency").orElse(""))
                .paymentCurrency(request.queryParam("payment-currency").orElse(""))
                .build();
    }
}
