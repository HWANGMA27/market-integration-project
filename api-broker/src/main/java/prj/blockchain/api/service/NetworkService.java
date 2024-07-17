package prj.blockchain.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Log4j2
@RequiredArgsConstructor
@Service
public class NetworkService {
    private final BithumbAPIService bithumbAPIService;

    public void deleteAllAndSaveNetworkData(String targetNetwork) {
//        Mono<Void> upbitMono = upbitAPIService.updateBithumbNetworkData(targetNetwork);
        bithumbAPIService.updateNetworkData(targetNetwork);
    }

}
