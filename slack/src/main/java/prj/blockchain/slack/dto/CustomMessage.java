package prj.blockchain.slack.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomMessage {

    private String className;

    private String text;

    @Override
    public String toString() {
        return "CustomMessage{" +
                "className='" + className + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}