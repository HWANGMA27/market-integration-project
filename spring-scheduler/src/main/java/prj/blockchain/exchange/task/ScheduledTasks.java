package prj.blockchain.exchange.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {
    @Scheduled(cron = "* *  * * ?")
    public void reportCurrentTimeWithCron() {
        System.out.println("The time is now " + new java.util.Date());
    }
}