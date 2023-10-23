package xyz.joystickjury.backend.game;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.sql.SQLException;
import java.util.List;


@RestController
@RequestMapping("/api/v1/games")
public interface iGameController {

    public ResponseEntity<List<GameDTO>> getAllGames(@RequestParam(name = "limit", required = false) @Min(1) Integer limit);
    public ResponseEntity<List<GameDTO>> getNewGames(@RequestParam(name = "limit", required = false) @Min(1) Integer limit);
    public ResponseEntity<List<GameDTO>> getUpcomingGames(@RequestParam(name = "limit", required = false) @Min(1) Integer limit);
    public ResponseEntity<List<GameDTO>> getHighestRatedGames(@RequestParam(name = "limit", required = false) @Min(1) Integer limit);
    public ResponseEntity<List<GameDTO>> getTrendingGames(@RequestParam(name = "limit", required = false) @Min(1) Integer limit);
    public ResponseEntity<GameDTO> getSpecificGame(@PathVariable @Min(1) int gameID) throws SQLException;
    public ResponseEntity<Void> saveGame(@RequestHeader @NotNull String Authorization, @RequestBody @Valid GameDTO newGameDTO);
    public ResponseEntity<Void> updateGame(@RequestHeader @NotNull String Authorization, @RequestBody @Valid GameDTO updatedGameDTO);
    public ResponseEntity<Void> deleteGame(@RequestHeader @NotNull String Authorization, @RequestParam @Min(1) int gameID);

}
