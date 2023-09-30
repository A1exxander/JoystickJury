package xyz.joystickjury.backend.game;

import lombok.*;
import lombok.experimental.SuperBuilder;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Date;

@Data @SuperBuilder @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode(callSuper = true) @Getter @Setter
public class ReleasedGame extends Game {

    @NotNull
    private Date releaseDate;
    @Null @Min(0) @Max(5)
    private float averageRating; // Unsure if this should be a part of our Game / ReleasedGame class

}
