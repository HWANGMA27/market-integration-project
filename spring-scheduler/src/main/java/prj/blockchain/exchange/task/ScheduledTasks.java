package prj.blockchain.exchange.task;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import prj.blockchain.exchange.dto.CustomMessage;
import prj.blockchain.exchange.service.MqService;

@Log4j2
@Component
@RequiredArgsConstructor
public class ScheduledTasks {
    private final MqService mqService;
    private final String networkCheckRoutingKey = "sch.exchange.network";
    private final String balanceCheckRoutingKey = "sch.exchange.balance";

    @Scheduled(cron = "${scheduler.sec-check}")
    public void executeGetNetworkData() {
        log.info(this.getClass() + " executed");
        CustomMessage customMessage = new CustomMessage(this.getClass().getName(), this.getClass().getName());
        mqService.sendMessage(customMessage, networkCheckRoutingKey);
        log.info(this.getClass() + " finished");
    }
//
    @Scheduled(cron = "${scheduler.minute-check}")
    public void executeGetDailyUserBalance() {
        log.info(this.getClass() + " executed");
        CustomMessage customMessage = new CustomMessage(this.getClass().getName(), this.getClass().getName());
        mqService.sendMessage(customMessage, balanceCheckRoutingKey);
        log.info(this.getClass() + " finished");
    }
}