package xyz.joystickjury.backend.auth;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.joystickjury.backend.cexceptions.InvalidCredentialsException;
import xyz.joystickjury.backend.cexceptions.ResourceAlreadyExistsException;
import xyz.joystickjury.backend.token.JWTManager;
import xyz.joystickjury.backend.user.User;
import xyz.joystickjury.backend.user.UserDTO;
import xyz.joystickjury.backend.user.UserService;
import javax.validation.Valid;
import java.sql.SQLException;


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
    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    @PostMapping("/token")
    public ResponseEntity<String> login(@RequestBody(required = true) @Valid RawUserCredentialsDTO rawUserCredentialsDTO) throws SQLException {

        RawUserCredentials rawUserCredentials = modelMapper.map(rawUserCredentialsDTO, RawUserCredentials.class);
        HashedUserCredentials hashedUserCredentials = credentialsService.getHashedUserCredentials(rawUserCredentials.getEmail());

        if (!credentialsService.areValidCredentials(rawUserCredentials, hashedUserCredentials)){
            throw new InvalidCredentialsException("Invalid request. User credentials are invalid or do not exist"); // Do not explicitly tell the user which for security purposes
        }

        return ResponseEntity.ok(jwtManager.generateJWT(userService.getUser(hashedUserCredentials.getUserID())));

    }

    @Override
    @PostMapping
    public ResponseEntity<String> register(@RequestBody(required = true) @Valid RawUserCredentialsDTO rawUserCredentialsDTO, @RequestBody(required = true) @Valid UserDTO newUserDTO) throws SQLException {

        RawUserCredentials rawUserCredentials = modelMapper.map(rawUserCredentialsDTO, RawUserCredentials.class);
        User newUser = modelMapper.map(newUserDTO, User.class);
        HashedUserCredentials hashedUserCredentials = credentialsService.createHashedUserCredentials(rawUserCredentials);

        if (credentialsService.emailExists(rawUserCredentials.getEmail())){
            throw new ResourceAlreadyExistsException("Invalid request. Email already exists");
        }

        userService.saveUser(newUser);
        credentialsService.saveCredentials(hashedUserCredentials);

        return ResponseEntity.ok(jwtManager.generateJWT(userService.getUser(newUser.getDisplayName())));

    }

}
