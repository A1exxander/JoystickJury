package xyz.joystickjury.backend.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.sql.SQLException;


@RestController
@RequestMapping("/api/user")
public interface iAuthController {

    @PostMapping("/token")
    public ResponseEntity<String> login(@RequestBody @Valid UserCredentialsDTO userCredentialsDTO) throws SQLException;
    @PostMapping
    public ResponseEntity<String> register(@RequestBody @Valid UserPostDTO userPostDTO) throws SQLException;

}

