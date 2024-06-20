package prj.blockchain.bithumb.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApiCredentialsDto {
    private String apiKey;
    private String secretKey;

    public ApiCredentialsDto(String apiKey, String secretKey) {
        this.apiKey = apiKey;
        this.secretKey = secretKey;
    }
}