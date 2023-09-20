package xyz.joystickjury.backend.auth;

import org.springframework.stereotype.Service;
import xyz.joystickjury.backend.user.User;


@Service
public class AuthService implements iAuthService{

    @Override
    public void registerUser(User user, UserCredentials userCredential) {

    }

    @Override
    public String loginUser(UserCredentials userCredential) {
        return null;
    }

}
