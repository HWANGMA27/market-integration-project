package prj.blockchain.exchange.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import prj.blockchain.exchange.dto.UserDto;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "app_user")
public class User extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickName;
    private String apiKey;
    private String apiSecret;

    @Builder
    public User(UserDto userDto) {
        this.nickName = userDto.getNickName();
        this.apiKey = userDto.getApiKey();
        this.apiSecret = userDto.getApiSecret();
    }
}
