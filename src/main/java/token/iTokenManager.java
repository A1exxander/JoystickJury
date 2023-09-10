package token;

import org.springframework.stereotype.Component;
import user.User;

@Component
public interface iTokenManager { // Consider setting up a cache
    public String generateToken(User user);
    void invalidateToken(String token);
    public Boolean isValidToken(String Token);
}
