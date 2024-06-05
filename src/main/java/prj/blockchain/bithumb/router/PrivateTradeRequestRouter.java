package prj.blockchain.bithumb.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import prj.blockchain.bithumb.handler.PrivateTradeRequestHandler;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;

@Configuration
public class PrivateTradeRequestRouter {
    @Bean
    public RouterFunction<ServerResponse> tradeRoute(PrivateTradeRequestHandler requestHandler) {
        return RouterFunctions.route(POST("/trade/order"), requestHandler::placeOrder);
    }
}