package xyz.joystickjury.backend.auth;

import org.springframework.stereotype.Service;

@Service
public interface iCredentialsService {
    public HashedUserCredentials getHashedUserCredentials(String email);
    public boolean areValidCredentials(UserCredentials userCredentials, HashedUserCredentials hashedUserCredentials);
}
