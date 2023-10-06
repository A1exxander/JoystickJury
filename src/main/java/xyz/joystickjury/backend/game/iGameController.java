package xyz.joystickjury.backend.game;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;


public interface iGameController {

    public ResponseEntity<List<GameDTO>> getAllGames(@RequestParam(name = "limit", required = false) @Min(1) Integer limit) throws SQLException;
    public ResponseEntity<List<GameDTO>> getNewGames(@RequestParam(name = "limit", required = false) @Min(1) Integer limit) throws SQLException;
    public ResponseEntity<List<GameDTO>> getUpcomingGames(@RequestParam(name = "limit", required = false) @Min(1) Integer limit) throws SQLException;
    public ResponseEntity<List<GameDTO>> getHighestRatedGames(@RequestParam(name = "limit", required = false) @Min(1) Integer limit) throws SQLException;
    public ResponseEntity<List<GameDTO>> getTrendingGames(@RequestParam(name = "limit", required = false) @Min(1) Integer limit) throws SQLException;
    public ResponseEntity<GameDTO> getSpecificGame(@PathVariable @Min(1) int gameID, @RequestParam(name = "fields", required = false) Set<String> fields) throws SQLException;
    public ResponseEntity<Void> saveGame(@RequestHeader @NotNull String Authorization, @RequestBody @Valid GameDTO newGameDTO) throws SQLException;
    public ResponseEntity<Void> updateGame(@RequestHeader @NotNull String Authorization, @RequestBody @Valid GameDTO updatedGameDTO) throws SQLException;
    public ResponseEntity<Void> deleteGame(@RequestHeader @NotNull String Authorization, @RequestParam @Min(1) int gameID) throws SQLException;

}
