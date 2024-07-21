package prj.blockchain.api.task;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import prj.blockchain.api.config.mq.QueueProperties;
import prj.blockchain.api.dto.CustomMessage;
import prj.blockchain.api.model.User;
import prj.blockchain.api.service.BalanceHistoryService;
import prj.blockchain.api.service.CurrencyService;
import prj.blockchain.api.service.NetworkService;
import prj.blockchain.api.service.UserService;
import java.util.List;

@Log4j2
@Component
@RequiredArgsConstructor
public class ScheduledTasks {

    private final QueueProperties queueProperties;
    private final UserService userService;
    private final NetworkService networkService;
    private final CurrencyService currencyService;
    private final BalanceHistoryService balanceHistoryService;

    private final String targetNetwork = "all";

    public void receiveMessage(CustomMessage messageDto, @Header("amqp_receivedRoutingKey") String routingKey) {
        log.info("routing key : " + routingKey);

        for (QueueProperties.Task task : queueProperties.getTasks()) {
            if (task.getRoutingKey().containsValue(routingKey)) {
                if (routingKey.equals(task.getRoutingKey().get("network"))) {
                    executeGetNetworkData();
                } else if (routingKey.equals(task.getRoutingKey().get("balance"))) {
                    executeGetDailyUserBalance();
                } else if (routingKey.equals(task.getRoutingKey().get("currency"))) {
                    executeGetCurrencyData();
                }
                break;
            }
        }
    }

    public void executeGetNetworkData() {
        log.info(this.getClass() + " executed");
        networkService.deleteAllAndSaveNetworkData(targetNetwork);
        log.info(this.getClass() + " finished");
    }

    public void executeGetCurrencyData() {
        log.info(this.getClass() + " executed");
        currencyService.deleteAllAndSaveCurrencyData();
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