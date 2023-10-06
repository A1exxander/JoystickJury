package xyz.joystickjury.backend.GameReview;

import lombok.AllArgsConstructor;
import lombok.Getter;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Date;


@AllArgsConstructor @Getter
public class GameReviewDTO {

    @Null @Min(1)
    private final Integer gameReviewID;
    @NotNull @Min(1)
    final private Integer userID;
    @NotNull @Min(1)
    final private Integer gameID;
    @NotNull
    final private String reviewTitle;
    @Min(1) @Max(5)
    final private int reviewScore; // We don't allow half ratings only whole integers from 1-5
    @Null
    final private String reviewText;
    @NotNull
    final private Date reviewPostDate;

}
