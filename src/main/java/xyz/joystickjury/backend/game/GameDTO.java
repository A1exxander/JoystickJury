package xyz.joystickjury.backend.game;

import lombok.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Set;


@AllArgsConstructor @Getter
public class GameDTO { // Detailed GameDTO is used to get all information of a released game

    @NotNull @Min(1)
    private final int gameID;
    @NotNull
    private final String gameTitle;
    private final String gameDescription;
    private final String gameBannerArtLink;
    private final String gameTrailerLink;
    @NotNull
    private final String developerName;
    @NotNull
    private final String publisherName;
    @NotNull
    private final Set<String> gameGenres;
    @NotNull
    private final ReleaseStatus releaseStatus; // Useful for having the client check the release status vs checking a date

}
