package xyz.joystickjury.backend.gamereview;

import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.List;


public interface iGameReviewController {

    @GetMapping("/v1/api/games/{gameID}/reviews/{gameReviewID}")
    public ResponseEntity<GameReviewDTO> getGameReview(@PathVariable @Min(1) int gameID, @PathVariable @Min(1) int gameReviewID);
    @GetMapping("/v1/api/games/{gameID}/reviews")
    public ResponseEntity<List<GameReviewDTO>> getAllGameReviewsByGameID(@Null Integer limit, @PathVariable int gameID);
    @GetMapping("/v1/api/games/reviews")
    public ResponseEntity<List<GameReviewDTO>> getAllGameReviewsByUserID(@Null Integer limit, @Min(1) int userID);
    @DeleteMapping("/v1/api/games/{gameID}/reviews/{gameReviewID}")
    public ResponseEntity<Void> deleteGameReview(@RequestHeader String Authorization, @PathVariable @Min(1) int gameID, @PathVariable @Min(1) int gameReviewID);
    @DeleteMapping("/v1/api/games/reviews/")
    public ResponseEntity<Void> postGameReview(@RequestHeader String Authorization, @RequestBody @NotNull @Valid GameReviewDTO gameReviewDTO);
    @PutMapping("/v1/api/games/reviews/")
    public ResponseEntity<Void> updateGameReview(String Authorization, @RequestBody @NotNull @Valid GameReviewDTO gameReviewDTO);

}
