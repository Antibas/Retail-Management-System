package gr.antivasis.retailmanagementsystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ActionNotAllowedException extends RuntimeException {
    public ActionNotAllowedException(String message) {
        super(message);
    }
}
