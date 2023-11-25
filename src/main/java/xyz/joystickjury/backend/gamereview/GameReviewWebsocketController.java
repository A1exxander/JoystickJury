package xyz.joystickjury.backend.gamereview;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.SimpleTypeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class GameReviewWebsocketController {

    @Autowired
    private final SimpMessagingTemplate messagingTemplate;
    @Autowired
    private final GameReviewMapper gameReviewMapper;
    @Autowired
    private final GameReviewService gameReviewService;

    @SneakyThrows
    @MessageMapping("games/{gameID}/reviews")
    public void postGameReview(@DestinationVariable String gameID, @Payload GameReviewDTO gameReviewDTO) {
        StringBuilder messageEndpoint = new StringBuilder("/topic/games/" + gameID + "/reviews");
        gameReviewService.saveGameReview(gameReviewMapper.dtoToEntity(gameReviewDTO));
        messagingTemplate.convertAndSend(messageEndpoint.toString(), gameReviewDTO);
    }

}
