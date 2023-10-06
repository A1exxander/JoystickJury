package xyz.joystickjury.backend.game;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import xyz.joystickjury.backend.utils.DatabaseConnectionManager;
import javax.validation.constraints.Min;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Date;


@Repository
@AllArgsConstructor
public class GameDAO implements iGameDAO {

    private Connection databaseConnection;

    public GameDAO(){ this.databaseConnection = DatabaseConnectionManager.getConnection(); }

    @Override
    public Game get(Integer id) throws SQLException {

        final String query = "SELECT G.*, GROUP_CONCAT(GG.GameGenre) AS GameGenres, AVG(GR.ReviewScore) AS AverageReviewScore FROM Game G LEFT JOIN GameReview GR ON G.GameID = GR.GameID LEFT JOIN GameGenre GG ON G.GameID = GG.GameID GROUP BY G.GameID WHERE G.GameID = ?";
        PreparedStatement preparedStatement = databaseConnection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        ResultSet result = preparedStatement.executeQuery();

        if (result.next()) {

            Date currentDate = new Date();
            Date releaseDate = result.getDate("ReleaseDate");
            ReleaseStatus releaseStatus = null;

            if (result.getDate("ReleaseDate") == null || currentDate.before(result.getDate("ReleaseDate"))){
                releaseStatus = ReleaseStatus.UNRELEASED;
            }
            else {
                releaseStatus = ReleaseStatus.RELEASED;
            }

            return new Game(
                    result.getInt("GameID"),
                    result.getString("GameTitle"),
                    result.getString("GameDescription"),
                    result.getString("GameBannerArtLink"),
                    result.getString("GameTrailerLink"),
                    result.getString("DeveloperName"),
                    result.getString("PublisherName"),
                    new HashSet<String>(List.of(result.getString("GameGenres").split(","))),
                    releaseStatus,
                    releaseDate,
                    result.getFloat("AverageReviewScore")
            );

        }
        else {
            return null;
        }

    }

    @Override
    public List<Game> getAll() throws SQLException {

        final String query = "SELECT G.*, GROUP_CONCAT(GG.GameGenre) AS GameGenres, AVG(GR.ReviewScore) AS AverageReviewScore FROM Game G LEFT JOIN GameReview GR ON G.GameID = GR.GameID LEFT JOIN GameGenre GG ON G.GameID = GG.GameID GROUP BY G.GameID;";
        PreparedStatement preparedStatement = databaseConnection.prepareStatement(query);
        ResultSet results = preparedStatement.executeQuery();
        List<Game> games = new ArrayList<>();

        Date currentDate = new Date();

        while(results.next()) {

            Date releaseDate = results.getDate("ReleaseDate");
            ReleaseStatus releaseStatus = null;

            if (results.getDate("ReleaseDate") == null || currentDate.before(results.getDate("ReleaseDate"))){
                releaseStatus = ReleaseStatus.UNRELEASED;
            }
            else {
                releaseStatus = ReleaseStatus.RELEASED;
            }

            games.add(new Game(
                    results.getInt("GameID"),
                    results.getString("GameTitle"),
                    results.getString("GameDescription"),
                    results.getString("GameBannerArtLink"),
                    results.getString("GameTrailerLink"),
                    results.getString("DeveloperName"),
                    results.getString("PublisherName"),
                    new HashSet<String>(List.of(results.getString("GameGenres").split(","))),
                    releaseStatus,
                    releaseDate,
                    results.getFloat("AverageReviewScore"))
            );
        }

        return games;

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = SQLException.class)
    public void save(Game game) throws SQLException { // Violates the SRP but W/E - Ideally we should've have a GameGenreDAO and create GameObjects thru GameService using GameDAO and GameGenreDAO classes

        final String insertGameQuery  = "INSERT INTO Game (GameTitle, GameDescription, GameTrailerLink, GameBannerArtLink, DeveloperName, PublisherName, ReleaseDate) VALUES (?, ?, ?, ?, ?, ?, ?)";
        final String insertGenreQuery = "INSERT INTO GameGenre (GameID, GameGenre) VALUES (?, ?)";
        PreparedStatement gameStatement = databaseConnection.prepareStatement(insertGameQuery);
        PreparedStatement genreStatement = databaseConnection.prepareStatement(insertGenreQuery);

        gameStatement.setString(1, game.getGameTitle());
        gameStatement.setString(2, game.getGameDescription());
        gameStatement.setString(3, game.getGameTrailerLink());
        gameStatement.setString(4, game.getGameBannerArtLink());
        gameStatement.setString(5, game.getDeveloperName());
        gameStatement.setString(6, game.getPublisherName());
        gameStatement.setDate(7, (java.sql.Date)game.getReleaseDate());

        for (String genre : game.getGameGenres()) {
            genreStatement.setInt(1, game.getGameID());
            genreStatement.setString(2, genre);
            genreStatement.addBatch();
        }

        gameStatement.executeUpdate();
        genreStatement.executeBatch();

    }

    @Override
    public void update(Game game) throws SQLException {

        final String query  = "UPDATE Game SET GameTitle = ?, GameDescription = ?, GameTrailerLink = ?, GameBannerArtLink = ?, DeveloperName = ?, PublisherName = ?, ReleaseDate = ? WHERE GameID = ?";
        PreparedStatement statement = databaseConnection.prepareStatement(query);

        statement.setString(1, game.getGameTitle());
        statement.setString(2, game.getGameDescription());
        statement.setString(3, game.getGameTrailerLink());
        statement.setString(4, game.getGameBannerArtLink());
        statement.setString(5, game.getDeveloperName());
        statement.setString(6, game.getPublisherName());
        statement.setDate(7, (java.sql.Date)game.getReleaseDate());
        statement.setInt(8, game.getGameID());

        statement.executeUpdate();

    }

    @Override
    public void delete(Integer id) throws SQLException {

        final String query = "DELETE FROM Game WHERE GameID = ?";
        PreparedStatement statement = databaseConnection.prepareStatement(query);
        statement.setInt(1, id);
        statement.executeUpdate();

    }

    @Override
    public List<Game> getTrending() throws SQLException {

        final String query  = "SELECT G.*, GROUP_CONCAT(GG.GameGenre) AS GameGenres, COUNT(CASE WHEN GR.ReviewPostDate < CURDATE() - 30 then 1 ELSE 0 END) as ReviewsThisMonth FROM Game G LEFT JOIN GameReview GR ON G.GameID = GR.GameID LEFT JOIN GameGenre GG ON G.GameID = GG.GameID WHERE G.ReleaseDate <=  CURDATE() GROUP BY G.GameID ORDER BY ReleaseDate DESC LIMIT 10";
        PreparedStatement statement = databaseConnection.prepareStatement(query);
        List<Game> games = new ArrayList<>();
        ResultSet results = statement.executeQuery();

        Date currentDate = new Date();

        while (results.next()) {

            Date releaseDate = results.getDate("ReleaseDate");
            ReleaseStatus releaseStatus = null;

            if (results.getDate("ReleaseDate") == null || currentDate.before(results.getDate("ReleaseDate"))){
                releaseStatus = ReleaseStatus.UNRELEASED;
            }
            else {
                releaseStatus = ReleaseStatus.RELEASED;
            }

            games.add(new Game(
                    results.getInt("GameID"),
                    results.getString("GameTitle"),
                    results.getString("GameDescription"),
                    results.getString("GameBannerArtLink"),
                    results.getString("GameTrailerLink"),
                    results.getString("DeveloperName"),
                    results.getString("PublisherName"),
                    new HashSet<String>(List.of(results.getString("GameGenres").split(","))),
                    releaseStatus,
                    releaseDate,
                    results.getFloat("AverageReviewScore"))
            );

        }

        return games;

    }

    @Override
    public List<Game> getUpcoming() throws SQLException {

        final String query  = "SELECT G.*, GROUP_CONCAT(GG.GameGenre) AS GameGenres, AVG(GR.ReviewScore) AS AverageReviewScore FROM Game G LEFT JOIN GameReview GR ON G.GameID = GR.GameID LEFT JOIN GameGenre GG ON G.GameID = GG.GameID WHERE G.ReleaseDate > CURDATE() GROUP BY G.GameID ORDER BY G.ReleaseDate DESC LIMIT 10";
        PreparedStatement statement = databaseConnection.prepareStatement(query);
        List<Game> games = new ArrayList<>();
        ResultSet results = statement.executeQuery();

        Date currentDate = new Date();

        while (results.next()) {

            Date releaseDate = results.getDate("ReleaseDate");
            ReleaseStatus releaseStatus = null;

            if (results.getDate("ReleaseDate") == null || currentDate.before(results.getDate("ReleaseDate"))){
                releaseStatus = ReleaseStatus.UNRELEASED;
            }
            else {
                releaseStatus = ReleaseStatus.RELEASED;
            }

            games.add(new Game(
                    results.getInt("GameID"),
                    results.getString("GameTitle"),
                    results.getString("GameDescription"),
                    results.getString("GameBannerArtLink"),
                    results.getString("GameTrailerLink"),
                    results.getString("DeveloperName"),
                    results.getString("PublisherName"),
                    new HashSet<String>(List.of(results.getString("GameGenres").split(","))),
                    releaseStatus,
                    releaseDate,
                    results.getFloat("AverageReviewScore"))
            );

        }

        return games;

    }

    @Override
    public List<Game> getRecent() throws SQLException {

        final String query  = "SELECT G.*, GROUP_CONCAT(GG.GameGenre) AS GameGenres, AVG(GR.ReviewScore) AS AverageReviewScore FROM Game G LEFT JOIN GameReview GR ON G.GameID = GR.GameID LEFT JOIN GameGenre GG ON G.GameID = GG.GameID WHERE G.ReleaseDate <= CURDATE() GROUP BY G.GameID ORDER BY G.ReleaseDate DESC LIMIT 10";
        PreparedStatement statement = databaseConnection.prepareStatement(query);
        List<Game> games = new ArrayList<>();
        ResultSet results = statement.executeQuery();

        Date currentDate = new Date();

        while (results.next()) {

            Date releaseDate = results.getDate("ReleaseDate");
            ReleaseStatus releaseStatus = null;

            if (results.getDate("ReleaseDate") == null || currentDate.before(results.getDate("ReleaseDate"))){
                releaseStatus = ReleaseStatus.UNRELEASED;
            }
            else {
                releaseStatus = ReleaseStatus.RELEASED;
            }

            games.add(new Game(
                    results.getInt("GameID"),
                    results.getString("GameTitle"),
                    results.getString("GameDescription"),
                    results.getString("GameBannerArtLink"),
                    results.getString("GameTrailerLink"),
                    results.getString("DeveloperName"),
                    results.getString("PublisherName"),
                    new HashSet<String>(List.of(results.getString("GameGenres").split(","))),
                    releaseStatus,
                    releaseDate,
                    results.getFloat("AverageReviewScore"))
            );

        }

        return games;

    }

    @Override
    public List<Game> getHighestRated() throws SQLException {

        final String query  = "SELECT G.*, GROUP_CONCAT(GG.GameGenre) AS GameGenres, AVG(GR.ReviewScore) AS AverageReviewScore FROM Game G LEFT JOIN GameReview GR ON G.GameID = GR.GameID LEFT JOIN GameGenre GG ON G.GameID = GG.GameID GROUP BY G.GameID ORDER BY AverageReviewScore DESC LIMIT 10";
        PreparedStatement statement = databaseConnection.prepareStatement(query);
        List<Game> games = new ArrayList<>();
        ResultSet results = statement.executeQuery();

        Date currentDate = new Date();

        while (results.next()) {

            Date releaseDate = results.getDate("ReleaseDate");
            ReleaseStatus releaseStatus = null;

            if (results.getDate("ReleaseDate") == null || currentDate.before(results.getDate("ReleaseDate"))){
                releaseStatus = ReleaseStatus.UNRELEASED;
            }
            else {
                releaseStatus = ReleaseStatus.RELEASED;
            }

            games.add(new Game(
                    results.getInt("GameID"),
                    results.getString("GameTitle"),
                    results.getString("GameDescription"),
                    results.getString("GameBannerArtLink"),
                    results.getString("GameTrailerLink"),
                    results.getString("DeveloperName"),
                    results.getString("PublisherName"),
                    new HashSet<String>(List.of(results.getString("GameGenres").split(","))),
                    releaseStatus,
                    releaseDate,
                    results.getFloat("AverageReviewScore"))
            );

        }

        return games;

    }


}
