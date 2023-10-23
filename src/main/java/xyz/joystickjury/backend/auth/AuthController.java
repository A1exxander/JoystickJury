package xyz.joystickjury.backend.auth;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.joystickjury.backend.token.JWTManager;
import xyz.joystickjury.backend.user.User;
import xyz.joystickjury.backend.user.UserMapper;
import javax.validation.Valid;
import java.sql.SQLException;
import java.util.Date;


@RestController
@RequestMapping("api/users")
@AllArgsConstructor
public class AuthController implements iAuthController{

    @Autowired
    private final JWTManager jwtManager;
    @Autowired
    private final UserMapper userMapper;
    @Autowired
    private final UserCredentialsMapper userCredentialsMapper;
    @Autowired
    private final AuthService authService;

    @Override
    @SneakyThrows
    @PostMapping("/token")
    public ResponseEntity<String> login(@RequestBody @Valid UserCredentialsDTO userCredentialsDTO) {

        UserCredentials rawUserCredentials = userCredentialsMapper.dtoToEntity(userCredentialsDTO);
        User authenticatedUser = authService.login(rawUserCredentials);
        return ResponseEntity.ok("Bearer " + jwtManager.generateJWT(authenticatedUser));

    }

    @Override
    @SneakyThrows
    @PostMapping
    public ResponseEntity<String> register(@RequestBody @Valid UserPostDTO userPostDTO) {

        UserCredentials rawUserCredentials = userCredentialsMapper.dtoToEntity(userPostDTO.getUserCredentialsDTO());
        User newUser = userMapper.dtoToEntity(userPostDTO.getUserDTO());
        newUser.setRegistrationDate(new Date()); // We do not want to use the one provided by userDTO for security purposes & in the future, our DTO will likely not have a date

        int userID = authService.register(rawUserCredentials, newUser);
        newUser.setUserID(userID);

        return ResponseEntity.ok("Bearer " + jwtManager.generateJWT(newUser));

    }

}
