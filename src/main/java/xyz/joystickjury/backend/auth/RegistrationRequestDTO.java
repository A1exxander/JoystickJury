package xyz.joystickjury.backend.auth;

import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import xyz.joystickjury.backend.user.UserDTO;


@AllArgsConstructor @Getter
public class RegistrationRequestDTO {

    @Valid
    private CredentialsDTO credentialsDTO;
    @Valid
    private UserDTO userDTO;

}

