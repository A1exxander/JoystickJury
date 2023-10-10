package xyz.joystickjury.backend.gamereview;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Date;


@AllArgsConstructor @Getter @Setter
public class GameReview {

    @Null @Min(1)
    private Integer gameReviewID;
    @NotNull @Min(1)
    private Integer userID;
    @NotNull @Min(1)
    private Integer gameID;
    @NotNull
    private String reviewTitle;
    @Min(1) @Max(5)
    private int reviewScore; // We don't allow half ratings only whole integers from 1-5
    @Null
    private String reviewText;
    @NotNull
    private Date reviewPostDate;

}
