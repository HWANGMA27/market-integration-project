package prj.blockchain.exchange.task;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
public class ScheduledTasks {
//    private final UserService userService;
//    private final NetworkService networkService;
//    private final BalanceHistoryService balanceHistoryService;
//    private String targetNetwork = "all";
//
//    @Scheduled(cron = "${scheduler.daily-check}")
//    public void executeGetNetworkData() {
//        log.info(this.getClass() + " executed");
//        networkService.deleteAllAndSaveNetworkData(targetNetwork)
//                .doOnError(error -> log.error("Error: " + error.getMessage()))
//                .subscribe();
//        log.info(this.getClass() + " finished");
//    }
//
//    @Scheduled(cron = "${scheduler.daily-check}")
//    public void executeGetDailyUserBalance() {
//        log.info(this.getClass() + " executed");
//        List<User> allUser = userService.findAllUser();
//        for (User user: allUser) {
//            try {
//                    balanceHistoryService.saveUserBalanceHistory(user, targetNetwork);
//                } catch (Exception e) {
//                    log.error(e.getMessage());
//                }
//        }
//        log.info(this.getClass() + " finished");
//    }
}