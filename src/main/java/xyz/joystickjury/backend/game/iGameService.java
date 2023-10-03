package xyz.joystickjury.backend.game;

import javax.validation.constraints.Min;
import java.sql.SQLException;
import java.util.List;


public interface iGameService {

    public Game getGame(int id) throws SQLException;
    public List<Game> getAllGames() throws SQLException;
    public void saveGame(Game t) throws SQLException;
    public void updateGame(Game t) throws SQLException;
    public void deleteGame(int gameID) throws SQLException;
    public List<Game> getTrending() throws SQLException;
    public List<Game> getUpcoming() throws SQLException;
    public List<Game> getRecent() throws SQLException;
    public List<Game> getHighestRated() throws SQLException;
    public boolean gameExists(@Min(1) int id) throws SQLException;

}
