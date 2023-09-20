package xyz.joystickjury.backend.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.joystickjury.backend.user.UserDTO;

@RequestMapping("api/user")
@RestController
public class AuthController implements iAuthController{

    @Override
    @PostMapping("/token")
    public ResponseEntity<String> login(UserCredentialsDTO userCredentialsDTO) {
        return null;
    }

    @Override
    @PutMapping
    public ResponseEntity<String> register(UserCredentialsDTO userCredentialsDTO, UserDTO newUser) {
        return null;
    }
}
