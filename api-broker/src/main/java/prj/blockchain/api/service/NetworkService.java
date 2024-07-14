package prj.blockchain.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import prj.blockchain.api.config.ApiProperties;
import prj.blockchain.api.model.Network;
import prj.blockchain.api.repository.NetworkRepository;
import prj.blockchain.api.util.JsonResponseConvert;
import reactor.core.publisher.Mono;

import java.util.List;

@Log4j2
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class NetworkService {
    private final WebClient webClient;
    private final ApiProperties endPoint;
    private final JsonResponseConvert jsonResponseConvert;
    private final NetworkRepository networkRepository;

    public Mono<List<Network>> fetchNetworkData(String targetNetwork) {
        String getPriceEndPoint = String.join("/", endPoint.getNetworkStatus(), targetNetwork);
        return webClient.get()
                .uri(getPriceEndPoint)
                .retrieve()
                .bodyToMono(String.class)
                .map(jsonResponseConvert::networkResponseMapping)
                .doOnError(error -> System.err.println("Error: " + error.getMessage()));
    }

    @Transactional
    public Mono<Void> deleteAllAndSaveNetworkData(String targetNetwork) {
        return Mono.fromRunnable(() -> {
            networkRepository.deleteAll();
            log.info("All network data deleted.");
        }).then(fetchNetworkData(targetNetwork)
                .flatMap(networkResponses -> {
                    networkRepository.saveAll(networkResponses);
                    log.info("Saved " + networkResponses.size() + " network responses to the database.");
                    return Mono.empty();
                }));
    }
}
