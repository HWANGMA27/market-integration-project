package prj.blockchain.exchange.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import prj.blockchain.exchange.model.User;

@Data
@NoArgsConstructor
public class UserDto {
    private String nickName;
    private String apiKey;
    private String apiSecret;

    @Builder
    public UserDto(String nickName, String apiKey, String apiSecret){
        this.nickName = nickName;
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
    }

    public static UserDto fromEntity(User user){
        return UserDto.builder()
                .nickName(user.getNickName())
                .build();
    }
}
