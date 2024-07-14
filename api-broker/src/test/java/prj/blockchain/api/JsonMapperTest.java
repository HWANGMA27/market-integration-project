package prj.blockchain.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import prj.blockchain.api.model.Network;
import prj.blockchain.api.util.JsonResponseConvert;
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
