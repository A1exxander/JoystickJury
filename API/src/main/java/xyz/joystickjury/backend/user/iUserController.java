package xyz.joystickjury.backend.user;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import java.sql.SQLException;
import java.util.List;


@RequestMapping("/api/v1/users")
@RestController
public interface iUserController {

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers(@RequestParam(name = "limit", required = false ) @Min(0) Integer limit);
    @GetMapping("/{userID}")
    public ResponseEntity<UserDTO> getSpecificUser(@PathVariable int userID);
    @GetMapping("/current") // /user will be used to fetch all users, /user/current will be used to fetch only the current user w JWT, and /user/{userID} will be used to fetch other users
    public ResponseEntity<UserDTO> getCurrentUser(@RequestHeader(required = true) String Authorization);
    @PutMapping
    public ResponseEntity<Void> updateCurrentUser(@RequestHeader(required = true) String Authorization, @RequestBody(required = true) @Valid UserDTO updatedUserDTO);
    @DeleteMapping
    public ResponseEntity<Void> deleteCurrentUser(@RequestHeader(required = true) String Authorization);

}

