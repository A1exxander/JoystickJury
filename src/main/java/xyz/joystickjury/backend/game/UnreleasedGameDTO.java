package xyz.joystickjury.backend.game;

import lombok.Getter;
import javax.validation.constraints.Null;
import java.util.Date;
import java.util.Set;


@Getter
public class UnreleasedGameDTO extends GameDTO { // Detailed GameDTO is used to get all information of a released game

    @Null
    private final Date anticipatedReleaseDate;

    public UnreleasedGameDTO(int gameID, String gameTitle, String gameDescription, String gameBannerArtLink, String gameTrailerLink, String developerName, String publisherName, Set<String> gameGenres, Date anticipatedReleaseDate) {
        super(gameID, gameTitle, gameDescription, gameBannerArtLink, gameTrailerLink, developerName, publisherName, gameGenres, ReleaseStatus.UNRELEASED);
        this.anticipatedReleaseDate = anticipatedReleaseDate;
    }

}