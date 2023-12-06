package xyz.joystickjury.backend.gamereview;

import xyz.joystickjury.backend.utils.iDAO;

import java.sql.SQLException;
import java.util.List;


public interface iGameReviewDAO extends iDAO<Integer, GameReview> {

    public GameReview get(int gameID, int userID) throws SQLException;
    public List<GameReview> getAllByGameID(int gameID) throws SQLException;
    public List<GameReview> getAllByUserID(int userID) throws SQLException;

}
