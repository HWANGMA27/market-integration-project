package prj.blockchain.exchange.task;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import prj.blockchain.exchange.service.NetworkService;

@Log4j2
@Component
@RequiredArgsConstructor
public class ScheduledTasks {

    private final NetworkService networkService;
    private String targetNetwork = "all";

    @Scheduled(cron = "${scheduler.daily-check}")
    public void performScheduledTask() {
        log.info(this.getClass() + " executed");
        networkService.deleteAllAndSaveNetworkData(targetNetwork)
                .doOnError(error -> log.error("Error: " + error.getMessage()))
                .subscribe();
        log.info(this.getClass() + " finished");
    }
}