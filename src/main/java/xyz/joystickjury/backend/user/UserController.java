package xyz.joystickjury.backend.user;

import io.jsonwebtoken.JwtException;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.joystickjury.backend.exception.UnauthorizedOperationException;
import xyz.joystickjury.backend.token.JWTManager;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController implements iUserController { // TODO: Add endpoints to delete or update ANY user as an admin

    @Autowired
    private final UserService userService;
    @Autowired
    private final JWTManager jwtManager;
    @Autowired
    private final UserMapper userMapper;

    @Override
    @SneakyThrows
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers(@RequestParam(name = "limit", required = false) @Min(1) Integer limit) {

        List<User> users = userService.getAllUsers();
        List<UserDTO> userDTOs = null;

        if (limit == null || limit > users.size()) { // Would be better to simply just fetch limit users from our database
            userDTOs = users.stream().map(user -> userMapper.entityToDTO(user)).collect(Collectors.toList());
        } else {
            userDTOs = users.subList(0, limit).stream().map(user -> userMapper.entityToDTO(user)).collect(Collectors.toList());
        }

        return ResponseEntity.ok(userDTOs);

    }

    @Override
    @SneakyThrows
    @GetMapping("/{userID}")
    public ResponseEntity<UserDTO> getSpecificUser(@PathVariable @Min(1) int userID) {
        return ResponseEntity.ok(userMapper.entityToDTO(userService.getUser(userID)));
    }

    @Override
    @SneakyThrows
    @GetMapping("/current") // /users will be used to fetch all users, /user/current will be used to fetch only the current user w JWT, and /user/{userID} will be used to fetch other users
    public ResponseEntity<UserDTO> getCurrentUser(@RequestHeader String Authorization) {

        String jwt = jwtManager.extractBearerJWT(Authorization);

        if (!jwtManager.isValidJWT(jwt)) {
            throw new JwtException("Invalid JWT Provided");
        }

        Integer currentUserID = Integer.valueOf(jwtManager.decodeJWT(jwt).subject);
        return ResponseEntity.ok(userMapper.entityToDTO(userService.getUser(currentUserID)));

    }

    @Override
    @SneakyThrows
    @PutMapping
    public ResponseEntity<Void> updateCurrentUser(@RequestHeader String Authorization, @RequestBody @Valid UserDTO updatedUserDTO) { // Determine what happens with a null body

        String jwt = jwtManager.extractBearerJWT(Authorization);
        User updatedUser = userMapper.dtoToEntity(updatedUserDTO);

        if (!jwtManager.isValidJWT(jwt)) {
            throw new JwtException("Invalid JWT Provided");
        }

        Integer currentUserID = Integer.valueOf(jwtManager.decodeJWT(jwt).subject);
        User currentUser = userService.getUser(currentUserID);

        if (!userService.isSameUser(currentUser, updatedUser)) { // Do not update the user if they change read-only data
            throw new UnauthorizedOperationException("You cannot modify read-only data");
        }

        userService.updateUser(currentUser, updatedUser);
        return ResponseEntity.noContent().build(); // Used w ResponseEntity<void> controller methods to indicate a 204 Response Type with no body

    }

    @Override
    @SneakyThrows
    @DeleteMapping
    public ResponseEntity<Void> deleteCurrentUser(@RequestHeader String Authorization) {

        String jwt = jwtManager.extractBearerJWT(Authorization);

        if (!jwtManager.isValidJWT(jwt)) {
            throw new JwtException("Invalid JWT Provided");
        }

        userService.deleteUser(Integer.valueOf(jwtManager.decodeJWT(jwt).subject));
        jwtManager.invalidateJWT(jwt);

        return ResponseEntity.noContent().build();

    }

}
