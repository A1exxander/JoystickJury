package xyz.joystickjury.backend.auth;

import org.springframework.stereotype.Service;
import xyz.joystickjury.backend.user.User;

@Service
public interface iAuthService { // Implement password resetting etc later
    public void registerUser(User user, UserCredentials userCredential);
    public String loginUser(UserCredentials userCredential);
}
