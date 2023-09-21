package xyz.joystickjury.backend.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.joystickjury.backend.token.JWTManager;
import xyz.joystickjury.backend.user.UserDTO;
import xyz.joystickjury.backend.user.UserService;

@RequestMapping("api/user")
@RestController
public class AuthController implements iAuthController{

    @Autowired
    private CredentialsService credentialsService;
    @Autowired
    private UserService userService;
    @Autowired
    private JWTManager jwtManager;

    @Override
    @PostMapping("/token")
    public ResponseEntity<String> login(RawUserCredentialsDTO rawUserCredentialsDTO) {
        return null;
    }

    @Override
    @PostMapping
    public ResponseEntity<String> register(RawUserCredentialsDTO rawUserCredentialsDTO, UserDTO newUser) {
        return null;
    }
}
