package xyz.joystickjury.backend.GameReview;

import javax.validation.constraints.Min;
import java.sql.SQLException;
import java.util.List;


public class GameReviewDAO implements iGameReviewDAO {

    @Override
    public GameReview get(Integer id) throws SQLException {
        return null;
    }

    @Override
    public List<GameReview> getAll() throws SQLException {
        return null;
    }

    @Override
    public void save(GameReview gameReview) throws SQLException {

    }

    @Override
    public void update(GameReview gameReview) throws SQLException {

    }

    @Override
    public void delete(Integer id) throws SQLException {

    }

    @Override
    public List<GameReview> getAllByGameID(@Min(1) int gameID) {
        return null;
    }

    @Override
    public List<GameReview> getAllByUserID(@Min(1) int userID) {
        return null;
    }

}
