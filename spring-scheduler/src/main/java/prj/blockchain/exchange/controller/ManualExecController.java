package prj.blockchain.exchange.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import prj.blockchain.exchange.dto.UserDto;
import prj.blockchain.exchange.service.NetworkService;
import prj.blockchain.exchange.service.UserService;
import reactor.core.publisher.Mono;

@Log4j2
@RequiredArgsConstructor
@RequestMapping("/manual")
@RestController
public class ManualExecController {

    private final UserService userService;

    @PostMapping("/user")
    public UserDto addUser(@RequestBody UserDto userDto) {
        return userService.addUser(userDto);
    }
}
