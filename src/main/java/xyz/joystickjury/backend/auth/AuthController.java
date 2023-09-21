package xyz.joystickjury.backend.auth;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.joystickjury.backend.exception.ResourceAlreadyExistsException;
import xyz.joystickjury.backend.token.JWTManager;
import xyz.joystickjury.backend.user.User;
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

    @Override
    @PostMapping("/token")
    public ResponseEntity<String> login(@RequestBody(required = true) @Valid CredentialsDTO credentialsDTO) throws SQLException {

        //UserCredentials rawUserCredentials = modelMapper.map(credentialsDTO, RawUserCredentials.class);
        //UserCredentials hashedUserCredentials = credentialsService.getHashedUserCredentials(UserCredentials.getEmail());

        //if (!credentialsService.areValidCredentials(UserCredentials, hashedUserCredentials)){
            //throw new InvalidCredentialsException("Invalid request. User credentials are invalid or do not exist"); // Do not explicitly tell the user which for security purposes
       // }

        //return ResponseEntity.ok(jwtManager.generateJWT(userService.getUser(hashedUserCredentials.getUserID())));
        return ResponseEntity.ok("Okay big guy");
    }

    @Override
    @PostMapping
    public ResponseEntity<String> register(@RequestBody @Valid RegistrationRequestDTO registrationRequestDTO) throws SQLException {
        /*
        UserCredentials rawUserCredentials = modelMapper.map(registrationRequestDTO.getLoginRequestDTO(), UserCredentials.class);
        User newUser = userMapper.userDTOToUser(registrationRequestDTO.getUserDTO());

        if (credentialsService.emailExists(rawUserCredentials.getEmail())){
            throw new ResourceAlreadyExistsException("Invalid request. Email address already exists");
        }
        else if (userService.userExists(newUser.getDisplayName())) {
            throw new ResourceAlreadyExistsException("Invalid request. Display name already exists");
        }

        userService.saveUser(newUser);
        newUser.setUserID(userService.getUser(newUser.getDisplayName()).getUserID());
        UserCredentials hashedUserCredentials = credentialsService.createHashedUserCredentials(newUser.getUserID(), rawUserCredentials);
        credentialsService.saveCredentials(hashedUserCredentials);
*/
        return ResponseEntity.ok(null);

    }

}
