package xyz.joystickjury.backend.GameReview;

import xyz.joystickjury.backend.game.Game;
import xyz.joystickjury.backend.utils.iDAO;

import javax.validation.constraints.Min;
import java.util.List;


public interface iGameReviewDAO extends iDAO<Integer, GameReview> {

    public List<GameReview> getAllByGameID(@Min(1) int gameID);
    public List<GameReview> getAllByUserID(@Min(1) int userID);

}
