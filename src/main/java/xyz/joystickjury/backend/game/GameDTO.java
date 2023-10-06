package xyz.joystickjury.backend.game;

import lombok.AllArgsConstructor;
import lombok.Getter;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Date;
import java.util.Set;


@AllArgsConstructor @Getter
public class GameDTO {

    @Null @Min(1)
    private final Integer gameID;
    @NotNull
    private final String gameTitle;
    @NotNull
    private final String gameBannerArtLink;
    private final String gameDescription;
    private final String gameTrailerLink;
    @NotNull
    private final String developerName;
    @NotNull
    private final String publisherName;
    @NotNull
    private final Set<String> gameGenres;
    @NotNull
    private final ReleaseStatus releaseStatus;
    @Null
    private final Date releaseDate;
    @Null @Min(1) @Max(5)
    private final Float averageRating;

}
