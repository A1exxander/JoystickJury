package xyz.joystickjury.backend.gamereview;

import lombok.SneakyThrows;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import javax.validation.constraints.Null;
import java.security.Principal;


public interface iGameReviewWebsocketController {
    @SneakyThrows
    @MessageMapping("games/{gameID}/reviews")
    void postGameReview(@DestinationVariable String gameID, @Payload GameReviewDTO gameReviewDTO);
}
