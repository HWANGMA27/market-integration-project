package prj.blockchain.slack.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import prj.blockchain.slack.config.SlackProperties;
import prj.blockchain.slack.dto.CustomMessage;
import prj.blockchain.slack.service.SlackService;
import prj.blockchain.slack.util.SlackClient;

@Log4j2
@RequiredArgsConstructor
@RestController
public class SlackController {

    private final SlackClient slackClient;
    private final SlackProperties slackProperties;
    private final SlackService slackService;

    @PostMapping("/slack/manual")
    public void sendSlackMessage(@RequestBody CustomMessage messageDto) {
        try {
            if(messageDto.isSuccess()) {
                slackClient.sendMessage(slackProperties.getChannel().get("alert"), slackService.getSuccessSlackMessage(messageDto));
            }else {
                slackClient.sendMessage(slackProperties.getChannel().get("warning"), slackService.getFailSlackMessage(messageDto));
            }
        } catch (JsonProcessingException e) {
            log.error("Error occurred while get slack message : " + e.getMessage());
        }
    }
}
