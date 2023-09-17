package xyz.joystickjury.backend.cexceptions;

public class ResourceDoesNotExistException extends RuntimeException {
    public ResourceDoesNotExistException(String message) { super(message); }
}
