package prj.blockchain.slack.router;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import prj.blockchain.slack.config.QueueProperties;
import prj.blockchain.slack.config.SlackProperties;
import prj.blockchain.slack.dto.CustomMessage;
import prj.blockchain.slack.util.SlackClient;
import java.util.Map;

@Log4j2
@Component
@RequiredArgsConstructor
public class SlackMessageRouter {

    private final QueueProperties queueProperties;
    private final SlackClient slackClient;
    private final SlackProperties slackProperties;

    public void receiveMessage(CustomMessage messageDto, @Header("amqp_receivedRoutingKey")String routingKey) {
        log.info("routing key : " + routingKey);
        log.info(messageDto.toString());

        for (QueueProperties.Task task: queueProperties.getTasks()) {
            Map<String, String> routingKeyMap = task.getRoutingKey();
            if(routingKeyMap.containsKey("warning")) {
                slackClient.sendMessage(slackProperties.getChannel().get("warning"), messageDto.getText());
                break;
            }else if (routingKeyMap.containsKey("alert")) {
                slackClient.sendMessage(slackProperties.getChannel().get("alert"), messageDto.getText());
                break;
            }
        }
    }
}
