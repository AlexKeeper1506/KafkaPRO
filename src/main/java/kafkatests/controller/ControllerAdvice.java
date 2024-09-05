package kafkatests.controller;

import kafkatests.exception.ConferenceExistsException;
import kafkatests.exception.ConferenceNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(ConferenceExistsException.class)
    public ResponseEntity<String> handleConferenceExistsException(ConferenceExistsException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConferenceNotFound.class)
    public ResponseEntity<String> handleConferenceNotFoundException(ConferenceNotFound exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }
}
