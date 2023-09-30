package xyz.joystickjury.backend.game;

import lombok.AllArgsConstructor;
import lombok.Getter;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


@AllArgsConstructor @Getter
public class SimpleGameDTO { // Simplified GameDTO used to fetch basic information on game - Used on homepage - Same class is used for unreleased & released games

    @NotNull @Min(1)
    private final int gameID;
    @NotNull
    private final String gameTitle;
    @NotNull
    private final String gameBannerArtLink;

}