package prj.blockchain.bithumb.log;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Aspect
@Log4j2
@Component
public class LogAspect {

    // Pointcut Target *Handler
    @Pointcut("execution(* prj.blockchain.bithumb..*Handler.*(..))")
    public void handlerMethods() {}

    // Around 어드바이스: WebFlux에서의 로그 처리
    @Around("handlerMethods()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        log.info("Entering method: {} with arguments = {}", joinPoint.getSignature(), joinPoint.getArgs());

        Object result = joinPoint.proceed();

        if (result instanceof Mono) {
            return ((Mono<?>) result)
                    .doOnSuccess(res -> log.info("Method {} executed successfully in {} ms with result = {}", joinPoint.getSignature(), System.currentTimeMillis() - start, res))
                    .doOnError(throwable -> log.error("Method {} failed in {} ms with error = {}", joinPoint.getSignature(), System.currentTimeMillis() - start, throwable.getMessage()));
        }

        log.info("Method {} executed successfully in {} ms", joinPoint.getSignature(), System.currentTimeMillis() - start);
        return result;
    }
}