package xyz.joystickjury.backend.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.joystickjury.backend.exception.ResourceDoesNotExistException;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class GameService implements iGameService {

    @Autowired
    private GameDAO gameDAO;

    @Override
    public Game getGame(@Min(1) int id) throws SQLException {
        Game game = gameDAO.get(id);
        if (game == null) { throw new ResourceDoesNotExistException("A game with the gameID of " + id + " could not be found."); }
        return game;
    }

    @Override
    public List<Game> getBySeachQuery(@NotNull @Min(1) String searchQuery) throws SQLException {

        List<String> searchQueryList = List.of(searchQuery.split(" "));
        Set<String> stopWords = new HashSet<>(Arrays.asList("the", "is", "at", "which", "on")); // Words we wish to ignore
        searchQueryList = searchQueryList.stream().filter(word -> !stopWords.contains(word)).collect(Collectors.toList());

        if (searchQuery.isEmpty()) {
            return null;
        }

        List<Game> games = gameDAO.getAll(searchQueryList);
        return games;

    }

    @Override
    public List<Game> getAllGames() throws SQLException {
        return gameDAO.getAll();
    }

    @Override
    public void saveGame(@NotNull Game newGame) throws SQLException {
        gameDAO.save(newGame);
    }

    @Override
    public void updateGame(@NotNull Game updatedGame) throws SQLException {
        if (updatedGame.getGameID() == null) { throw new IllegalArgumentException("A game must have a non null GameID"); }
        else if (!gameExists(updatedGame.getGameID())){ throw new ResourceDoesNotExistException("A game with the gameID of " + updatedGame.getGameID() + " could not be found."); }
        gameDAO.update(updatedGame);
    }


    @Override
    public void deleteGame(@Min(1) int gameID) throws SQLException {
        if (!gameExists(gameID)){ throw new ResourceDoesNotExistException("A game with the gameID of " + gameID + " could not be found."); }
        gameDAO.delete(gameID);
    }

    @Override
    public List<Game> getTrending() throws SQLException{
        return gameDAO.getTrending();
    }

    @Override
    public List<Game> getUpcoming() throws SQLException {
        return gameDAO.getUpcoming();
    }

    @Override
    public List<Game> getRecent() throws SQLException {
        return gameDAO.getRecent();
    }

    @Override
    public List<Game> getHighestRated() throws SQLException {
        return gameDAO.getHighestRated();
    }

    @Override
    public boolean gameExists(@Min(1) int id) throws SQLException { return gameDAO.get(id) != null; } // If you simply want to check gameExists in other classes, use this, else if you actually need the game after, simply use get and check if its null

    @Override
    public boolean gameIsReleased(@Min(1) int gameID) throws SQLException {
        Game game = gameDAO.get(gameID);
        if (game == null) { throw new ResourceDoesNotExistException("A game with the gameID of " + gameID + " could not be found."); }
        return game.getReleaseStatus() == ReleaseStatus.RELEASED;
    }

}
