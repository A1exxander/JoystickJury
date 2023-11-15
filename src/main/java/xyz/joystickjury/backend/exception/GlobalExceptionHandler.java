package xyz.joystickjury.backend.exception;

import io.fusionauth.jwt.JWTException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.sql.SQLException;


@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = Logger.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<String> handleSQLException(SQLException ex) {
        logger.log(Level.FATAL, "SQLException: " + ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Internal server error! Please try again later"); // Don't leak internal details
    }

    @ExceptionHandler(JWTException.class)
    public ResponseEntity<String> handleJWTException(JWTException ex) {
        logger.log(Level.ERROR,"JWTException: " + ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid request! " + ex.getMessage());
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<String> handleInvalidCredentialsException(InvalidCredentialsException ex) {
        logger.log(Level.ERROR,"InvalidCredentialsException: " + ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid request! " + ex.getMessage());
    }

    @ExceptionHandler(IllegalOperationException.class)
    public ResponseEntity<String> handleIllegalOperationException(IllegalOperationException ex) {
        logger.log(Level.ERROR,"IllegalOperationException: " + ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid request! " + ex.getMessage());
    }

    @ExceptionHandler(UnauthorizedOperationException.class)
    public ResponseEntity<String> handleUnauthorizedOperationException(UnauthorizedOperationException ex) {
        logger.log(Level.ERROR,"UnauthorizedOperationException: " + ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid request! " + ex.getMessage());
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<String> handleResourceAlreadyExistsException(ResourceAlreadyExistsException ex) {
        logger.log(Level.ERROR,"ResourceAlreadyExistsException: " + ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Invalid request! " + ex.getMessage());
    }

    @ExceptionHandler(ResourceDoesNotExistException.class)
    public ResponseEntity<String> handleResourceDoesNotExistException(ResourceDoesNotExistException ex) {
        logger.log(Level.ERROR,"ResourceDoesNotExistException: " + ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid request! " + ex.getMessage());
    }


}