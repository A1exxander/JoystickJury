package xyz.joystickjury.backend.auth;

import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import xyz.joystickjury.backend.user.UserDTO;


@AllArgsConstructor @Getter
public class RegistrationRequestDTO {

    @Valid
    private final UserCredentialsDTO userCredentialsDTO;
    @Valid
    private final UserDTO userDTO;

}

