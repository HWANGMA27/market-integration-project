package prj.blockchain.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import prj.blockchain.api.config.BithumbApiProperties;
import prj.blockchain.api.model.Currency;
import prj.blockchain.api.model.Exchange;
import prj.blockchain.api.model.Network;
import prj.blockchain.api.repository.CurrencyRepository;
import prj.blockchain.api.repository.NetworkRepository;
import prj.blockchain.api.util.BithumbJsonResponseConvert;
import reactor.core.publisher.Mono;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Service
public class BithumbAPIService {

    private final WebClient webClient;
    private final BithumbApiProperties bithumbApiProperties;
    private final BithumbJsonResponseConvert jsonResponseConvert;
    private final NetworkRepository networkRepository;
    private final CurrencyRepository currencyRepository;
    private final Exchange exchange = Exchange.BITHUMB;

    @Transactional
    public void updateCurrencyData() {
        currencyRepository.deleteByExchange(exchange);
        log.info(exchange + " currency data deleted.");

        fetchCurrencyData().subscribe(currencyResponses -> {
            currencyRepository.saveAll(currencyResponses);
            log.info("Saved " + currencyResponses.size() + " currency responses to the database.");
        });
    }

    public Mono<List<Currency>> fetchCurrencyData() {
        String getCurrencyEndPoint = bithumbApiProperties.getEndpoint().getCurrency();
        return webClient.get()
                .uri(getCurrencyEndPoint)
                .retrieve()
                .bodyToMono(String.class)
                .map(jsonResponseConvert::currencyResponseMapping)
                .doOnError(error -> log.error("Error: " + error.getMessage()));
    }

    @Transactional
    public void updateNetworkData(String targetNetwork) {
        networkRepository.deleteByExchange(exchange);
        log.info(exchange + " network data deleted.");

        fetchNetworkData(targetNetwork).subscribe(networkResponses -> {
            networkRepository.saveAll(networkResponses);
            log.info("Saved " + networkResponses.size() + " network responses to the database.");
        });
    }

    public Mono<List<Network>> fetchNetworkData(String targetNetwork) {
        String getPriceEndPoint = String.join("/", bithumbApiProperties.getEndpoint().getNetwork(), targetNetwork);
        return webClient.get()
                .uri(getPriceEndPoint)
                .retrieve()
                .bodyToMono(String.class)
                .map(jsonResponseConvert::networkResponseMapping)
                .doOnError(error -> log.error("Error: " + error.getMessage()));
    }
}
