package prj.blockchain.exchange.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    @Scheduled(cron = "${scheduler.cron}")
    public void performScheduledTask() {
        System.out.println("Scheduled task is running...");
    }
}