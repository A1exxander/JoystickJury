package xyz.joystickjury.backend.game;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import xyz.joystickjury.backend.utils.DatabaseConnectionManager;
import java.sql.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Repository
@AllArgsConstructor
public class GameDAO implements iGameDAO {

    private final Connection databaseConnection = DatabaseConnectionManager.getConnection();

    @Override
    public Game get(Integer id) throws SQLException {

        final String query = "SELECT G.*, GROUP_CONCAT(GG.GameGenre) AS GameGenres, AVG(GR.ReviewScore) AS AverageReviewScore FROM Game G LEFT JOIN GameReview GR ON G.GameID = GR.GameID LEFT JOIN GameGenre GG ON G.GameID = GG.GameID WHERE G.GameID = ? GROUP BY G.GameID";
        PreparedStatement preparedStatement = databaseConnection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        ResultSet result = preparedStatement.executeQuery();

        if (result.next()) {

            java.util.Date currentDate = new java.util.Date();
            Date releaseDate = result.getDate("ReleaseDate");
            ReleaseStatus releaseStatus = null;
            Float averageGameScore = null;

            if (result.getDate("ReleaseDate") == null || currentDate.before(result.getDate("ReleaseDate"))){
                releaseStatus = ReleaseStatus.UNRELEASED;
            }
            else {
                releaseStatus = ReleaseStatus.RELEASED;
            }

            if (releaseStatus == ReleaseStatus.RELEASED && result.getFloat("AverageReviewScore") != 0.0f){
                averageGameScore = result.getFloat("AverageReviewScore");
            }

            return new Game(
                    result.getInt("GameID"),
                    result.getString("GameTitle"),
                    result.getString("GameDescription"),
                    result.getString("GameCoverArtLink"),
                    result.getString("GameBannerArtLink"),
                    result.getString("GameTrailerLink"),
                    result.getString("DeveloperName"),
                    result.getString("PublisherName"),
                    new HashSet<String>(List.of(result.getString("GameGenres").split(","))),
                    releaseStatus,
                    releaseDate,
                    averageGameScore
            );

        }
        else {
            return null;
        }

    }

    @Override
    public Game get(String gameTitle) throws SQLException {

        final String query = "SELECT G.*, GROUP_CONCAT(GG.GameGenre) AS GameGenres, AVG(GR.ReviewScore) AS AverageReviewScore FROM Game G LEFT JOIN GameReview GR ON G.GameID = GR.GameID LEFT JOIN GameGenre GG ON G.GameID = GG.GameID WHERE G.GameTitle = ? GROUP BY G.GameID";
        PreparedStatement preparedStatement = databaseConnection.prepareStatement(query);
        preparedStatement.setString(1, gameTitle);
        ResultSet result = preparedStatement.executeQuery();

        if (result.next()) {

            java.util.Date currentDate = new java.util.Date();
            Date releaseDate = result.getDate("ReleaseDate");
            ReleaseStatus releaseStatus = null;
            Float averageGameScore = null;
            HashSet<String> gameGenres = null;

            if (result.getDate("ReleaseDate") == null || currentDate.before(result.getDate("ReleaseDate"))){
                releaseStatus = ReleaseStatus.UNRELEASED;
            }
            else {
                releaseStatus = ReleaseStatus.RELEASED;
            }

            if (releaseStatus == ReleaseStatus.RELEASED && result.getFloat("AverageReviewScore") != 0.0f){
                averageGameScore = result.getFloat("AverageReviewScore");
            }

            if (result.getString("GameGenres") != null){
                gameGenres = new HashSet<String>(List.of(result.getString("GameGenres").split(",")));
            }

            return new Game(
                    result.getInt("GameID"),
                    result.getString("GameTitle"),
                    result.getString("GameDescription"),
                    result.getString("GameCoverArtLink"),
                    result.getString("GameBannerArtLink"),
                    result.getString("GameTrailerLink"),
                    result.getString("DeveloperName"),
                    result.getString("PublisherName"),
                    gameGenres,
                    releaseStatus,
                    releaseDate,
                    averageGameScore
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
        List<Game> games = new LinkedList<>();

        java.util.Date currentDate = new java.util.Date();

        while(results.next()) {

            Date releaseDate = results.getDate("ReleaseDate");
            ReleaseStatus releaseStatus = null;
            Float averageGameScore = null;
            HashSet<String> gameGenres = null;

            if (results.getDate("ReleaseDate") == null || currentDate.before(results.getDate("ReleaseDate"))){
                releaseStatus = ReleaseStatus.UNRELEASED;
            }
            else {
                releaseStatus = ReleaseStatus.RELEASED;
            }

            if (releaseStatus == ReleaseStatus.RELEASED && results.getFloat("AverageReviewScore") == 0.0f){
                averageGameScore = results.getFloat("AverageReviewScore");
            }

            if (results.getString("GameGenres") != null){
                gameGenres = new HashSet<String>(List.of(results.getString("GameGenres").split(",")));
            }

            games.add(new Game(
                    results.getInt("GameID"),
                    results.getString("GameTitle"),
                    results.getString("GameDescription"),
                    results.getString("GameCoverArtLink"),
                    results.getString("GameBannerArtLink"),
                    results.getString("GameTrailerLink"),
                    results.getString("DeveloperName"),
                    results.getString("PublisherName"),
                    gameGenres,
                    releaseStatus,
                    releaseDate,
                    results.getFloat("AverageReviewScore"))
            );
        }

        return games;

    }

    @Override
    public List<Game> getAll(List<String> searchQuery) throws SQLException {

        String regexPattern = searchQuery.stream().collect(Collectors.joining("|"));
        final String query = "SELECT G.*, AVG(GR.ReviewScore) AS AverageReviewScore, COUNT(GR.GameID) AS GameReviewCount, GROUP_CONCAT(GG.GameGenre) AS GameGenres FROM Game G " +
                "LEFT JOIN GameReview GR ON G.GameID = GR.GameID " +
                "LEFT JOIN GameGenre GG ON G.GameID = GG.GameID " +
                "WHERE GameTitle REGEXP ? OR GameDescription REGEXP ? OR DeveloperName REGEXP ? OR PublisherName REGEXP ? " +
                "GROUP BY G.GameID " +
                "ORDER BY GameReviewCount DESC";

        PreparedStatement preparedStatement = databaseConnection.prepareStatement(query);
        for (int i = 1; i <= 4; i++) {
            preparedStatement.setString(i, regexPattern);
        }

        List<Game> games = new LinkedList<>();
        java.util.Date currentDate = new java.util.Date();
        ResultSet results = preparedStatement.executeQuery();

        while (results.next()) {

            Date releaseDate = results.getDate("ReleaseDate");
            ReleaseStatus releaseStatus = null;
            Float averageGameScore = null;
            HashSet<String> gameGenres = null;

            if (releaseDate == null || currentDate.before(releaseDate)){
                releaseStatus = ReleaseStatus.UNRELEASED;
            } else {
                releaseStatus = ReleaseStatus.RELEASED;
            }

            if (releaseStatus == ReleaseStatus.RELEASED && results.getFloat("AverageReviewScore") != 0.0f){
                averageGameScore = results.getFloat("AverageReviewScore");
            }

            if (results.getString("GameGenres") != null){
                gameGenres = new HashSet<>(List.of(results.getString("GameGenres").split(",")));
            }

            games.add(new Game(
                    results.getInt("GameID"),
                    results.getString("GameTitle"),
                    results.getString("GameDescription"),
                    results.getString("GameCoverArtLink"),
                    results.getString("GameBannerArtLink"),
                    results.getString("GameTrailerLink"),
                    results.getString("DeveloperName"),
                    results.getString("PublisherName"),
                    gameGenres,
                    releaseStatus,
                    releaseDate,
                    averageGameScore
            ));
        }

        return games;

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = SQLException.class)
    public void save(Game game) throws SQLException { // Violates the SRP but W/E - Ideally we should have a GameGenreDAO and create GameObjects thru GameService using GameDAO and GameGenreDAO classes

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
        gameStatement.setDate(7, new java.sql.Date(game.getReleaseDate().getTime()));

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

        final String query  = "UPDATE Game SET GameTitle = ?, GameDescription = ?, GameTrailerLink = ?, GameCoverArtLink = ?, GameBannerArtLink = ?, DeveloperName = ?, PublisherName = ?, ReleaseDate = ? WHERE GameID = ?";
        PreparedStatement statement = databaseConnection.prepareStatement(query);

        statement.setString(1, game.getGameTitle());
        statement.setString(2, game.getGameDescription());
        statement.setString(3, game.getGameTrailerLink());
        statement.setString(4, game.getGameCoverArtLink());
        statement.setString(5, game.getGameBannerArtLink());
        statement.setString(6, game.getDeveloperName());
        statement.setString(7, game.getPublisherName());
        statement.setDate(8, (java.sql.Date)game.getReleaseDate());
        statement.setInt(9, game.getGameID());

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

        final String query  = "SELECT G.*, GROUP_CONCAT(GG.GameGenre) AS GameGenres, AVG(GR.ReviewScore) AS AverageReviewScore, SUM(CASE WHEN GR.ReviewPostDate >= CURDATE() - INTERVAL 30 DAY THEN 1 ELSE 0 END) AS ReviewsThisMonth FROM Game G LEFT JOIN GameReview GR ON G.GameID = GR.GameID LEFT JOIN GameGenre GG ON G.GameID = GG.GameID WHERE G.ReleaseDate <= CURDATE() AND G.GameID != 1 AND G.GameBannerArtLink LIKE \"https://images.gog-statics.com%\" GROUP BY G.GameID ORDER BY ReleaseDate DESC LIMIT 10";
        PreparedStatement statement = databaseConnection.prepareStatement(query);
        List<Game> games = new LinkedList<>();
        ResultSet results = statement.executeQuery();

        java.util.Date currentDate = new java.util.Date();

        while (results.next()) {

            Date releaseDate = results.getDate("ReleaseDate");
            ReleaseStatus releaseStatus = null;
            Float averageGameScore = null;

            if (results.getDate("ReleaseDate") == null || currentDate.before(results.getDate("ReleaseDate"))){
                releaseStatus = ReleaseStatus.UNRELEASED;
            }
            else {
                releaseStatus = ReleaseStatus.RELEASED;
            }

            if (releaseStatus == ReleaseStatus.RELEASED && results.getFloat("AverageReviewScore") != 0.0f){
                averageGameScore = results.getFloat("AverageReviewScore");
            }

            games.add(new Game(
                    results.getInt("GameID"),
                    results.getString("GameTitle"),
                    results.getString("GameDescription"),
                    results.getString("GameCoverArtLink"),
                    results.getString("GameBannerArtLink"),
                    results.getString("GameTrailerLink"),
                    results.getString("DeveloperName"),
                    results.getString("PublisherName"),
                    new HashSet<String>(List.of(results.getString("GameGenres").split(","))),
                    releaseStatus,
                    releaseDate,
                    averageGameScore)
            );

        }

        return games;

    }

    @Override
    public List<Game> getUpcoming() throws SQLException {

        final String query  = "SELECT G.*, GROUP_CONCAT(GG.GameGenre) AS GameGenres, AVG(GR.ReviewScore) AS AverageReviewScore FROM Game G LEFT JOIN GameReview GR ON G.GameID = GR.GameID LEFT JOIN GameGenre GG ON G.GameID = GG.GameID WHERE G.ReleaseDate > CURDATE() GROUP BY G.GameID ORDER BY G.ReleaseDate DESC LIMIT 10";
        PreparedStatement statement = databaseConnection.prepareStatement(query);
        List<Game> games = new LinkedList<>();
        ResultSet results = statement.executeQuery();

        java.util.Date currentDate = new java.util.Date();

        while (results.next()) {

            Date releaseDate = results.getDate("ReleaseDate");
            ReleaseStatus releaseStatus = null;
            Float averageGameScore = null;

            if (results.getDate("ReleaseDate") == null || currentDate.before(results.getDate("ReleaseDate"))){
                releaseStatus = ReleaseStatus.UNRELEASED;
            }
            else {
                releaseStatus = ReleaseStatus.RELEASED;
            }

            if (releaseStatus == ReleaseStatus.RELEASED && results.getFloat("AverageReviewScore") != 0.0f){
                averageGameScore = results.getFloat("AverageReviewScore");
            }

            games.add(new Game(
                    results.getInt("GameID"),
                    results.getString("GameTitle"),
                    results.getString("GameDescription"),
                    results.getString("GameCoverArtLink"),
                    results.getString("GameBannerArtLink"),
                    results.getString("GameTrailerLink"),
                    results.getString("DeveloperName"),
                    results.getString("PublisherName"),
                    new HashSet<String>(List.of(results.getString("GameGenres").split(","))),
                    releaseStatus,
                    releaseDate,
                    averageGameScore)
            );

        }

        return games;

    }

    @Override
    public List<Game> getRecent() throws SQLException {

        final String query  = "SELECT G.*, GROUP_CONCAT(GG.GameGenre) AS GameGenres, AVG(GR.ReviewScore) AS AverageReviewScore FROM Game G LEFT JOIN GameReview GR ON G.GameID = GR.GameID LEFT JOIN GameGenre GG ON G.GameID = GG.GameID WHERE G.ReleaseDate <= CURDATE() GROUP BY G.GameID ORDER BY G.ReleaseDate DESC LIMIT 10";
        PreparedStatement statement = databaseConnection.prepareStatement(query);
        List<Game> games = new LinkedList<>();
        ResultSet results = statement.executeQuery();

        java.util.Date currentDate = new java.util.Date();

        while (results.next()) {

            Date releaseDate = results.getDate("ReleaseDate");
            ReleaseStatus releaseStatus = null;
            Float averageGameScore = null;

            if (results.getDate("ReleaseDate") == null || currentDate.before(results.getDate("ReleaseDate"))){
                releaseStatus = ReleaseStatus.UNRELEASED;
            }
            else {
                releaseStatus = ReleaseStatus.RELEASED;
            }

            if (releaseStatus == ReleaseStatus.RELEASED && results.getFloat("AverageReviewScore") != 0.0f){
                averageGameScore = results.getFloat("AverageReviewScore");
            }

            games.add(new Game(
                    results.getInt("GameID"),
                    results.getString("GameTitle"),
                    results.getString("GameDescription"),
                    results.getString("GameCoverArtLink"),
                    results.getString("GameBannerArtLink"),
                    results.getString("GameTrailerLink"),
                    results.getString("DeveloperName"),
                    results.getString("PublisherName"),
                    new HashSet<String>(List.of(results.getString("GameGenres").split(","))),
                    releaseStatus,
                    releaseDate,
                    averageGameScore)
            );

        }

        return games;

    }

    @Override
    public List<Game> getHighestRated() throws SQLException {

        final String query  = "SELECT G.*, GROUP_CONCAT(GG.GameGenre) AS GameGenres, AVG(GR.ReviewScore) AS AverageReviewScore FROM Game G LEFT JOIN GameReview GR ON G.GameID = GR.GameID LEFT JOIN GameGenre GG ON G.GameID = GG.GameID GROUP BY G.GameID ORDER BY AverageReviewScore DESC LIMIT 10";
        PreparedStatement statement = databaseConnection.prepareStatement(query);
        List<Game> games = new LinkedList<>();
        ResultSet results = statement.executeQuery();

        java.util.Date currentDate = new java.util.Date();

        while (results.next()) {

            Date releaseDate = results.getDate("ReleaseDate");
            ReleaseStatus releaseStatus = null;
            Float averageGameScore = null;

            if (results.getDate("ReleaseDate") == null || currentDate.before(results.getDate("ReleaseDate"))){
                releaseStatus = ReleaseStatus.UNRELEASED;
            }
            else {
                releaseStatus = ReleaseStatus.RELEASED;
            }

            if (releaseStatus == ReleaseStatus.RELEASED && results.getFloat("AverageReviewScore") != 0.0f){
                averageGameScore = results.getFloat("AverageReviewScore");
            }

            games.add(new Game(
                    results.getInt("GameID"),
                    results.getString("GameTitle"),
                    results.getString("GameDescription"),
                    results.getString("GameCoverArtLink"),
                    results.getString("GameBannerArtLink"),
                    results.getString("GameTrailerLink"),
                    results.getString("DeveloperName"),
                    results.getString("PublisherName"),
                    new HashSet<String>(List.of(results.getString("GameGenres").split(","))),
                    releaseStatus,
                    releaseDate,
                    averageGameScore)
            );

        }

        return games;

    }


}
