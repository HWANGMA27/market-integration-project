package prj.blockchain.slack.util;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import prj.blockchain.slack.dto.SlackPayload;

@RequiredArgsConstructor
@Component
public class SlackClient {

    private final WebClient webClient;

    public void sendMessage(String webhookUrl, String message) {
        this.webClient.post()
                .uri(webhookUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new SlackPayload(message))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
