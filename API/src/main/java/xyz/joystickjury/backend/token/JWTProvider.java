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
import javax.validation.constraints.NotNull;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;


@Service
@AllArgsConstructor
@NoArgsConstructor
public class JWTProvider implements iJWTProvider {

    private final String secretKey = System.getenv("joystick-jury-secret-key");
    private final long tokenLifespanHours = 120;
    private final InvalidJWTCache invalidJWTCache = new InvalidJWTCache();

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
    public void invalidateJWT(@NotNull String jwt) {
        JWT decodedJWT = decodeJWT(jwt);
        if (!decodedJWT.isExpired()){
            invalidJWTCache.addToken(decodedJWT);
        }
    }

    @Override
    public Boolean isValidJWT(@NotNull String jwt) { // Validates our token by verifying its signature, checking that it isn't expired, and checking that it isn't blacklisted

        try {
            JWT decodedJWT = decodeJWT(jwt);
            return (!invalidJWTCache.containsToken(decodedJWT) && !decodedJWT.isExpired());
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public JWT decodeJWT(@NotNull String encodedJWT){
        Verifier verifier = HMACVerifier.newVerifier(secretKey);
        return JWT.getDecoder().decode(encodedJWT, verifier);
    }

    @Override
    public String extractBearerJWT(@NotNull String rawAuthorizationToken) throws IllegalArgumentException { // Maybe consider changing this method to use strategy pattern, where every strategy is dependent on Authorization type instead of being hardcoded to Bearer
        if (!rawAuthorizationToken.startsWith("Bearer")) { throw new IllegalArgumentException("Token did not start with expected \"Bearer\" prefix."); }
        return rawAuthorizationToken.substring(7);
    }

}
