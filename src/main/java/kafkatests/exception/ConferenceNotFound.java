package kafkatests.exception;

public class ConferenceNotFound extends RuntimeException {
    public ConferenceNotFound(String message) {
        super(message);
    }
}
