package xyz.joystickjury.backend.game;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Null;


@Data @SuperBuilder @EqualsAndHashCode(callSuper = true) @Getter
public class SimpleReleasedGameDTO extends SimpleGameDTO { // Detailed GameDTO is used to get all information of a released game

    @Null @Min(0) @Max(5)
    private final float averageRating;

}