package xyz.joystickjury.backend.gamereview;

import io.fusionauth.jwt.JWTException;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.joystickjury.backend.exception.UnauthorizedRequestException;
import xyz.joystickjury.backend.token.JWTProvider;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@AllArgsConstructor
public class GameReviewController implements iGameReviewController {

    @Autowired
    private final JWTProvider jwtProvider;
    @Autowired
    private final GameReviewService gameReviewService;
    @Autowired
    private final GameReviewMapper gameReviewMapper;

    @Override
    @SneakyThrows
    @GetMapping("/api/v1/games/{gameID}/reviews/{gameReviewID}")
    public ResponseEntity<GameReviewDTO> getGameReview(@PathVariable @Min(1) int gameID, @PathVariable @Min(1) int gameReviewID) {
        return ResponseEntity.ok(gameReviewMapper.entityToDTO(gameReviewService.getGameReview(gameReviewID)));
    }

    @Override
    @SneakyThrows
    @GetMapping("/api/v1/games/{gameID}/reviews")
    public ResponseEntity<List<GameReviewDTO>> getAllGameReviewsByGameID(@Null Integer limit, @PathVariable int gameID) {
        List<GameReview> gameReviews = gameReviewService.getAllGameReviewsByGame(gameID);
        List<GameReviewDTO> gameReviewDTOs = gameReviews.stream().map(game -> gameReviewMapper.entityToDTO(game)).collect(Collectors.toList());
        if (limit != null && limit < gameReviewDTOs.size()) { gameReviewDTOs = gameReviewDTOs.subList(0, limit); }
        return ResponseEntity.ok(gameReviewDTOs);
    }

    @Override
    @SneakyThrows
    @GetMapping("/api/v1/games/reviews")
    public ResponseEntity<List<GameReviewDTO>> getAllGameReviewsByUserID(@Null Integer limit, @Min(1) int userID) {
        List<GameReview> gameReviews = gameReviewService.getAllGameReviewsByUser(userID);
        List<GameReviewDTO> gameReviewDTOs = gameReviews.stream().map(game -> gameReviewMapper.entityToDTO(game)).collect(Collectors.toList());
        if (limit != null && limit < gameReviewDTOs.size()) { gameReviewDTOs = gameReviewDTOs.subList(0, limit); }
        return ResponseEntity.ok(gameReviewDTOs);
    }

    @Override
    @SneakyThrows
    @DeleteMapping("/api/v1/games/{gameID}/reviews/{gameReviewID}")
    public ResponseEntity<Void> deleteGameReview(@RequestHeader String Authorization, @PathVariable @Min(1) int gameID, @PathVariable @Min(1) int gameReviewID){

        String jwt = jwtProvider.extractBearerJWT(Authorization);

        if (!jwtProvider.isValidJWT(jwt)) {
            throw new JWTException("Invalid JWT provided.");
        }

        Integer userID = Integer.valueOf(jwtProvider.decodeJWT(jwt).subject);
        Integer gameReviewUserID = gameReviewService.getGameReview(gameReviewID).getUserID();

        if (userID != gameReviewUserID && !jwtProvider.decodeJWT(jwt).getString("role").equalsIgnoreCase("ADMIN")){
            throw new UnauthorizedRequestException("You cannot delete the another user's reviews.");
        }

        gameReviewService.deleteGameReview(gameReviewID);
        return ResponseEntity.noContent().build();

    }

    @Override
    @SneakyThrows
    @PostMapping("/api/v1/games/reviews")
    public ResponseEntity<Void> postGameReview(@RequestHeader String Authorization, @RequestBody @NotNull @Valid GameReviewDTO gameReviewDTO) {

        String jwt = jwtProvider.extractBearerJWT(Authorization);

        if (!jwtProvider.isValidJWT(jwt)) {
            throw new JWTException("Invalid JWT provided.");
        }

        gameReviewService.saveGameReview(gameReviewMapper.dtoToEntity(gameReviewDTO));
        return ResponseEntity.noContent().build();

    }

    @Override
    @SneakyThrows
    @PutMapping("/api/v1/games/{gameID}/reviews/{gameReviewID}")
    public ResponseEntity<Void> updateGameReview(String Authorization, @RequestBody @NotNull @Valid GameReviewDTO gameReviewDTO, @PathVariable @Min(1) int gameID,  @PathVariable @Min(1) int userID) {

        String jwt = jwtProvider.extractBearerJWT(Authorization);

        if (!jwtProvider.isValidJWT(jwt)) {
            throw new JWTException("Invalid JWT provided.");
        }

        gameReviewService.updateGameReview(gameReviewMapper.dtoToEntity(gameReviewDTO));
        return ResponseEntity.noContent().build();

    }

}
