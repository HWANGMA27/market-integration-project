package prj.blockchain.slack.router;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import prj.blockchain.slack.config.QueueProperties;
import prj.blockchain.slack.config.SlackProperties;
import prj.blockchain.slack.dto.CustomMessage;
import prj.blockchain.slack.service.SlackService;
import prj.blockchain.slack.util.SlackClient;
import java.util.Map;

@Log4j2
@Component
@RequiredArgsConstructor
public class SlackMessageRouter {

    private final QueueProperties queueProperties;
    private final SlackClient slackClient;
    private final SlackProperties slackProperties;
    private final SlackService slackService;

    public void receiveMessage(CustomMessage messageDto, @Header("amqp_receivedRoutingKey")String routingKey) {
        log.info("routing key : " + routingKey);
        log.info(messageDto.toString());

        try {
            for (QueueProperties.Task task: queueProperties.getTasks()) {
                Map<String, String> routingKeyMap = task.getRoutingKey();
                String message;
                if(routingKeyMap.containsKey("warning")) {
                    if(messageDto.isSuccess()) message = slackService.getSuccessSlackMessage(messageDto);
                    else message = slackService.getFailSlackMessage(messageDto);
                    slackClient.sendMessage(slackProperties.getChannel().get("warning"), message);
                    break;
                }else if (routingKeyMap.containsKey("alert")) {
                    if(messageDto.isSuccess()) message = slackService.getSuccessSlackMessage(messageDto);
                    else message = slackService.getFailSlackMessage(messageDto);
                    slackClient.sendMessage(slackProperties.getChannel().get("alert"), message);
                    break;
                }
            }
        } catch (JsonProcessingException e) {
            log.error("Error occurred while get slack message : " + e.getMessage());
        }
    }
}
