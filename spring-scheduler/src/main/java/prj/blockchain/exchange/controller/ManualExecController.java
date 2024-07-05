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
    private final NetworkService networkService;
    private String targetNetwork = "all";

    @GetMapping("/networks")
    public Mono<Void> manualNetworkFetching() {
        log.info(this.getClass() + " executed");
        return networkService.deleteAllAndSaveNetworkData(targetNetwork)
                .doOnError(error -> log.error("Error: " + error.getMessage()))
                .doOnTerminate(() -> log.info(this.getClass() + " finished"))
                .then();
    }

    @PostMapping("/user")
    public UserDto addUser(@RequestBody UserDto userDto) {
        return userService.addUser(userDto);
    }

    @DeleteMapping("/user/{id}")
    public UserDto deActivateUser(@PathVariable Long id) {
        return userService.deactivateUser(id);
    }
}
