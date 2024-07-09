package prj.blockchain.exchange.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import prj.blockchain.exchange.model.BalanceHistory;
import prj.blockchain.exchange.model.Network;
import prj.blockchain.exchange.model.User;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Component
public class JsonResponseConvert {

    private final ObjectMapper objectMapper;

    public List<Network> networkResponseMapping(String response) {
    System.out.println(response);
        List<Network> networkList = new ArrayList<>();
        try {
            log.info(this.getClass() + " executed");
            JsonNode root = objectMapper.readTree(response);
            if (root.has("data")) {
                JsonNode dataArray = root.get("data");
                if (dataArray.isArray()) {
                    for (JsonNode dataNode : dataArray) {
                        Network network = objectMapper.treeToValue(dataNode, Network.class);
                        networkList.add(network);
                    }
                }
                networkList.forEach(network -> log.info("Network: " + network.getNetwork() + ", Network: " + network.getCurrency() + ", DepositStatus: "
                        + network.getDepositStatus() + ", WithdrawalStatus: " + network.getWithdrawalStatus()));
            }
            log.info(this.getClass() + " finished");
        } catch (IOException e) {
            log.error(this.getClass() + " error msg : " + e.getMessage());
        }
        return networkList;
    }

    public List<BalanceHistory> balanceResponseMapping(User user, String currency, String response) {
        System.out.println(response);
        List<BalanceHistory> balanceHistoryList = new ArrayList<>();
        try {
            log.info(this.getClass() + " executed");
            JsonNode root = objectMapper.readTree(response);
            if (root.has("data")) {
                JsonNode dataNode = root.get("data");

                BalanceHistory balanceHistory = BalanceHistory.builder()
                        .user(user)
                        .totalKrw(dataNode.get("total_krw").asDouble())
                        .inUseKrw(dataNode.get("in_use_krw").asDouble())
                        .availableKrw(dataNode.get("available_krw").asDouble())
                        .totalAsset(new BigDecimal(dataNode.get("total_".concat(currency)).asText()))
                        .inUseAsset(new BigDecimal(dataNode.get("in_use_".concat(currency)).asText()))
                        .availableAsset(new BigDecimal(dataNode.get("available_".concat(currency)).asText()))
                        .xcoinLast(new BigDecimal(dataNode.get("xcoin_last_".concat(currency)).asText()))
                        .assetType(currency).build();
                balanceHistoryList.add(balanceHistory);
                balanceHistoryList.forEach(balanceHistoryItem -> log.info("BalanceHistory: " + balanceHistoryItem.toString()));
            }
            log.info(this.getClass() + " finished");
        } catch (IOException e) {
            log.error(this.getClass() + " error msg : " + e.getMessage());
        }
        return balanceHistoryList;
    }

}
