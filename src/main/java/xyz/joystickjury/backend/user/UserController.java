package xyz.joystickjury.backend.user;

import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.joystickjury.backend.cexceptions.UnauthorizedOperationException;
import xyz.joystickjury.backend.token.JWTManager;
import javax.validation.constraints.Min;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private JWTManager jwtManager;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(@RequestParam(name = "limit", required = false ) @Min(0) Integer limit) throws SQLException {

        List<User> users = userService.getAllUsers();

        if (limit == null || limit != null && limit > users.size()) {
            return ResponseEntity.ok(users);
        }
        else {
            return ResponseEntity.ok(users.subList(0, limit));
        }

    }

    @GetMapping("/current") // /user will be used to fetch all users, /user/current will be used to fetch only the current user w JWT, and /user/{userID} will be used to fetch other user
    public ResponseEntity<User> getCurrentUser(@RequestHeader(required = true) String Authorization) throws SQLException {

        String jwt = jwtManager.extractBearerJWT(Authorization);

        if (!jwtManager.isValidJWT(jwt)) {
            throw new JwtException("Invalid JWT Provided");
        }

        Integer currentUserID = Integer.valueOf(jwtManager.decodeJWT(jwt).subject);
        return ResponseEntity.ok(userService.getUser(currentUserID));

    }

    @PutMapping
    public ResponseEntity<String> updateCurrentUser(@RequestHeader(required = true) String Authorization, @RequestBody User updatedUser) throws SQLException {

        String jwt = jwtManager.extractBearerJWT(Authorization);

        if (!jwtManager.isValidJWT(jwt)) {
            throw new JwtException("Invalid JWT Provided");
        }

        Integer currentUserID = Integer.valueOf(jwtManager.decodeJWT(jwt).subject);
        User currentUser = userService.getUser(currentUserID);

        if (!userService.isSameUser(currentUser, updatedUser)){ // Used to prevent other users from modifying the data of other users maliciously.
            throw new UnauthorizedOperationException("Invalid update request. You can only update your own profile.");
        }

        return ResponseEntity.ok("Successfully updated user.");

    }

    @DeleteMapping
    public ResponseEntity<String> deleteCurrentUser(@RequestHeader(required = true) String Authorization) throws SQLException {

        String jwt = jwtManager.extractBearerJWT(Authorization);

        if (!jwtManager.isValidJWT(jwt)) {
            throw new JwtException("Invalid JWT Provided");
        }

        userService.deleteUser(Integer.valueOf(jwtManager.decodeJWT(jwt).subject));
        jwtManager.invalidateJWT(jwt);

        return ResponseEntity.ok("Successfully deleted user.");

    }

    //TODO : AUTH Controller for logins and registrations, ControllerAdvice, adding Interface for UserController, UserController unit tests, User Service unit tests, more JWTManager Unit tests

}
