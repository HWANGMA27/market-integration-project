package prj.blockchain.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import prj.blockchain.api.config.BithumbApiProperties;
import prj.blockchain.api.model.Exchange;
import prj.blockchain.api.model.Network;
import prj.blockchain.api.repository.NetworkRepository;
import prj.blockchain.api.util.BithumbJsonResponseConvert;
import reactor.core.publisher.Mono;

import java.util.List;

@Log4j2
@Transactional
@RequiredArgsConstructor
@Service
public class BithumbAPIService {

    private final WebClient webClient;
    private final BithumbApiProperties bithumbApiProperties;
    private final BithumbJsonResponseConvert jsonResponseConvert;
    private final NetworkRepository networkRepository;
    private final Exchange exchange = Exchange.BITHUMB;

    public Mono<Void> updateBithumbNetworkData(String targetNetwork) {
        return Mono.fromRunnable(() -> {
            networkRepository.deleteAll();
            networkRepository.deleteByExchange(exchange);
            log.info("Bithumb network data deleted.");
        }).then(fetchNetworkData(targetNetwork)
                .flatMap(networkResponses -> {
                    networkRepository.saveAll(networkResponses);
                    log.info("Saved " + networkResponses.size() + " network responses to the database.");
                    return Mono.empty();
                }));
    }

    public Mono<List<Network>> fetchNetworkData(String targetNetwork) {
        String getPriceEndPoint = String.join("/", bithumbApiProperties.getEndpoint().getNetworkStatus(), targetNetwork);
        return webClient.get()
                .uri(getPriceEndPoint)
                .retrieve()
                .bodyToMono(String.class)
                .map(jsonResponseConvert::networkResponseMapping)
                .doOnError(error -> log.error("Error: " + error.getMessage()));
    }
}
