package contreoller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import service.BithumbApiService;

@AllArgsConstructor
@RestController
public class BithumbPublicApiController {

    private BithumbApiService bithumbApiService;

    @GetMapping("/ticker/{cryptocurrency}")
    public Mono<String> getTicker(@PathVariable String cryptocurrency) {
        return bithumbApiService.getTicker(cryptocurrency);
    }

}
