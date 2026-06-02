package gr.antivasis.retailmanagementsystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.io.Serializable;
import java.util.UUID;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends ResponseStatusException {

    public ResourceNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }

    public ResourceNotFoundException(String resourceName, UUID resourceId) {
        super(HttpStatus.NOT_FOUND, resourceName + " with id: " + resourceId + " was not found");
    }

    public ResourceNotFoundException(String resourceName, Serializable resourceId) {
        super(HttpStatus.NOT_FOUND, resourceName + " with id: " + resourceId + " was not found");
    }

    public ResourceNotFoundException(String resourceName, String key, String value) {
        super(HttpStatus.NOT_FOUND, resourceName + " with " + key + ": " + value + " was not found");
    }
}
