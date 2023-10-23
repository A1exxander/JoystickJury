package xyz.joystickjury.backend.gamerecommender;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.joystickjury.backend.game.GameDTO;
import javax.validation.constraints.Null;
import java.util.List;

@RestController
@RequestMapping("/api/v1/games/recommended")
public interface iGameRecommendationController {
    @GetMapping()
    public ResponseEntity<List<GameDTO>> getRecommendedGames(@RequestHeader String Authorization, @Null Integer limit);
}
