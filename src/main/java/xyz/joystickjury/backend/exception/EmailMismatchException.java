package xyz.joystickjury.backend.exception;

public class EmailMismatchException extends RuntimeException {
    public EmailMismatchException(String message) {
        super(message);
    }
}
