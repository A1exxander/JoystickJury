package xyz.joystickjury.backend.gamerecommender;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import xyz.joystickjury.backend.game.Game;
import javax.validation.constraints.Min;
import java.sql.SQLException;
import java.util.List;


@Service
@AllArgsConstructor @NoArgsConstructor @Setter
public class GameRecommendationService implements iGameRecommendationService {

    private iGameRecommenderStrategy gameRecommenderStrategy = new SlopeOneGameRecommender(); // Use strategy pattern as we are not sure if this will change in the future

    @Override
    public List<Game> getRecommendedGames(@Min(1) int userID) throws SQLException {
        return gameRecommenderStrategy.recommendGames(userID);
    }

}
