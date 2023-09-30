package xyz.joystickjury.backend.game;

import lombok.*;
import lombok.experimental.SuperBuilder;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Date;


@Data @SuperBuilder @EqualsAndHashCode(callSuper = true) @Getter
public class DetailedReleasedGameDTO extends DetailedGameDTO{ // Detailed GameDTO is used to get all information of a released game

        @NotNull
        private final Date releaseDate;
        @Null @Min(0) @Max(5)
        private final float averageRating;

}
