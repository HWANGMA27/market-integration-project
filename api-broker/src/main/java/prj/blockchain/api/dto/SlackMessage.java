package prj.blockchain.api.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SlackMessage {

    private String text;

    @Override
    public String toString() {
        return "SlackMessage{" +
                "text='" + text + '\'' +
                '}';
    }
}