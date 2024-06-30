package prj.blockchain.exchange.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Network {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("net_type")
    private String network;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("deposit_status")
    private String depositStatus;

    @JsonProperty("withdrawal_status")
    private String withdrawalStatus;

}
