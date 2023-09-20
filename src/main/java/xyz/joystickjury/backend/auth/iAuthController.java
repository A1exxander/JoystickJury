package xyz.joystickjury.backend.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.joystickjury.backend.user.UserDTO;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
public interface iAuthController {
    @PostMapping("/token")
    ResponseEntity<String> login(@RequestBody @Valid UserCredentialsDTO userCredentialsDTO);
    @PutMapping
    ResponseEntity<String> register(@RequestBody @Valid UserCredentialsDTO userCredentialsDTO, @RequestBody @Valid UserDTO newUser);
}
