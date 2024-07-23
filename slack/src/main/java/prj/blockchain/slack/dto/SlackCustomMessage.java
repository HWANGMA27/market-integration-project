package prj.blockchain.slack.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class SlackCustomMessage {
    private List<Block> blocks;

    @Data
    @AllArgsConstructor
    public static class Block {
        private String type;
        private Text text;

        public Block(String type) {
            this.type = type;
        }
    }

    @Data
    @AllArgsConstructor
    public static class Text {
        private String type;
        private String text;
    }
}
