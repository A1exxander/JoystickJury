package xyz.joystickjury.backend.gamerecommender;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.joystickjury.backend.game.Game;
import javax.validation.constraints.Min;
import java.sql.SQLException;
import java.util.List;


@Service
@AllArgsConstructor @NoArgsConstructor @Setter
public class GameRecommenderService implements iGameRecommenderService {

    @Autowired
    private GameRecommenderStrategyFactory gameRecommenderStrategyFactory; // Use strategy pattern as we may want to recommend users popular games instead of personalized recommendations someday

    @Override
    public List<Game> getRecommendedGames(@Min(1) int userID) throws SQLException {
        iGameRecommenderStrategy gameRecommenderStrategy = gameRecommenderStrategyFactory.getStrategy(GameRecommenderStrategy.SLOPEONE);
        return gameRecommenderStrategy.recommendGames(userID);
    }

}
