package xyz.joystickjury.backend.game;

import lombok.*;
import javax.validation.constraints.Null;
import java.util.Date;
import java.util.Set;


@Getter @Setter
public class UnreleasedGame extends Game {

    @Null
    private Date anticipatedReleaseDate;

    public UnreleasedGame(int gameID, String gameTitle, String gameDescription, String gameBannerArtLink, String gameTrailerLink, String developerName, String publisherName, Set<String> gameGenres, Date anticipatedReleaseDate) {
        super(gameID, gameTitle, gameDescription, gameBannerArtLink, gameTrailerLink, developerName, publisherName, gameGenres, ReleaseStatus.UNRELEASED);
        this.anticipatedReleaseDate = anticipatedReleaseDate;
    }

}
