package prj.blockchain.exchange.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import prj.blockchain.exchange.model.Network;

import java.io.IOException;
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
}
