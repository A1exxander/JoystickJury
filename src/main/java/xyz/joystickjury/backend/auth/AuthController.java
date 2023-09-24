package xyz.joystickjury.backend.auth;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.joystickjury.backend.exception.InvalidCredentialsException;
import xyz.joystickjury.backend.exception.ResourceAlreadyExistsException;
import xyz.joystickjury.backend.token.JWTManager;
import xyz.joystickjury.backend.user.User;
import xyz.joystickjury.backend.user.UserMapper;
import xyz.joystickjury.backend.user.UserService;
import javax.validation.Valid;
import java.sql.SQLException;
import java.util.Date;


@RestController
@RequestMapping("api/user")
@AllArgsConstructor
public class AuthController implements iAuthController{

    @Autowired
    private final CredentialsService credentialsService;
    @Autowired
    private final UserService userService;
    @Autowired
    private final JWTManager jwtManager;
    @Autowired
    private final UserMapper userMapper;
    @Autowired
    private final CredentialsMapper credentialsMapper;

    @Override
    @PostMapping("/token")
    public ResponseEntity<String> login(@RequestBody @Valid CredentialsDTO credentialsDTO) throws SQLException {

        UserCredentials rawUserCredentials = credentialsMapper.dtoToEntity(credentialsDTO);
        UserCredentials hashedUserCredentials = credentialsService.getHashedUserCredentials(rawUserCredentials.getEmail());

        if (!credentialsService.areValidCredentials(rawUserCredentials, hashedUserCredentials)){
            throw new InvalidCredentialsException("Invalid request. User credentials are invalid or do not exist"); // Do not explicitly tell the user which for security purposes
        }

        return ResponseEntity.ok(jwtManager.generateJWT(userService.getUser(hashedUserCredentials.getUserID())));

    }

    @Override
    @PostMapping
    public ResponseEntity<String> register(@RequestBody @Valid RegistrationRequestDTO registrationRequestDTO) throws SQLException {

        UserCredentials rawUserCredentials = credentialsMapper.dtoToEntity(registrationRequestDTO.getCredentialsDTO());
        User newUser = userMapper.dtoToEntity(registrationRequestDTO.getUserDTO());
        newUser.setRegistrationDate(new Date()); // We do not want to use the one provided by userDTO for security purposes & in the future, our DTO will likely not have a date

        if (credentialsService.emailExists(rawUserCredentials.getEmail())) {
            throw new ResourceAlreadyExistsException("Invalid request. Email address already exists");
        }
        else if (userService.userExists(newUser.getDisplayName())) {
            throw new ResourceAlreadyExistsException("Invalid request. Display name already exists");
        }

        userService.saveUser(newUser);
        newUser.setUserID(userService.getUser(newUser.getDisplayName()).getUserID());
        UserCredentials hashedUserCredentials = credentialsService.createHashedUserCredentials(newUser.getUserID(), rawUserCredentials);
        credentialsService.saveCredentials(hashedUserCredentials);

        return ResponseEntity.ok(jwtManager.generateJWT(userService.getUser(hashedUserCredentials.getUserID())));

    }

}
