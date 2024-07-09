package prj.blockchain.exchange.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prj.blockchain.exchange.config.CryptoConfig;
import prj.blockchain.exchange.dto.UserDto;
import prj.blockchain.exchange.exception.BadRequestException;
import prj.blockchain.exchange.model.User;
import prj.blockchain.exchange.repository.UserRepository;
import prj.blockchain.exchange.util.CryptoUtil;

import javax.crypto.SecretKey;
import java.util.Optional;

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
            userDto.setApiSecret(encryptedApiSecret);
            return UserDto.fromEntity(userRepository.save(new User(userDto)));
        }catch (Exception e) {
            log.error(e.getMessage());
            return new UserDto();
        }
    }

    @Transactional
    public UserDto deactivateUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new BadRequestException("User not found"));
        user.deActivate();
        return UserDto.fromEntity(user);
    }

    public Optional<User> findUser(Long id) {
        return userRepository.findById(id);
    }
}
