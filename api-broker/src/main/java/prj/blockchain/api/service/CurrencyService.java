package prj.blockchain.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Log4j2
@RequiredArgsConstructor
@Service
public class CurrencyService {
    private final BithumbAPIService bithumbAPIService;

    public void deleteAllAndSaveCurrencyData() {
        bithumbAPIService.updateCurrencyData();
    }

}