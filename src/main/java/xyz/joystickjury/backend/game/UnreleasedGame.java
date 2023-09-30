package xyz.joystickjury.backend.game;

import lombok.*;
import lombok.experimental.SuperBuilder;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data @SuperBuilder @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode(callSuper = true) @Getter @Setter
public class UnreleasedGame extends Game {

    @NotNull
    private Date anticipatedReleaseDate;

}
