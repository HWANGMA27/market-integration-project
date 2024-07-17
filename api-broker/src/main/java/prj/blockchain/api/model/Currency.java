package prj.blockchain.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Currency extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Exchange exchange;

    @JsonProperty("net_name")
    private String network;

    @JsonProperty("net_type")
    private String currency;

    public void updateExchangeInfo(Exchange exchange) {
        this.exchange = exchange;
    }
}
