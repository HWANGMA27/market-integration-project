package prj.blockchain.exchange.model;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class BalanceHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private String status;

    private Double totalKrw;

    private Double inUseKrw;

    private Double availableKrw;

    private Double totalAsset;

    private Double inUseAsset;

    private Double availableAsset;

    private Double xcoinLast;

    private String assetType;
}