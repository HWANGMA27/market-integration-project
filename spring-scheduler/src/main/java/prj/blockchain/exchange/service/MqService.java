package prj.blockchain.exchange.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import prj.blockchain.exchange.dto.CustomMessage;

@Log4j2
@RequiredArgsConstructor
@Service
public class MqService {
    private static final String exchangeName = "sch-exchange";
    private final RabbitTemplate rabbitTemplate;

    /**
     * 1. Queue 로 메세지를 발행
     * 2. Producer 역할 ->  Topic Exchange 전략
     **/
    public void sendMessage(CustomMessage messageDto, String routingKey) {
        log.info("-----------------------------");
        log.info(this.getClass() + ": Sending message...");
        this.rabbitTemplate.convertAndSend(exchangeName, routingKey, messageDto);
        log.info("-----------------------------");
    }
}
