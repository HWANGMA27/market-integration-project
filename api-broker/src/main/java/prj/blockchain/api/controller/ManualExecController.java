package prj.blockchain.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import prj.blockchain.api.dto.UserDto;
import prj.blockchain.api.model.User;
import prj.blockchain.api.service.BalanceHistoryService;
import prj.blockchain.api.service.CurrencyService;
import prj.blockchain.api.service.NetworkService;
import prj.blockchain.api.service.UserService;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
@RequestMapping("/manual")
@RestController
public class ManualExecController {

    private final UserService userService;
    private final NetworkService networkService;
    private final CurrencyService currencyService;
    private final BalanceHistoryService balanceHistoryService;
    private String targetNetwork = "all";

    @GetMapping("/networks")
    public void manualNetworkFetching() {
        log.info(this.getClass() + " executed");
        networkService.deleteAllAndSaveNetworkData(targetNetwork);
    }

    @GetMapping("/currencies")
    public void manualCurrencyFetching() {
        log.info(this.getClass() + " executed");
        currencyService.deleteAllAndSaveCurrencyData();
    }

    @PostMapping("/user")
    public UserDto addUser(@RequestBody UserDto userDto) {
        return userService.addUser(userDto);
    }

    @DeleteMapping("/user/{id}")
    public UserDto deActivateUser(@PathVariable Long id) {
        return userService.deactivateUser(id);
    }

    @GetMapping("/user/{id}/balance/{currency}")
    private void getBalance(@PathVariable Long id, @PathVariable String currency) {
        Optional<User> user = userService.findUser(id);
        if(user.isPresent()){
            try {
                balanceHistoryService.saveUserBalanceHistory(user.get(), currency);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }
}
