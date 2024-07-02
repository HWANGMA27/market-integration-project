package prj.blockchain.exchange.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prj.blockchain.exchange.config.CryptoConfig;
import prj.blockchain.exchange.dto.UserDto;
import prj.blockchain.exchange.model.User;
import prj.blockchain.exchange.repository.UserRepository;
import prj.blockchain.exchange.util.CryptoUtil;

import javax.crypto.SecretKey;

@Log4j2
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {
    private final CryptoConfig cryptoConfig;
    private final UserRepository userRepository;

    @Transactional
    public UserDto addUser(UserDto userDto) {
        try {
            SecretKey secretKey = cryptoConfig.getSecretKey();
            String encryptedApiKey = CryptoUtil.encrypt(userDto.getApiKey(), secretKey); // 사용자 키 암호화
            String encryptedApiSecret = CryptoUtil.encrypt(userDto.getApiSecret(), secretKey); // 사용자 secret 암호화
            userDto.setApiKey(encryptedApiKey);
            userDto.setApiKey(encryptedApiSecret);
            return UserDto.fromEntity(userRepository.save(new User(userDto)));
        }catch (Exception e) {
            log.error(e.getMessage());
            return new UserDto();
        }
    }
}
