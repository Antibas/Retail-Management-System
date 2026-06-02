package gr.antivasis.retailmanagementsystem.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
  private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<Map<String, String>> responseStatusExceptionHandler(ResponseStatusException ex) {
    HttpStatus status = (HttpStatus) ex.getStatusCode();
    String reason = ex.getReason() != null ? ex.getReason() : status.getReasonPhrase();
    return getResponseEntity(status, reason);
  }

  @ExceptionHandler(ActionNotAllowedException.class)
  public ResponseEntity<Map<String, String>> actionNotAllowedExceptionHandler(ActionNotAllowedException ex) {
    return getResponseEntity(HttpStatus.CONFLICT, ex.getMessage());
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<Map<String, String>> dataIntegrityViolationExceptionError(DataIntegrityViolationException ex) {
    HttpStatus httpStatus;
    if (ex.getMessage().contains("already exists"))
      httpStatus = HttpStatus.CONFLICT;
    else if (ex.getMessage().contains("is not present in table"))
      httpStatus = HttpStatus.NOT_FOUND;
    else
      httpStatus = HttpStatus.BAD_REQUEST;
    return getResponseEntity(
            httpStatus,
            clearDataIntegrityViolationExceptionMessage(ex.getMessage()));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String, String>> generalExceptionHandler(Exception ex) {
    return getResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
  }

  private <T> ResponseEntity<Map<String, T>> getResponseEntity(HttpStatus status, T errors) {
    if (status.is4xxClientError()) {
      logger.warn("{}: {}", status, errors);
    } else {
      logger.error("{}: {}", status, errors);
    }
    return ResponseEntity.status(status).body(Map.of("error", errors));
  }

  private static String clearDataIntegrityViolationExceptionMessage(String message){
    if(message.split(":").length < 3)
      return message;
    String detail = message.split(":")[2];
    return detail
            .substring(0, detail.indexOf(']'))
            .trim();
  }
}
