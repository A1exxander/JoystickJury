package xyz.joystickjury.backend.exception;

import io.fusionauth.jwt.JWTException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.sql.SQLException;


@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<String> handleSQLException(SQLException ex) {
        logger.error("SQLException: " + ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Internal server error! Please try again later"); // Don't leak internal details
    }

    @ExceptionHandler(JWTException.class)
    public ResponseEntity<String> handleJWTException(JWTException ex) {
        logger.warn("JWTException: " + ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid request! " + ex.getMessage());
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<String> handleInvalidCredentialsException(InvalidCredentialsException ex) {
        logger.warn("InvalidCredentialsException: " + ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid request! " + ex.getMessage());
    }

    @ExceptionHandler(IllegalOperationException.class)
    public ResponseEntity<String> handleIllegalOperationException(IllegalOperationException ex) {
        logger.warn("IllegalOperationException: " + ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid request! " + ex.getMessage());
    }

    @ExceptionHandler(UnauthorizedOperationException.class)
    public ResponseEntity<String> handleUnauthorizedOperationException(UnauthorizedOperationException ex) {
        logger.warn("UnauthorizedOperationException: " + ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid request! " + ex.getMessage());
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<String> handleResourceAlreadyExistsException(ResourceAlreadyExistsException ex) {
        logger.warn("ResourceAlreadyExistsException: " + ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Invalid request! " + ex.getMessage());
    }

    @ExceptionHandler(ResourceDoesNotExistException.class)
    public ResponseEntity<String> handleResourceDoesNotExistException(ResourceDoesNotExistException ex) {
        logger.warn("ResourceDoesNotExistException: " + ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid request! " + ex.getMessage());
    }


}