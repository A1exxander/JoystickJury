package xyz.joystickjury.backend.GameReview;

import xyz.joystickjury.backend.game.Game;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.sql.SQLException;
import java.util.List;


public interface iGameReviewService {

    public GameReview getGameReview(@Min(1) int gameReviewID) throws SQLException;
    public List<GameReview> getAllGameReviews() throws SQLException;
    public List<GameReview> getAllGameReviewsByUser(@Min(1) int userID) throws SQLException;
    public List<GameReview> getAllGameReviewsByGame(@Min(1) int gameID) throws SQLException;
    public void saveGameReview(GameReview gameReview) throws SQLException;
    public void updateGameReview(GameReview gameReview) throws SQLException;
    public void deleteGameReview(@Min(1) int gameReviewID) throws SQLException;
    public boolean gameReviewExists(@Min(1) int gameReviewID) throws SQLException;
    public boolean gameReviewExists(@Min(1) int userID, int gameID) throws SQLException;
}
