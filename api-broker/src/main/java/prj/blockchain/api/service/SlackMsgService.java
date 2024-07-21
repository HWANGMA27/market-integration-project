package prj.blockchain.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import prj.blockchain.api.config.mq.QueueConfig;
import prj.blockchain.api.config.mq.QueueProperties;
import prj.blockchain.api.dto.CustomMessage;
import prj.blockchain.api.exception.NotFoundQueueException;

@Log4j2
@RequiredArgsConstructor
@Service
public class SlackMsgService {
    private final QueueProperties queueProperties;
    private final RabbitTemplate rabbitTemplate;
    private final String slackQueueName = "slack-queue";

    public QueueProperties.Task getSlackQueueTask() {
        return queueProperties.getTaskByName(slackQueueName).orElseThrow(() -> new NotFoundQueueException(slackQueueName + " is not exist"));
    }

    public void sendMessage(CustomMessage messageDto, String routingKey) {
        log.info(this.getClass() + ": Sending message...");
        this.rabbitTemplate.convertAndSend(queueProperties.getExchange(), routingKey, messageDto);
        log.info(this.getClass() + " finished");
    }
}
