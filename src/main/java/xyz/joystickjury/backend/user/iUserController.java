package xyz.joystickjury.backend.user;


import io.jsonwebtoken.JwtException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.joystickjury.backend.cexceptions.UnauthorizedOperationException;

import javax.validation.constraints.Min;
import java.sql.SQLException;
import java.util.List;

@RequestMapping("/api/user")
public interface iUserController {

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(@RequestParam(name = "limit", required = false ) @Min(0) Integer limit) throws SQLException;
    @GetMapping("/{userID}")
    public ResponseEntity<User> getSpecificUser(@PathVariable int userID) throws SQLException;
    @GetMapping("/current") // /user will be used to fetch all users, /user/current will be used to fetch only the current user w JWT, and /user/{userID} will be used to fetch other users
    public ResponseEntity<User> getCurrentUser(@RequestHeader(required = true) String Authorization) throws SQLException;
    @PutMapping
    public ResponseEntity<String> updateCurrentUser(@RequestHeader(required = true) String Authorization, @RequestBody(required = true) User updatedUser) throws SQLException;
    @DeleteMapping
    public ResponseEntity<String> deleteCurrentUser(@RequestHeader(required = true) String Authorization) throws SQLException;

}

