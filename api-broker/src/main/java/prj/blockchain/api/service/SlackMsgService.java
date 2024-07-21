package prj.blockchain.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import prj.blockchain.api.config.mq.QueueProperties;
import prj.blockchain.api.dto.CustomMessage;

@Log4j2
@RequiredArgsConstructor
@Service
public class SlackMsgService {
    private final QueueProperties queueProperties;
    private final RabbitTemplate rabbitTemplate;

    public void sendMessage(CustomMessage messageDto, String routingKey) {
        log.info(this.getClass() + ": Sending message...");
        this.rabbitTemplate.convertAndSend(queueProperties.getExchange(), routingKey, messageDto);
        log.info(this.getClass() + " finished");
    }
}
