package prj.blockchain.api.task;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import prj.blockchain.api.dto.CustomMessage;
import prj.blockchain.api.model.User;
import prj.blockchain.api.service.BalanceHistoryService;
import prj.blockchain.api.service.NetworkService;
import prj.blockchain.api.service.UserService;

import java.util.List;

@Log4j2
@Component
@RequiredArgsConstructor
public class ScheduledTasks {
    private final UserService userService;
    private final NetworkService networkService;
    private final BalanceHistoryService balanceHistoryService;
    private final String queueName = "sch-queue";
    private final String targetNetwork = "all";

    @RabbitListener(queues = queueName)
    public void receiveMessage(CustomMessage messageDto, @Header("amqp_receivedRoutingKey") String routingKey) {
        log.info("routing key : " + routingKey);
        // MessageListenerAdapter 추가하여 if문 제거 예정
        if (routingKey.equals("sch.exchange.network")) {
            executeGetNetworkData();
        } else if (routingKey.equals("sch.exchange.balance")) {
            executeGetDailyUserBalance();
        }
    }

    public void executeGetNetworkData() {
        log.info(this.getClass() + " executed");
        networkService.deleteAllAndSaveNetworkData(targetNetwork)
                .doOnError(error -> log.error("Error: " + error.getMessage()))
                .subscribe();
        log.info(this.getClass() + " finished");
    }

    public void executeGetDailyUserBalance() {
        log.info(this.getClass() + " executed");
        List<User> allUser = userService.findAllUser();
        for (User user: allUser) {
            try {
                    balanceHistoryService.saveUserBalanceHistory(user, targetNetwork);
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
        }
        log.info(this.getClass() + " finished");
    }
}