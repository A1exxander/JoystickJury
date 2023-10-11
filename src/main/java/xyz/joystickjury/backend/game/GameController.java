package xyz.joystickjury.backend.game;

import io.jsonwebtoken.JwtException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.joystickjury.backend.exception.UnauthorizedOperationException;
import xyz.joystickjury.backend.token.JWTManager;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/games")
@AllArgsConstructor
public class GameController implements iGameController {

    @Autowired
    private GameService gameService;
    @Autowired
    private GameMapper gameMapper;
    @Autowired
    private JWTManager jwtManager;

    private ResponseEntity<List<GameDTO>> getGamesByType(@Null Integer limit, @NotNull List<Game> games) { // Consider moving this

        List<GameDTO> gamesDTO = games.stream().map(game -> gameMapper.entityToDTO(game)).collect(Collectors.toList());
        if (limit != null && limit < gamesDTO.size()) { gamesDTO = gamesDTO.subList(0, limit); }

        return ResponseEntity.ok(gamesDTO);

    }

    @Override
    @GetMapping
    public ResponseEntity<List<GameDTO>> getAllGames(@RequestParam(name = "limit", required = false) @Min(1) Integer limit) throws SQLException {
        return getGamesByType(limit, gameService.getAllGames());
    }

    @Override
    @GetMapping("/latest")
    public ResponseEntity<List<GameDTO>> getNewGames(@RequestParam(name = "limit", required = false) @Min(1) Integer limit) throws SQLException {
        return getGamesByType(limit, gameService.getRecent());
    }

    @Override
    @GetMapping("/upcoming")
    public ResponseEntity<List<GameDTO>> getUpcomingGames(@RequestParam(name = "limit", required = false) @Min(1) Integer limit) throws SQLException {
        return getGamesByType(limit, gameService.getUpcoming());
    }

    @Override
    @GetMapping("/highest-rated")
    public ResponseEntity<List<GameDTO>> getHighestRatedGames(@RequestParam(name = "limit", required = false) @Min(1) Integer limit) throws SQLException {
        return getGamesByType(limit, gameService.getHighestRated());
    }

    @Override
    @GetMapping("/trending")
    public ResponseEntity<List<GameDTO>> getTrendingGames(@RequestParam(name = "limit", required = false) @Min(1) Integer limit) throws SQLException {
        return getGamesByType(limit, gameService.getTrending());
    }

    @Override
    @GetMapping("/{gameID}")
    public ResponseEntity<GameDTO> getSpecificGame(@PathVariable @Min(1) int gameID) throws SQLException {

        return ResponseEntity.ok(gameMapper.entityToDTO(gameService.getGame(gameID)));

    }

    @Override
    @PostMapping
    public ResponseEntity<Void> saveGame(@RequestHeader @NotNull String Authorization, @RequestBody @Valid GameDTO newGameDTO) throws SQLException {

        String jwt = jwtManager.extractBearerJWT(Authorization);
        Game newGame = gameMapper.dtoToEntity(newGameDTO);

        if (!jwtManager.isValidJWT(jwt)) {
            throw new JwtException("Invalid request. Invalid JWT provided");
        }
        else if (jwtManager.decodeJWT(jwt).getString("role") != "ADMIN") {
            throw new UnauthorizedOperationException("Invalid request. Account must have admin level permissions to save a new game");
        }

        gameService.saveGame(newGame);
        return ResponseEntity.noContent().build();

    }

    @Override
    @PutMapping
    public ResponseEntity<Void> updateGame(@RequestHeader @NotNull String Authorization, @RequestBody @Valid GameDTO updatedGameDTO) throws SQLException {

        String jwt = jwtManager.extractBearerJWT(Authorization);
        Game updatedGame = gameMapper.dtoToEntity(updatedGameDTO);

        if (!jwtManager.isValidJWT(jwt)) {
            throw new JwtException("Invalid request. Invalid JWT provided");
        }
        else if (jwtManager.decodeJWT(jwt).getString("role") != "ADMIN") {
            throw new UnauthorizedOperationException("Invalid request. Account must have admin level permissions to save a new game");
        }

        gameService.updateGame(updatedGame);
        return ResponseEntity.noContent().build();

    }

    @Override
    @DeleteMapping
    public ResponseEntity<Void> deleteGame(@RequestHeader @NotNull String Authorization, @RequestParam @Min(1) int gameID) throws SQLException {

        String jwt = jwtManager.extractBearerJWT(Authorization);

        if (!jwtManager.isValidJWT(jwt)) {
            throw new JwtException("Invalid request. Invalid JWT provided");
        }
        else if (jwtManager.decodeJWT(jwt).getString("role") != "ADMIN") {
            throw new UnauthorizedOperationException("Invalid request. Account must have admin level permissions to save a new game");
        }

        gameService.deleteGame(gameID);
        return ResponseEntity.noContent().build();

    }

}
