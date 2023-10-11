package xyz.joystickjury.backend.gamereview;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;
import xyz.joystickjury.backend.utils.DatabaseConnectionManager;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

@Repository
@NoArgsConstructor
public class GameReviewDAO implements iGameReviewDAO {

    private final Connection databaseConnection = DatabaseConnectionManager.getConnection();

    @Override
    public GameReview get(Integer id) throws SQLException {

        final String query = "SELECT * FROM GameReview WHERE GameReviewID = ?";
        PreparedStatement preparedStatement = databaseConnection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        ResultSet result = preparedStatement.executeQuery();

        GameReview gameReview = null;

        if (result.next()) {
            gameReview = new GameReview(result.getInt("GameReviewID"), result.getInt("UserID"), result.getInt("GameID"), result.getString("ReviewTitle"), result.getInt("ReviewScore"), result.getString("ReviewText"), result.getDate("ReviewPostDate"));
        }

        return gameReview;

    }

    @Override
    public GameReview get(int gameID, int userID) throws SQLException {

        final String query = "SELECT * FROM GameReview WHERE GameID = ? AND UserID = ?";
        PreparedStatement preparedStatement = databaseConnection.prepareStatement(query);
        preparedStatement.setInt(1, gameID);
        preparedStatement.setInt(2, userID);
        ResultSet result = preparedStatement.executeQuery();

        GameReview gameReview = null;

        if (result.next()) {
            gameReview = new GameReview(result.getInt("GameReviewID"), result.getInt("UserID"), result.getInt("GameID"), result.getString("ReviewTitle"), result.getInt("ReviewScore"), result.getString("ReviewText"), result.getDate("ReviewPostDate"));
        }

        return gameReview;

    }


    @Override
    public List<GameReview> getAll() throws SQLException {

        final String query = "SELECT * FROM GameReview";
        PreparedStatement preparedStatement = databaseConnection.prepareStatement(query);
        ResultSet result = preparedStatement.executeQuery();

        List<GameReview> gameReviews = new LinkedList<>();

        if (result.next()) {
            gameReviews.add(new GameReview(result.getInt("GameReviewID"), result.getInt("UserID"), result.getInt("GameID"), result.getString("ReviewTitle"), result.getInt("ReviewScore"), result.getString("ReviewText"), result.getDate("ReviewPostDate")));
        }

        return gameReviews;

    }

    @Override
    public void save(GameReview gameReview) throws SQLException {

        final String query = "INSERT INTO GameReview(UserID, GameID, ReviewTitle, ReviewScore, ReviewText, ReviewDate) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = databaseConnection.prepareStatement(query);

        preparedStatement.setInt(1, gameReview.getUserID());
        preparedStatement.setInt(2, gameReview.getGameID());
        preparedStatement.setString(3, gameReview.getReviewTitle());
        preparedStatement.setInt(4, gameReview.getReviewScore());
        preparedStatement.setString(5, gameReview.getReviewText());
        preparedStatement.setDate(6, new java.sql.Date(new java.util.Date().getTime()));

        preparedStatement.executeUpdate();

    }

    @Override
    public void update(GameReview gameReview) throws SQLException {

        final String query = "UPDATE GameReview SET UserID = ?, GameID = ?, ReviewTitle = ?, ReviewScore = ?, ReviewText = ?, ReviewDate = ? WHERE GameReviewID = ?";
        PreparedStatement preparedStatement = databaseConnection.prepareStatement(query);

        preparedStatement.setInt(1, gameReview.getUserID());
        preparedStatement.setInt(2, gameReview.getGameID());
        preparedStatement.setString(3, gameReview.getReviewTitle());
        preparedStatement.setInt(4, gameReview.getReviewScore());
        preparedStatement.setString(5, gameReview.getReviewText());
        preparedStatement.setDate(6, new java.sql.Date(new java.util.Date().getTime()));
        preparedStatement.setInt(7, gameReview.getGameReviewID());

        preparedStatement.executeUpdate();

    }

    @Override
    public void delete(Integer id) throws SQLException {

        final String query = "DELETE FROM GameReview WHERE GameReviewID = ?";
        PreparedStatement preparedStatement = databaseConnection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

    @Override
    public List<GameReview> getAllByGameID(int gameID) throws SQLException {

        final String query = "SELECT * FROM GameReview WHERE GameID = ? ORDER BY ReviewPostDate DESC";
        PreparedStatement preparedStatement = databaseConnection.prepareStatement(query);
        preparedStatement.setInt(1, gameID);
        ResultSet result = preparedStatement.executeQuery();

        List<GameReview> gameReviews = new LinkedList<>();

        while (result.next()) {
            gameReviews.add(new GameReview(result.getInt("GameReviewID"), result.getInt("UserID"), result.getInt("GameID"), result.getString("ReviewTitle"), result.getInt("ReviewScore"), result.getString("ReviewText"), result.getDate("ReviewPostDate")));
        }

        return gameReviews;
    }

    @Override
    public List<GameReview> getAllByUserID(int userID) throws SQLException {

        final String query = "SELECT * FROM GameReview WHERE UserID = ?";
        PreparedStatement preparedStatement = databaseConnection.prepareStatement(query);
        preparedStatement.setInt(1, userID);
        ResultSet result = preparedStatement.executeQuery();

        List<GameReview> gameReviews = new LinkedList<>();

        while (result.next()) {
            gameReviews.add(new GameReview(result.getInt("GameReviewID"), result.getInt("UserID"), result.getInt("GameID"), result.getString("ReviewTitle"), result.getInt("ReviewScore"), result.getString("ReviewText"), result.getDate("ReviewPostDate")));
        }

        return gameReviews;
    }


}
