package prj.blockchain.exchange.task;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import prj.blockchain.exchange.config.ApiProperties;

@Component
@RequiredArgsConstructor
public class ScheduledTasks {

    private final WebClient webClient;
    private final ApiProperties endPoint;
    private final String targetCurrency = "btc";
    private final String paymentCurrency = "krw";

    @Scheduled(cron = "${scheduler.cron}")
    public void performScheduledTask() {
        String getPriceEndPoint = String.join("/", endPoint.getPrice(), targetCurrency, paymentCurrency);
        webClient.get()
                .uri(getPriceEndPoint)
                .retrieve()
                .bodyToMono(String.class)
                .doOnNext(response -> System.out.println("Response: " + response))
                .doOnError(error -> System.err.println("Error: " + error.getMessage()))
                .subscribe();
    }
}