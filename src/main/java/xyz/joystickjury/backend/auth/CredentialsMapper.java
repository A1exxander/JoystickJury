package xyz.joystickjury.backend.auth;

import org.springframework.stereotype.Component;
import xyz.joystickjury.backend.utils.iMapper;


@Component
public class CredentialsMapper implements iMapper<UserCredentials, CredentialsDTO> {

    @Override
    public CredentialsDTO entityToDTO(UserCredentials credentials) {
        return new CredentialsDTO(credentials.getEmail(), credentials.getPassword());
    }

    @Override
    public UserCredentials dtoToEntity(CredentialsDTO credentialsDTO) {
        return new UserCredentials(null, credentialsDTO.getEmail(), credentialsDTO.getPassword());
    }

}
