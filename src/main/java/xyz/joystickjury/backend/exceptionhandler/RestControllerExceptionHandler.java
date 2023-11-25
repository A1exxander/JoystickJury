package xyz.joystickjury.backend.exceptionhandler;

import io.fusionauth.jwt.JWTException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import xyz.joystickjury.backend.exception.*;

import java.sql.SQLException;


@RestControllerAdvice
public class RestControllerExceptionHandler {

    private static final Logger logger = Logger.getLogger(RestControllerExceptionHandler.class);

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<String> handleSQLException(SQLException ex) {
        logger.log(Level.FATAL, "SQLException: " + ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Internal server error! Please try again later"); // Don't leak internals
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

    @ExceptionHandler(IllegalRequestException.class)
    public ResponseEntity<String> handleIllegalOperationException(IllegalRequestException ex) {
        logger.log(Level.ERROR,"IllegalRequestException: " + ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid request! " + ex.getMessage());
    }

    @ExceptionHandler(UnauthorizedRequestException.class)
    public ResponseEntity<String> handleUnauthorizedOperationException(UnauthorizedRequestException ex) {
        logger.log(Level.ERROR,"UnauthorizedRequestException: " + ex.getMessage(), ex);
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