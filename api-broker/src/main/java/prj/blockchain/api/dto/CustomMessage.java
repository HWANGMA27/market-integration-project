package prj.blockchain.api.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomMessage {
    private boolean success; // ex) true
    private String action; // ex) save
    private String targetTable; // ex) currency
    private String apiName; // ex) bithumb / upbit
    private String affectedData; // ex) 9 rows

    @Override
    public String toString() {
        return "CustomMessage{" +
                "success=" + success +
                ", action='" + action + '\'' +
                ", targetTable='" + targetTable + '\'' +
                ", apiName='" + apiName + '\'' +
                ", affectedData='" + affectedData + '\'' +
                '}';
    }
}