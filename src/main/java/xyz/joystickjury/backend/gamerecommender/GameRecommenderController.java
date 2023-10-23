package xyz.joystickjury.backend.gamerecommender;

import io.fusionauth.jwt.JWTException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.joystickjury.backend.game.Game;
import xyz.joystickjury.backend.game.GameDTO;
import xyz.joystickjury.backend.game.GameMapper;
import xyz.joystickjury.backend.token.JWTManager;
import xyz.joystickjury.backend.user.UserDTO;

import javax.validation.constraints.Null;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/v1/games/recommended")
@AllArgsConstructor
public class GameRecommenderController {

    @Autowired
    private JWTManager jwtManager;
    @Autowired
    private GameRecommenderService gameRecommenderService;
    @Autowired
    private GameMapper gameMapper;

    @GetMapping
    public ResponseEntity<List<GameDTO>> getRecommendedGames(@RequestHeader String Authorization, @Null Integer limit) throws SQLException {

        String jwt = jwtManager.extractBearerJWT(Authorization);

        if (!jwtManager.isValidJWT(jwt)){
            throw new JWTException("Invalid JWT Provided");
        }

        List<Game> recommendedGames = gameRecommenderService.getRecommendedGames(Integer.valueOf(jwtManager.decodeJWT(jwt).subject));
        List<GameDTO> recommendedGameDTOs = null;

        if (limit == null || limit > recommendedGames.size()) {
            recommendedGameDTOs = recommendedGames.stream().map(game -> gameMapper.entityToDTO(game)).collect(Collectors.toList());
        } else {
            recommendedGameDTOs = recommendedGames.subList(0, limit).stream().map(game -> gameMapper.entityToDTO(game)).collect(Collectors.toList());
        }

        return ResponseEntity.ok(recommendedGameDTOs);

    }

}
