package prj.blockchain.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Network extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Exchange exchange;

    @JsonProperty("net_type")
    private String network;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("deposit_status")
    private String depositStatus;

    @JsonProperty("withdrawal_status")
    private String withdrawalStatus;

    public void updateExchangeInfo(Exchange exchange) {
        this.exchange = exchange;
    }

}
