package token;

import io.fusionauth.jwt.Signer;
import io.fusionauth.jwt.Verifier;
import io.fusionauth.jwt.domain.JWT;
import io.fusionauth.jwt.hmac.HMACSigner;
import io.fusionauth.jwt.hmac.HMACVerifier;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import user.User;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor @NoArgsConstructor
public class TokenManager implements iTokenManager{

    private String secretKey = System.getenv("joystick-jury-secret-key");
    private long tokenLifespanHours = 120;
    private Set<String> invalidTokens = new HashSet<>(); // TODO: Currently causes a memory leak. Switch out to something like an LRU cache & evict expired keys automatically in the future.

    @Override
    public String generateToken(User user) {

        Signer signer = HMACSigner.newSHA256Signer(secretKey);
        JWT jwt = new JWT()
                .setIssuer("joystick-jury.com")
                .setSubject(String.valueOf(user.getUserID()))
                .addClaim("role", user.getUserType())
                .setIssuedAt(ZonedDateTime.now(ZoneOffset.UTC))
                .setExpiration(ZonedDateTime.now(ZoneOffset.UTC).plusHours(tokenLifespanHours));

        return JWT.getEncoder().encode(jwt, signer);

    }

    @Override
    public void invalidateToken(String token) { invalidTokens.add(token); }

    @Override
    public Boolean isValidToken(String token) { // Validates our token by verifying its signature & checking that it isn't expired

        if(invalidTokens.contains(token)){ return false; }

        Verifier verifier = HMACVerifier.newVerifier(secretKey);
        try {
            JWT decodedJWT = JWT.getDecoder().decode(token, verifier);
            return decodedJWT.expiration.isAfter(ZonedDateTime.now());
        } catch (Exception e) {
            return false;
        }

    }


}
