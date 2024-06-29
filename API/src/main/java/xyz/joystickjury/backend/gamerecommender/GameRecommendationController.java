package xyz.joystickjury.backend.gamerecommender;

import io.fusionauth.jwt.JWTException;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.joystickjury.backend.game.Game;
import xyz.joystickjury.backend.game.GameDTO;
import xyz.joystickjury.backend.game.GameMapper;
import xyz.joystickjury.backend.token.JWTProvider;
import jakarta.validation.constraints.Null;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/games/recommended")
@AllArgsConstructor
public class GameRecommendationController implements iGameRecommendationController{

    @Autowired
    private JWTProvider jwtProvider;
    @Autowired
    private GameRecommendationService gameRecommendationService;
    @Autowired
    private GameMapper gameMapper;

    @Override
    @SneakyThrows
    @GetMapping
    public ResponseEntity<List<GameDTO>> getRecommendedGames(@RequestHeader String Authorization, @Null Integer limit) {

        String jwt = jwtProvider.extractBearerJWT(Authorization);

        if (!jwtProvider.isValidJWT(jwt)){
            throw new JWTException("Invalid JWT Provided");
        }

        List<Game> recommendedGames = gameRecommendationService.getRecommendedGames(Integer.valueOf(jwtProvider.decodeJWT(jwt).subject));
        List<GameDTO> recommendedGameDTOs = null;

        if (limit == null || limit > recommendedGames.size()) {
            recommendedGameDTOs = recommendedGames.stream().map(game -> gameMapper.entityToDTO(game)).collect(Collectors.toList());
        } else {
            recommendedGameDTOs = recommendedGames.subList(0, limit).stream().map(game -> gameMapper.entityToDTO(game)).collect(Collectors.toList());
        }

        return ResponseEntity.ok(recommendedGameDTOs);

    }

}
