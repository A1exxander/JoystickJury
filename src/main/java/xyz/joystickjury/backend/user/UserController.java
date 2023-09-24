package xyz.joystickjury.backend.user;

import io.jsonwebtoken.JwtException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.joystickjury.backend.exception.UnauthorizedOperationException;
import xyz.joystickjury.backend.token.JWTManager;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController implements iUserController {

    @Autowired
    private final UserService userService;
    @Autowired
    private final JWTManager jwtManager;
    @Autowired
    private final UserMapper userMapper;

    @Override
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers(@RequestParam(name = "limit", required = false ) @Min(0) Integer limit) throws SQLException {

        List<User> users = userService.getAllUsers();
        List<UserDTO> userDTOs = null;

        if (limit == null || limit > users.size()) {
            userDTOs = users.stream().map(user -> userMapper.entityToDTO(user)).collect(Collectors.toList());
        } else {
            userDTOs = users.subList(0, limit).stream().map(user -> userMapper.entityToDTO(user)).collect(Collectors.toList());
        }

        return ResponseEntity.ok(userDTOs);

    }

    @Override
    @GetMapping("/{userID}")
    public ResponseEntity<UserDTO> getSpecificUser(@PathVariable int userID) throws SQLException {
        return ResponseEntity.ok(userMapper.entityToDTO(userService.getUser(userID)));
    }

    @Override
    @GetMapping("/current") // /user will be used to fetch all users, /user/current will be used to fetch only the current user w JWT, and /user/{userID} will be used to fetch other users
    public ResponseEntity<UserDTO> getCurrentUser(@RequestHeader String Authorization) throws SQLException {

        String jwt = jwtManager.extractBearerJWT(Authorization);

        if (!jwtManager.isValidJWT(jwt)) {
            throw new JwtException("Invalid Request. Invalid JWT Provided");
        }

        Integer currentUserID = Integer.valueOf(jwtManager.decodeJWT(jwt).subject);
        return ResponseEntity.ok(userMapper.entityToDTO(userService.getUser(currentUserID)));

    }

    @Override
    @PutMapping
    public ResponseEntity<String> updateCurrentUser(@RequestHeader String Authorization, @RequestBody @Valid UserDTO updatedUserDTO) throws SQLException { // Determine what happens with a null body

        String jwt = jwtManager.extractBearerJWT(Authorization);
        User updatedUser = userMapper.dtoToEntity(updatedUserDTO);

        if (!jwtManager.isValidJWT(jwt)) {
            throw new JwtException("Invalid Request. Invalid JWT Provided");
        }

        Integer currentUserID = Integer.valueOf(jwtManager.decodeJWT(jwt).subject);
        User currentUser = userService.getUser(currentUserID);

        if (!userService.isSameUser(currentUser, updatedUser)) { // Do not update the user if they change read-only data
            throw new UnauthorizedOperationException("Invalid update request. You cannot modify read-only data");
        }

        userService.updateUser(currentUser, updatedUser);

        return ResponseEntity.ok(null);

    }

    @Override
    @DeleteMapping
    public ResponseEntity<String> deleteCurrentUser(@RequestHeader String Authorization) throws SQLException {

        String jwt = jwtManager.extractBearerJWT(Authorization);

        if (!jwtManager.isValidJWT(jwt)) {
            throw new JwtException("Invalid Request. Invalid JWT Provided");
        }

        userService.deleteUser(Integer.valueOf(jwtManager.decodeJWT(jwt).subject));
        jwtManager.invalidateJWT(jwt);

        return ResponseEntity.ok(null);

    }

}
