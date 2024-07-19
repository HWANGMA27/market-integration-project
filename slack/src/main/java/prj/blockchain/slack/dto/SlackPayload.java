package prj.blockchain.slack.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SlackPayload {
    private String text;
}
