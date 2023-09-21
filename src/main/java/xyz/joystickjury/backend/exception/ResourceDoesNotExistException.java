package xyz.joystickjury.backend.exception;

public class ResourceDoesNotExistException extends RuntimeException {
    public ResourceDoesNotExistException(String message) { super(message); }
}
