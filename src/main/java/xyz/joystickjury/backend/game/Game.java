package xyz.joystickjury.backend.game;

import lombok.*;
import lombok.experimental.SuperBuilder;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Set;


@Data @SuperBuilder @AllArgsConstructor @NoArgsConstructor @Getter @Setter
public class Game {

    @NotNull @Min(1)
    private int gameID;
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

}
