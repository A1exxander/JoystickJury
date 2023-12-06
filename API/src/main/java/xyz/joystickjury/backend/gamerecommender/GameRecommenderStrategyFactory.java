package xyz.joystickjury.backend.gamerecommender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GameRecommenderStrategyFactory {

    @Autowired
    private SlopeOneGameRecommender slopeOneGameRecommender;

    public iGameRecommenderStrategy getStrategy(GameRecommenderStrategy gameRecommenderStrategy) {
        switch (gameRecommenderStrategy) {
            case SLOPEONE:
                return slopeOneGameRecommender;
        }
        return null;
    }

}
