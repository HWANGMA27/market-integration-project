package prj.blockchain.exchange;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import prj.blockchain.exchange.model.Network;
import prj.blockchain.exchange.task.ScheduledTasks;
import prj.blockchain.exchange.util.JsonResponseConvert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class JsonMapperTest {

    @Autowired
    private JsonResponseConvert jsonResponseConvert;

    @Autowired
    private ObjectMapper objectMapper;

    String jsonResponse = "{\"status\":\"0000\",\"data\":[{\"currency\":\"BTC\",\"net_type\":\"BTC\",\"deposit_status\":1,\"withdrawal_status\":1}]}";

    @Test
    public void jsonConvertTest() {
        List<Network> networks = jsonResponseConvert.networkResponseMapping(jsonResponse);
        assertEquals(1, networks.size());
    }
}
