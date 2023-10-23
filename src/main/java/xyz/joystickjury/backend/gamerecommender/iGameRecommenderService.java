package xyz.joystickjury.backend.gamerecommender;

import xyz.joystickjury.backend.game.Game;
import javax.validation.constraints.Min;
import java.sql.SQLException;
import java.util.List;


public interface iGameRecommenderService {
    public List<Game> getRecommendedGames(@Min(1) int userID) throws SQLException;
}
