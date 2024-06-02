package prj.blockchain.bithumb.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.reactive.function.server.ServerRequest;

@Data
@EqualsAndHashCode(callSuper = true)
public class PrivateTradeRequestDto extends ApiCredentialsDto {
    private String orderCurrency;
    private String paymentCurrency;

    @Builder
    public PrivateTradeRequestDto(String apiKey, String secretKey, String orderCurrency, String paymentCurrency) {
        super(apiKey, secretKey);
        this.orderCurrency = orderCurrency;
        this.paymentCurrency = paymentCurrency;
    }

    public static PrivateTradeRequestDto fromRequest(ServerRequest request) {
        return PrivateTradeRequestDto.builder()
                .apiKey(request.headers().firstHeader("api-key"))
                .secretKey(request.headers().firstHeader("api-secret"))
                .orderCurrency(request.queryParam("order-currency").orElse(""))
                .paymentCurrency(request.queryParam("payment-currency").orElse(""))
                .build();
    }
}
