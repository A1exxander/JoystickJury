package xyz.joystickjury.backend.game;

import lombok.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Date;
import java.util.Set;


@Getter @Setter
public class ReleasedGame extends Game {

    @NotNull
    private Date releaseDate;
    @Null @Min(0) @Max(5)
    private float averageRating; // Unsure if this should be a part of our Game / ReleasedGame class

    public ReleasedGame(int gameID, String gameTitle, String gameDescription, String gameBannerArtLink, String gameTrailerLink, String developerName, String publisherName, Set<String> gameGenres, Date releaseDate, float averageRating) {
        super(gameID, gameTitle, gameDescription, gameBannerArtLink, gameTrailerLink, developerName, publisherName, gameGenres, ReleaseStatus.RELEASED);
        this.releaseDate = releaseDate;
        this.averageRating = averageRating;
    }

}
