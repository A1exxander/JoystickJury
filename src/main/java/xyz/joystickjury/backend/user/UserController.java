package xyz.joystickjury.backend.user;

import io.jsonwebtoken.JwtException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.joystickjury.backend.cexceptions.UnauthorizedOperationException;
import xyz.joystickjury.backend.token.JWTManager;
import javax.validation.constraints.Min;
import java.sql.SQLException;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private final UserService userService;
    @Autowired
    private final JWTManager jwtManager;

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
            throw new JwtException("Invalid Request. Invalid JWT Provided");
        }

        Integer currentUserID = Integer.valueOf(jwtManager.decodeJWT(jwt).subject);
        return ResponseEntity.ok(userService.getUser(currentUserID));

    }

    @PutMapping
    public ResponseEntity<String> updateCurrentUser(@RequestHeader(required = true) String Authorization, @RequestBody(required = true) User updatedUser) throws SQLException { // Determine what happens with a null body

        String jwt = jwtManager.extractBearerJWT(Authorization);

        if (!jwtManager.isValidJWT(jwt)) {
            throw new JwtException("Invalid Request. Invalid JWT Provided");
        }

        Integer currentUserID = Integer.valueOf(jwtManager.decodeJWT(jwt).subject);
        User currentUser = userService.getUser(currentUserID);

        if (!userService.isSameUser(currentUser, updatedUser)) { // Do not update the user if they change read-only data
            throw new UnauthorizedOperationException("Invalid update request. You cannot modify read-only data");
        }

        userService.updateUser(currentUser, updatedUser);

        return ResponseEntity.ok("Successfully updated user.");

    }

    @DeleteMapping
    public ResponseEntity<String> deleteCurrentUser(@RequestHeader(required = true) String Authorization) throws SQLException {

        String jwt = jwtManager.extractBearerJWT(Authorization);

        if (!jwtManager.isValidJWT(jwt)) {
            throw new JwtException("Invalid Request. Invalid JWT Provided");
        }

        userService.deleteUser(Integer.valueOf(jwtManager.decodeJWT(jwt).subject));
        jwtManager.invalidateJWT(jwt);

        return ResponseEntity.ok("Successfully deleted user.");

    }

    //TODO : AUTH Controller for logins and registrations, Make User stop using email ( make it apart of credentials ) ControllerAdvice, adding Interface for UserController, UserController unit tests, UserDTO

}
