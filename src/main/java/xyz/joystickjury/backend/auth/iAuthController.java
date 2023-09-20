package xyz.joystickjury.backend.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.joystickjury.backend.user.UserDTO;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
public interface iAuthController {
    @PostMapping("/token")
    public ResponseEntity<String> login(@RequestBody @Valid RawUserCredentialsDTO rawUserCredentialsDTO);
    @PostMapping
    public ResponseEntity<String> register(@RequestBody @Valid RawUserCredentialsDTO rawUserCredentialsDTO, @RequestBody @Valid UserDTO newUser);
}
