package prj.blockchain.api.log;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Log4j2
@Component
public class LogAspect {

    @Pointcut("execution(* prj.blockchain.api..*Controller.*(..))")
    public void controller() {
    }

    @Pointcut("execution(* prj.blockchain.api..*Tasks.*(..))")
    public void scheduler() {
    }

    @Pointcut("execution(* prj.blockchain.api..*MsgService.*(..))")
    public void msgService() {
    }

    // Around advice: 지정된 Pointcut의 메서드가 호출될 때마다 실행
    @Around("controller()")
    public Object controllerLogAround(ProceedingJoinPoint joinPoint) throws Throwable {
        // IP 주소 가져오기
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String clientIp = getClientIp(request);

        // 메서드 시작 시 로그 기록
        log.info("Entering method: {}.{}() with arguments = {} from IP = {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                joinPoint.getArgs(),
                clientIp);

        long startTime = System.currentTimeMillis(); // 시작 시간 기록

        try {
            Object result = joinPoint.proceed(); // 실제 메서드 호출
            long elapsedTime = System.currentTimeMillis() - startTime; // 실행 시간 계산

            // 메서드 종료 시 로그 기록
            log.info("Method {}.{}() executed successfully in {} ms with result = {} from IP = {}",
                    joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(),
                    elapsedTime,
                    result,
                    clientIp);
            return result;
        } catch (Throwable throwable) {
            long elapsedTime = System.currentTimeMillis() - startTime; // 실행 시간 계산

            // 예외 발생 시 로그 기록
            log.error("Exception in method: {}.{}() after {} ms with cause = {} from IP = {}",
                    joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(),
                    elapsedTime,
                    throwable.getCause() != null ? throwable.getCause() : "NULL",
                    clientIp);
            throw throwable;
        }
    }

    // 클라이언트 IP 주소를 얻는 메서드
    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }


    @Around("scheduler()")
    public Object scheduledTaskLogAround(ProceedingJoinPoint joinPoint) throws Throwable {
        // 메서드 시작 시 로그 기록
        log.info("Entering method: {}.{}() with arguments = {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                joinPoint.getArgs());

        long startTime = System.currentTimeMillis(); // 시작 시간 기록

        try {
            Object result = joinPoint.proceed(); // 실제 메서드 호출
            long elapsedTime = System.currentTimeMillis() - startTime; // 실행 시간 계산

            // 메서드 종료 시 로그 기록
            log.info("Method {}.{}() executed successfully in {} ms with result = {}",
                    joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(),
                    elapsedTime,
                    result);
            return result;
        } catch (Throwable throwable) {
            long elapsedTime = System.currentTimeMillis() - startTime; // 실행 시간 계산

            // 예외 발생 시 로그 기록
            log.error("Exception in method: {}.{}() after {} ms with cause = {}",
                    joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(),
                    elapsedTime,
                    throwable.getCause() != null ? throwable.getCause() : "NULL");
            throw throwable;
        }
    }

    // Around 어드바이스: 슬랙 서비스 메서드 호출 전후에 로그 기록
    @Around("msgService()")
    public Object logMsgService(ProceedingJoinPoint joinPoint) throws Throwable {
        // 메서드 시작 시 로그 기록
        log.info("Message sending started. Method: {}.{}() with arguments = {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                joinPoint.getArgs());

        try {
            Object result = joinPoint.proceed(); // 실제 메서드 호출
            // 메서드 종료 시 로그 기록
            log.info("Message sent successfully. Method: {}.{}() with result = {}",
                    joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(),
                    result);
            return result;
        } catch (Throwable throwable) {
            // 예외 발생 시 로그 기록
            log.error("Exception while sending Message in method: {}.{}() with cause = {}",
                    joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(),
                    throwable.getCause() != null ? throwable.getCause() : "NULL");
            throw throwable;
        }
    }
}
