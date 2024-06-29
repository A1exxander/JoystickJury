package xyz.joystickjury.backend.game;

import io.jsonwebtoken.JwtException;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.joystickjury.backend.exception.UnauthorizedRequestException;
import xyz.joystickjury.backend.token.JWTProvider;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.sql.SQLException;
import java.util.List;


@RestController
@RequestMapping("/api/v1/games")
@AllArgsConstructor
public class GameController implements iGameController {

    @Autowired
    private final GameService gameService;
    @Autowired
    private GameMapper gameMapper;
    @Autowired
    private JWTProvider jwtProvider;

    @Override
    @SneakyThrows
    @GetMapping
    public ResponseEntity<List<GameDTO>> getAllGames(@RequestParam(name = "limit", required = false) @Min(1) Integer limit, @RequestParam(name = "q", required = false) String searchQuery) {

        List<Game> games;

        if (searchQuery == null){
            games = gameService.getAllGames();
        }
        else {
            games = gameService.getBySeachQuery(searchQuery);
        }

        if (limit != null && limit < games.size()) { games = games.subList(0, limit); }
        return ResponseEntity.ok(gameMapper.entityToDTOs(games));

    }

    @Override
    @SneakyThrows
    @GetMapping("/latest")
    public ResponseEntity<List<GameDTO>> getNewGames(@RequestParam(name = "limit", required = false) @Min(1) Integer limit) {
        List<Game> recentGames = gameService.getRecent();
        if (limit != null && limit < recentGames.size()) { recentGames = recentGames.subList(0, limit); }
        return ResponseEntity.ok(gameMapper.entityToDTOs(recentGames));
    }

    @Override
    @SneakyThrows
    @GetMapping("/upcoming")
    public ResponseEntity<List<GameDTO>> getUpcomingGames(@RequestParam(name = "limit", required = false) @Min(1) Integer limit) {
        List<Game> upcomingGames = gameService.getUpcoming();
        if (limit != null && limit < upcomingGames.size()) { upcomingGames = upcomingGames.subList(0, limit); }
        return ResponseEntity.ok(gameMapper.entityToDTOs(upcomingGames));
    }

    @Override
    @SneakyThrows
    @GetMapping("/highest-rated")
    public ResponseEntity<List<GameDTO>> getHighestRatedGames(@RequestParam(name = "limit", required = false) @Min(1) Integer limit) {
        List<Game> highestRatedGames = gameService.getHighestRated();
        if (limit != null && limit < highestRatedGames.size()) { highestRatedGames = highestRatedGames.subList(0, limit); }
        return ResponseEntity.ok(gameMapper.entityToDTOs(highestRatedGames));
    }

    @Override
    @SneakyThrows
    @GetMapping("/trending")
    public ResponseEntity<List<GameDTO>> getTrendingGames(@RequestParam(name = "limit", required = false) @Min(1) Integer limit) {
        List<Game> trendingGames = gameService.getTrending();
        if (limit != null && limit <trendingGames.size()) { trendingGames = trendingGames.subList(0, limit); }
        return ResponseEntity.ok(gameMapper.entityToDTOs(trendingGames));
    }

    @Override
    @SneakyThrows
    @GetMapping("/{gameID}")
    public ResponseEntity<GameDTO> getSpecificGame(@PathVariable @Min(1) int gameID) throws SQLException {
        return ResponseEntity.ok(gameMapper.entityToDTO(gameService.getGame(gameID)));
    }

    @Override
    @SneakyThrows
    @PostMapping
    public ResponseEntity<Void> saveGame(@RequestHeader @NotNull String Authorization, @RequestBody @Valid GameDTO newGameDTO) {

        String jwt = jwtProvider.extractBearerJWT(Authorization);
        Game newGame = gameMapper.dtoToEntity(newGameDTO);

        if (!jwtProvider.isValidJWT(jwt)) {
            throw new JwtException("Invalid JWT provided");
        }
        else if (!jwtProvider.decodeJWT(jwt).getString("role").equalsIgnoreCase("ADMIN")) {
            throw new UnauthorizedRequestException("Account must have admin level permissions to save a new game");
        }

        gameService.saveGame(newGame);
        return ResponseEntity.noContent().build();

    }

    @Override
    @SneakyThrows
    @PutMapping
    public ResponseEntity<Void> updateGame(@RequestHeader @NotNull String Authorization, @RequestBody @Valid GameDTO updatedGameDTO) {

        String jwt = jwtProvider.extractBearerJWT(Authorization);
        Game updatedGame = gameMapper.dtoToEntity(updatedGameDTO);

        if (!jwtProvider.isValidJWT(jwt)) {
            throw new JwtException("Invalid JWT provided");
        }
        else if (!jwtProvider.decodeJWT(jwt).getString("role").equalsIgnoreCase("ADMIN")) {
            throw new UnauthorizedRequestException("Account must have admin level permissions to save a new game");
        }

        gameService.updateGame(updatedGame);
        return ResponseEntity.noContent().build();

    }

    @Override
    @SneakyThrows
    @DeleteMapping
    public ResponseEntity<Void> deleteGame(@RequestHeader @NotNull String Authorization, @RequestParam @Min(1) int gameID) {

        String jwt = jwtProvider.extractBearerJWT(Authorization);

        if (!jwtProvider.isValidJWT(jwt)) {
            throw new JwtException("Invalid JWT provided");
        }
        else if (!jwtProvider.decodeJWT(jwt).getString("role").equalsIgnoreCase("ADMIN")) {
            throw new UnauthorizedRequestException("Account must have admin level permissions to save a new game");
        }

        gameService.deleteGame(gameID);
        return ResponseEntity.noContent().build();

    }

}
