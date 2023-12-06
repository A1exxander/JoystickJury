package xyz.joystickjury.backend.game;

import xyz.joystickjury.backend.utils.iDAO;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;


public interface iGameDAO extends iDAO<Integer, Game> { // May be better to break these down into separate classes IE TrendingGamesDAO & TrendingGamesService, but it would be too much and I think this simplicity is better
    public Game get(String gameTitle) throws SQLException;
    public List<Game> getAll(List<String> searchQuery) throws SQLException;
    public List<Game> getTrending() throws SQLException;   // Gets a list of games that have the most reviews in the past month
    public List<Game> getUpcoming() throws SQLException;   // Gets a list of games that are not out yet, ordering by ID desc
    public List<Game> getRecent() throws SQLException;     // Gets a list of games that recently came out
    public List<Game> getHighestRated() throws SQLException; // Gets a list of games that have overall the highest amount of 5 star reviews
}
