package xyz.joystickjury.backend.gamerecommender;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import xyz.joystickjury.backend.game.GameDTO;
import javax.validation.constraints.Null;
import java.util.List;


public interface iGameRecommendationController {
    @GetMapping()
    public ResponseEntity<List<GameDTO>> getRecommendedGames(@RequestHeader String Authorization, @Null Integer limit);
}
