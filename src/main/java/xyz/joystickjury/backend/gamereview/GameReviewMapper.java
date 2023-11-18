package xyz.joystickjury.backend.gamereview;

import org.springframework.stereotype.Component;
import xyz.joystickjury.backend.utils.iMapper;

@Component
public class GameReviewMapper implements iMapper<GameReview, GameReviewDTO> {

    @Override
    public GameReview dtoToEntity(GameReviewDTO dtoObject) {
        return new GameReview(dtoObject.getGameReviewID(), dtoObject.getUserID(), dtoObject.getGameID(), dtoObject.getReviewTitle(), dtoObject.getReviewScore(), dtoObject.getReviewText(), dtoObject.getReviewPostDate());
    }

    @Override
    public GameReviewDTO entityToDTO(GameReview entityObject) {
        return new GameReviewDTO(entityObject.getGameReviewID(), entityObject.getUserID(), entityObject.getGameID(), entityObject.getReviewTitle(), entityObject.getReviewScore(), entityObject.getReviewText(), entityObject.getReviewPostDate());
    }

}
