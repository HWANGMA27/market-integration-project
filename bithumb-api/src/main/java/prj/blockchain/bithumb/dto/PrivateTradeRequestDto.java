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
    private String units;
    private String price;
    private String type;

    @Builder
    public PrivateTradeRequestDto(String apiKey, String secretKey, String orderCurrency, String paymentCurrency,
                                  String units, String price, String type) {
        super(apiKey, secretKey);
        this.orderCurrency = orderCurrency;
        this.paymentCurrency = paymentCurrency;
        this.units = units;
        this.price = price;
        this.type = type;
    }

    public static PrivateTradeRequestDto fromRequest(ServerRequest request) {
        return PrivateTradeRequestDto.builder()
                .apiKey(request.headers().firstHeader("api-key"))
                .secretKey(request.headers().firstHeader("api-secret"))
                .orderCurrency(request.queryParam("orderCurrency").orElse(""))
                .paymentCurrency(request.queryParam("paymentCurrency").orElse(""))
                .price(request.queryParam("price").orElse(""))
                .units(request.queryParam("units").orElse(""))
                .type(request.queryParam("type").orElseThrow())
                .build();
    }
}
