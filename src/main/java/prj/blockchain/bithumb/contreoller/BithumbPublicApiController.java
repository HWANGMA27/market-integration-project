package prj.blockchain.bithumb.contreoller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import prj.blockchain.bithumb.service.BithumbApiService;

@AllArgsConstructor
@RestController
public class BithumbPublicApiController {

    private BithumbApiService bithumbApiService;

    @GetMapping("/ticker/{cryptocurrency}")
    public Mono<String> getTicker(@PathVariable String cryptocurrency) {
        return bithumbApiService.getTicker(cryptocurrency);
    }

    @GetMapping("/status/{cryptocurrency}")
    public Mono<String> getStatus(@PathVariable String cryptocurrency) {
        return bithumbApiService.getStatus(cryptocurrency);
    }

    @GetMapping("/network/status/{cryptocurrency}")
    public Mono<String> getNetworkStatus(@PathVariable String cryptocurrency) {
        return bithumbApiService.getNetworkStatus(cryptocurrency);
    }

}
