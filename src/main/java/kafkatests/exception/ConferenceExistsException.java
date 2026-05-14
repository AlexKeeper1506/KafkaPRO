package kafkatests.exception;

public class ConferenceExistsException extends RuntimeException {
    public ConferenceExistsException(String message) {
        super(message);
    }
}
