package xyz.joystickjury.backend.game;

import lombok.Getter;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Null;


@Getter
public class SimpleReleasedGameDTO extends SimpleGameDTO { // Detailed GameDTO is used to get all information of a released game

    @Null @Min(0) @Max(5)
    private final float averageRating;

    public SimpleReleasedGameDTO(int gameID, String gameTitle, String gameBannerArtLink, float averageRating) {
        super(gameID, gameTitle, gameBannerArtLink);
        this.averageRating = averageRating;
    }

}