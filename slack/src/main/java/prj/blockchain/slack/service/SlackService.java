package prj.blockchain.slack.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import prj.blockchain.slack.dto.CustomMessage;
import prj.blockchain.slack.dto.SlackCustomMessage;

import java.util.Arrays;

@Log4j2
@Service
@AllArgsConstructor
public class SlackService {

    public String getSuccessSlackMessage(CustomMessage customMessage) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String star = ":star:";
        StringBuilder text = new StringBuilder();
        text.append(star)
                .append("*[")
                .append(customMessage.getApiName())
                .append("] ")
                .append("Completed ").append(customMessage.getAction()).append(" ").append(customMessage.getTargetTable()).append("* ")
                .append(star)
                .append("\n").append(customMessage.getAction()).append(" Data: ")
                .append(customMessage.getAffectedData())
                .append(" rows");

        // DTO 객체 생성
        SlackCustomMessage.Block divider1 = new SlackCustomMessage.Block("divider");
        SlackCustomMessage.Text sectionText = new SlackCustomMessage.Text("mrkdwn", text.toString());
        SlackCustomMessage.Block section = new SlackCustomMessage.Block("section", sectionText);
        SlackCustomMessage.Block divider2 = new SlackCustomMessage.Block("divider");

        SlackCustomMessage message = new SlackCustomMessage(Arrays.asList(divider1, section, divider2));

        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL); // null 값을 포함하지 않도록 설정
        return objectMapper.writeValueAsString(message);
    }

    public String getFailSlackMessage(CustomMessage customMessage) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String star = ":ghost:";
        StringBuilder text = new StringBuilder();
        text.append(star)
                .append("*[")
                .append(customMessage.getApiName())
                .append("] ")
                .append("Failed ").append(customMessage.getAction()).append(" ").append(customMessage.getTargetTable()).append("* ")
                .append(star);

        // DTO 객체 생성
        SlackCustomMessage.Block divider1 = new SlackCustomMessage.Block("divider");
        SlackCustomMessage.Text sectionText = new SlackCustomMessage.Text("mrkdwn", text.toString());
        SlackCustomMessage.Block section = new SlackCustomMessage.Block("section", sectionText);
        SlackCustomMessage.Block divider2 = new SlackCustomMessage.Block("divider");

        SlackCustomMessage message = new SlackCustomMessage(Arrays.asList(divider1, section, divider2));

        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL); // null 값을 포함하지 않도록 설정
        return objectMapper.writeValueAsString(message);
    }
}
