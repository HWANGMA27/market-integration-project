package prj.blockchain.api.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class BalanceHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;


    private String assetType;

    @Column(precision = 38, scale = 18)
    private BigDecimal totalAsset;

    @Column(precision = 38, scale = 18)
    private BigDecimal inUseAsset;

    @Column(precision = 38, scale = 18)
    private BigDecimal availableAsset;
}