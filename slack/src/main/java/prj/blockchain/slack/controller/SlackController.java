package prj.blockchain.slack.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import prj.blockchain.slack.config.SlackProperties;
import prj.blockchain.slack.util.SlackClient;

@Log4j2
@RequiredArgsConstructor
@RestController
public class SlackController {

    private final SlackClient slackClient;
    private final SlackProperties slackProperties;

    @PostMapping("/slack/manual")
    public String sendSlackMessage(@RequestParam String message) {
        log.info(this.getClass() + " executed");
        slackClient.sendMessage(slackProperties.getChannel().getNoti(), message);
        log.info(this.getClass() + " finished");
        return "Message sent to Slack";
    }
}
