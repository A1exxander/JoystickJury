package xyz.joystickjury.backend.game;

import lombok.AllArgsConstructor;
import lombok.Getter;
import javax.validation.constraints.NotNull;
import java.util.Date;


@AllArgsConstructor @Getter
public class DetailedUnreleasedGameDTO { // Detailed GameDTO is used to get all information of a released game

    @NotNull
    private final Date anticipatedReleaseDate;

}