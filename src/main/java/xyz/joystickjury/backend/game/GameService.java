package xyz.joystickjury.backend.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.joystickjury.backend.exception.ResourceDoesNotExistException;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.sql.SQLException;
import java.util.List;


@Service
public class GameService implements iGameService {

    @Autowired
    private GameDAO gameDAO;

    @Override
    public Game getGame(@Min(1) int id) throws SQLException {
        if (!gameExists(id)){ throw new ResourceDoesNotExistException("Error. A game with the gameID of " + id + " could not be found."); }
        return gameDAO.get(id);
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
        if (updatedGame.getGameID() == null) { throw new IllegalArgumentException("Error. A game must have a non null GameID"); }
        else if (!gameExists(updatedGame.getGameID())){ throw new ResourceDoesNotExistException("Error. A game with the gameID of " + updatedGame.getGameID() + " could not be found."); }
        gameDAO.update(updatedGame);
    }


    @Override
    public void deleteGame(@Min(1) int gameID) throws SQLException {
        if (!gameExists(gameID)){ throw new ResourceDoesNotExistException("Error. A game with the gameID of " + gameID + " could not be found."); }
        gameDAO.delete(gameID);
    }

    @Override
    public List<Game> getTrending() throws SQLException{
        return gameDAO.getTrending();
    }

    @Override
    public List<Game> getUpcoming() {
        return gameDAO.getUpcoming();
    }

    @Override
    public List<Game> getRecent() {
        return gameDAO.getRecent();
    }

    @Override
    public List<Game> getHighestRated() {
        return gameDAO.getHighestRated();
    }

    @Override
    public boolean gameExists(@Min(1) int id) throws SQLException { return gameDAO.get(id) != null; }

}
