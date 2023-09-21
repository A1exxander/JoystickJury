package xyz.joystickjury.backend.token;

import io.fusionauth.jwt.domain.JWT;
import org.springframework.stereotype.Component;
import xyz.joystickjury.backend.user.User;

@Component
public interface iJWTManager { // Consider setting up a cache

    public String generateJWT(User user);
    public void invalidateJWT(String jwt);
    public Boolean isValidJWT(String jwt);
    public JWT decodeJWT(String encodedJWT);
    public String extractBearerJWT(String rawAuthorizationToken) throws IllegalArgumentException;

}
