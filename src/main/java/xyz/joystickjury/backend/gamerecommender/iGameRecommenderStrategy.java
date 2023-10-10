package xyz.joystickjury.backend.gamerecommender;

import org.springframework.stereotype.Component;
import xyz.joystickjury.backend.game.Game;
import javax.validation.constraints.Min;
import java.sql.SQLException;
import java.util.List;


@Component
public interface iGameRecommenderStrategy {
    public List<Game> recommendGames(@Min(1) int userID) throws SQLException;
}
