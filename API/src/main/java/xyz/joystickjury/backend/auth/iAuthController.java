package xyz.joystickjury.backend.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.sql.SQLException;


@RestController
@RequestMapping("/api/v1/user")
public interface iAuthController {

    @PostMapping("/token")
    public ResponseEntity<String> login(@RequestBody @Valid UserCredentialsDTO userCredentialsDTO);
    @PostMapping
    public ResponseEntity<String> register(@RequestBody @Valid UserPostDTO userPostDTO);

}

