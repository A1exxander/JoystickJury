package xyz.joystickjury.backend.game;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;


public class GameDAOTest {

    private static GameDAO gameDAO;

    @BeforeAll
    public static void setup(){
        gameDAO = new GameDAO();
    }

    @AfterAll
    public static void tearDown(){
        gameDAO = null;
    }

    @Nested
    public class GetGameTest {

        @Test
        public void should_GetCorrectGame_When_RequestingExistingGame() throws SQLException {
            Game game = gameDAO.get(1);
            assertEquals("Baldur's Gate 3", game.getGameTitle());
        }

        @Test
        public void should_ReturnNull_When_RequestingNonExistentGame() throws SQLException {
            assertNull(gameDAO.get(0));
        }

    }

    @Nested
    public class GetAllGamesTest {

        @Test
        public void should_GetAllGames_WhenCalled() throws SQLException {
            assertTrue(gameDAO.getAll().size() > 1); // Assuming there's more than one game in the DB
        }

    }

    @Nested
    public class SaveGameTest {

        @Test
        public void should_CreateGame_When_SavingNewGame() throws SQLException {
            // Assuming we have a constructor that sets the game's name and other attributes
            Game newGame = new Game(1, "TestGame", "A very good game.. yeah", "Test", "Test", "Test", "Larian Studios", "Larian Studios", new HashSet<>(), ReleaseStatus.RELEASED, new Date(), null);
            gameDAO.save(newGame);
            Game fetchedGame = gameDAO.get("TestGame");
            assertEquals(newGame.getGameTitle(), fetchedGame.getGameTitle());
        }

    }

    @Nested
    public class DeleteGameTest {

        @Test
        public void should_DeleteGame_WhenDeletingExistingGame() throws SQLException {
            Game fetchedGame = gameDAO.get("TestGame");
            gameDAO.delete(fetchedGame.getGameID());
            assertNull(gameDAO.get(fetchedGame.getGameID()));
        }

    }


}
