package xyz.joystickjury.backend.token;

import io.fusionauth.jwt.Signer;
import io.fusionauth.jwt.Verifier;
import io.fusionauth.jwt.domain.JWT;
import io.fusionauth.jwt.hmac.HMACSigner;
import io.fusionauth.jwt.hmac.HMACVerifier;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.joystickjury.backend.user.User;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;


@Service
@AllArgsConstructor @NoArgsConstructor
public class JWTManager implements iJWTManager {

    private final String secretKey = System.getenv("joystick-jury-secret-key");
    private final long tokenLifespanHours = 120;
    private Set<String> invalidTokens = new HashSet<>(); // TODO: Currently causes a memory leak. Switch out to something like an LRU cache & evict expired keys automatically in the future.

    @Override
    public String generateJWT(@NotNull User user) {

        Signer signer = HMACSigner.newSHA256Signer(secretKey);
        JWT jwt = new JWT()
                .setIssuer("joystick-jury.com")
                .setSubject(String.valueOf(user.getUserID()))
                .addClaim("role", user.getAccountType())
                .setIssuedAt(ZonedDateTime.now(ZoneOffset.UTC))
                .setExpiration(ZonedDateTime.now(ZoneOffset.UTC).plusHours(tokenLifespanHours));

        return JWT.getEncoder().encode(jwt, signer);

    }

    @Override
    public void invalidateJWT(@NotNull String jwt) { invalidTokens.add(jwt); }

    @Override
    public Boolean isValidJWT(@NotNull String jwt) { // Validates our token by verifying its signature & checking that it isn't expired

        if(invalidTokens.contains(jwt)) { return false; }

        Verifier verifier = HMACVerifier.newVerifier(secretKey);
        try {
            JWT decodedJWT = JWT.getDecoder().decode(jwt, verifier);
            return decodedJWT.expiration.isAfter(ZonedDateTime.now());
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public JWT decodeJWT(String encodedJWT){
        Verifier verifier = HMACVerifier.newVerifier(secretKey);
        return JWT.getDecoder().decode(encodedJWT, verifier);
    }

    @Override
    public String extractBearerJWT(String rawAuthorizationToken) throws IllegalArgumentException { // Maybe consider changing this method to use strategy pattern, where every strategy is dependent on Authorization type instead of being hardcoded to Bearer

        if (rawAuthorizationToken == null || !rawAuthorizationToken.startsWith("Bearer")){
            throw new IllegalArgumentException();
        }

        return rawAuthorizationToken.substring(7);

    }


}
