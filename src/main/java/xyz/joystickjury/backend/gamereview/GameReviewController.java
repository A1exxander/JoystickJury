package xyz.joystickjury.backend.gamereview;

import io.fusionauth.jwt.JWTException;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.joystickjury.backend.token.JWTManager;
import javax.validation.constraints.Min;


@RestController
@RequestMapping("/v1/api/games/reviews")
@AllArgsConstructor
public class GameReviewController implements iGameReviewController {

    @Autowired
    private final JWTManager jwtManager;
    @Autowired
    private final GameReviewService gameReviewService;

    @Override
    @SneakyThrows
    @DeleteMapping
    public ResponseEntity<Void> deleteGameReview(@RequestHeader String Authorization, @Min(1) int gameID){

        String jwt = jwtManager.extractBearerJWT(Authorization);

        if (!jwtManager.isValidJWT(jwt)) {
            throw new JWTException("Invalid JWT provided.");
        }

        gameReviewService.deleteGameReview(Integer.valueOf(jwtManager.decodeJWT(jwt).subject));
        return ResponseEntity.noContent().build();

    }

}
