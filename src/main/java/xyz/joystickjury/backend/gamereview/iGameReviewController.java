package xyz.joystickjury.backend.gamereview;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import javax.validation.constraints.Min;


public interface iGameReviewController {
    @GetMapping
    public ResponseEntity<Void> deleteGameReview(String Authorization, @Min(1) int gameID);
}
