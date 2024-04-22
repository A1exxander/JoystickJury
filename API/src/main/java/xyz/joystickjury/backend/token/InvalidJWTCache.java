package xyz.joystickjury.backend.token;

import io.fusionauth.jwt.domain.JWT;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.HashSet;
import java.util.Set;


@Component
@AllArgsConstructor
@NoArgsConstructor
public class InvalidJWTCache implements iInvalidTokenCache<JWT> {

    private final Set<JWT> invalidJWTs = new HashSet<JWT>(); // Would be better to use Redis for scalability, but this is okay for now.
    private final long expiredJWTEvictionRateMS = 3600000; // JWTs will be evicted every hour by default

    @Override
    public void addToken(@NotNull JWT token) {
        if (!token.isExpired()) {
            invalidJWTs.add(token);
        }
    }

    @Override
    public void removeToken(JWT token) {
        invalidJWTs.remove(token);
    }

    @Override
    public boolean containsToken(JWT token) {
        return invalidJWTs.contains(token);
    }

    @Scheduled(fixedRate = expiredJWTEvictionRateMS)
    private void removeExpiredJWTs() { // Remove expired JWTs to prevent memory leaks
        for (JWT currJWT : invalidJWTs) {
            if (currJWT.isExpired()) {
                invalidJWTs.remove(currJWT);
            }
        }
    }

}
