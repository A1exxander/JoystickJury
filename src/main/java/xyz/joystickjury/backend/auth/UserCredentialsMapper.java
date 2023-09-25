package xyz.joystickjury.backend.auth;

import org.springframework.stereotype.Component;
import xyz.joystickjury.backend.utils.iMapper;


@Component
public class UserCredentialsMapper implements iMapper<UserCredentials, UserCredentialsDTO> {

    @Override
    public UserCredentialsDTO entityToDTO(UserCredentials credentials) {
        return new UserCredentialsDTO(credentials.getEmail(), credentials.getPassword());
    }

    @Override
    public UserCredentials dtoToEntity(UserCredentialsDTO userCredentialsDTO) {
        return new UserCredentials(null, userCredentialsDTO.getEmail(), userCredentialsDTO.getPassword());
    }

}
