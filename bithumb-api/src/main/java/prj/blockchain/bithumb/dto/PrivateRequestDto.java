package prj.blockchain.bithumb.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.reactive.function.server.ServerRequest;

@Data
@EqualsAndHashCode(callSuper = true)
public class PrivateRequestDto extends ApiCredentialsDto {
    private String orderCurrency;
    private String paymentCurrency;

    @Builder
    public PrivateRequestDto(String apiKey, String secretKey,
                             String orderCurrency, String paymentCurrency) {
        super(apiKey, secretKey);
        this.orderCurrency = orderCurrency;
        this.paymentCurrency = paymentCurrency;
    }

    public static PrivateRequestDto fromRequest(ServerRequest request) {
        return PrivateRequestDto.builder()
                .apiKey(request.headers().firstHeader("api-key"))
                .secretKey(request.headers().firstHeader("api-secret"))
                .orderCurrency(request.queryParam("orderCurrency").orElse(""))
                .paymentCurrency(request.queryParam("paymentCurrency").orElse(""))
                .build();
    }
}
