package prj.blockchain.api.task;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.config.DirectRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.listener.MethodRabbitListenerEndpoint;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.stereotype.Component;
import prj.blockchain.api.config.QueueProperties;
import prj.blockchain.api.dto.CustomMessage;
import prj.blockchain.api.model.User;
import prj.blockchain.api.service.BalanceHistoryService;
import prj.blockchain.api.service.CurrencyService;
import prj.blockchain.api.service.NetworkService;
import prj.blockchain.api.service.UserService;

import java.util.List;
import java.lang.reflect.Method;

@Log4j2
@Component
@RequiredArgsConstructor
@Configuration
public class ScheduledTasks implements RabbitListenerConfigurer {

    private final QueueProperties queueProperties;
    private final UserService userService;
    private final NetworkService networkService;
    private final CurrencyService currencyService;
    private final BalanceHistoryService balanceHistoryService;
    private final DirectRabbitListenerContainerFactory factory;
    private final DefaultMessageHandlerMethodFactory messageHandlerMethodFactory;
    private RabbitListenerEndpointRegistrar registrar;

    private final String targetNetwork = "all";

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
        for (QueueProperties.Task task : queueProperties.getTasks()) {
            registerListener(registrar, task);
        }
    }

    private void registerListener(RabbitListenerEndpointRegistrar registrar, QueueProperties.Task task) {
        for (String key : task.getRoutingKey().keySet()) {
            String queueName = task.getName();
            String routingKey = task.getRoutingKey().get(key);

            Method method;
            try {
                method = this.getClass().getMethod("receiveMessage", CustomMessage.class, String.class);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }

            MethodRabbitListenerEndpoint endpoint = new MethodRabbitListenerEndpoint();
            endpoint.setId(queueName + "-" + key);
            endpoint.setMethod(method);
            endpoint.setBean(this);
            endpoint.setQueueNames(queueName);
            endpoint.setExclusive(false);
            endpoint.setMessageHandlerMethodFactory(messageHandlerMethodFactory);

            registrar.registerEndpoint(endpoint, factory);
        }
    }

    public void receiveMessage(CustomMessage messageDto, @org.springframework.messaging.handler.annotation.Header("amqp_receivedRoutingKey") String routingKey) {
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