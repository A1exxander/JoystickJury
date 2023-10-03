package xyz.joystickjury.backend.game;

import lombok.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Date;
import java.util.Set;


@AllArgsConstructor @NoArgsConstructor @Getter @Setter
public class Game {

    @Null @Min(1) // Can be null if were inserting a new game
    private Integer gameID;
    @NotNull
    private String gameTitle;
    private String gameDescription;
    private String gameBannerArtLink;
    private String gameTrailerLink;
    @NotNull
    private String developerName;
    @NotNull
    private String publisherName;
    @NotNull
    private Set<String> gameGenres;
    @NotNull
    private ReleaseStatus releaseStatus; // Indicates if our game has been released or is upcoming - Used to upcast a base class ref into a derived class of ReleasedGame or UnreleasedGame
    @Null
    private Date releaseDate;
    @Null @Min(1) @Max(5)
    private Float averageRating;

}
