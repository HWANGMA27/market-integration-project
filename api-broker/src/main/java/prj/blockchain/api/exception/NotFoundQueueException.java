package prj.blockchain.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotFoundQueueException extends RuntimeException {
    public NotFoundQueueException(String message) {
        super(message);
    }
}