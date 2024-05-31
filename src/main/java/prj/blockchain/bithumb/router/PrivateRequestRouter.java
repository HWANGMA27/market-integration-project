package prj.blockchain.bithumb.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import prj.blockchain.bithumb.handler.PrivateRequestHandler;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@Configuration
public class PrivateRequestRouter {
    @Bean
    public RouterFunction<ServerResponse> accountInfoRoute(PrivateRequestHandler requestHandler) {
        return RouterFunctions.route(GET("/account-info"), requestHandler::getAccountInfo);
    }
}