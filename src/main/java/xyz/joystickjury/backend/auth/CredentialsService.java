package xyz.joystickjury.backend.auth;

import org.springframework.stereotype.Service;


@Service
public class CredentialsService implements iCredentialsService {

    @Override
    public HashedUserCredentials getHashedUserCredentials(String email) {
        return null;
    }

    @Override
    public boolean areValidCredentials(UserCredentials userCredentials, HashedUserCredentials hashedUserCredentials) {
        return true;
    }

}
