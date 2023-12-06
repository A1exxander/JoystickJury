package xyz.joystickjury.backend.game;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import xyz.joystickjury.backend.exception.ResourceDoesNotExistException;
import java.sql.SQLException;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class GameServiceTest {

    @Mock
    private GameDAO gameDAOMock;

    @InjectMocks
    private GameService gameService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @AfterEach
    public void tearDown(){
        gameService = null;
        gameDAOMock = null;
    }

    @Nested
    class GetGameTests{

        @Test
        public void getGame_shouldReturnCorrectGame_WhenGameExists() throws SQLException {

            Game game = new Game(1, "Baldur's Gate 3", "A very good game.. yeah", null, null, null, "Larian Studios", "Larian Studios", new HashSet<>(), ReleaseStatus.RELEASED, new Date(), 5.0f); // Can be null if were inserting a new game
            when(gameDAOMock.get(1)).thenReturn(game);
            Game returnedGame = gameService.getGame(1);
            assertEquals(game, returnedGame);

        }

        @Test
        public void getGame_shouldThrowException_WhenGameExists() throws SQLException {

            when(gameDAOMock.get(1)).thenReturn(null);
            assertThrows(ResourceDoesNotExistException.class, () -> {
                gameService.getGame(1);
            });

        }

        @ParameterizedTest
        @ValueSource(ints = {0, -1})
        public void getGame_shouldThrowException_WhenGameIDLessThan1(int gameID) throws SQLException {
            assertThrows(ResourceDoesNotExistException.class, () -> gameService.getGame(gameID));
        }

    }

    @Nested
    class GetAllGamesTests {

        @Test
        public void getAllGames_shouldReturnAllGames_WhenGamesExists() throws SQLException {

            List<Game> gameList = new ArrayList<>(); // Act
            Game gameOne = new Game(1, "Baldur's Gate 3", "A very good game.. yeah", null, null, null, "Larian Studios", "Larian Studios", new HashSet<>(), ReleaseStatus.RELEASED, new Date(), 5.0f); // Can be null if were inserting a new game
            Game gameTwo = new Game(9000, "Cyberpunk 2078", "A very good game.. Not as good as BG3, but yeah...", null, null, null, "CD Projekt Red", "CD Projekt Red", new HashSet<>(), ReleaseStatus.UNRELEASED, new Date(), 4.5f);
            gameList.add(gameOne);
            gameList.add(gameTwo);

            when(gameDAOMock.getAll()).thenReturn(new ArrayList<Game>(Arrays.asList(gameOne, gameTwo)));
            List<Game> gameListResult = gameService.getAllGames(); // Arrange

            Assertions.assertEquals(gameList, gameListResult); // Assert

        }

        @Test
        public void getAllGames_shouldReturnEmptyList_WhenNoGamesExists() throws SQLException {

            when(gameDAOMock.getAll()).thenReturn(null);
            List<Game> result = gameService.getAllGames();
            assertNull(result);

        }

    }

    @Nested
    class SaveGameTest {

        @Test
        public void saveGame_shouldSaveGame_whenCreatingNewValidGame() throws SQLException {

            Game newGame = new Game(null, "Baldur's Gate 3", "A very good game.. yeah", null, null, null, "Larian Studios", "Larian Studios", new HashSet<>(), ReleaseStatus.RELEASED, new Date(), 5.0f); // Can be null if were inserting a new game
            doNothing().when(gameDAOMock).save(newGame);
            gameService.saveGame(newGame);
            verify(gameDAOMock).save(newGame);

        }

    }

    @Nested
    class UpdateGameTests {

        @Test
        public void updateGame_shouldUpdateGame_WhenGameExistsAndValid() throws SQLException {
            Game gameToUpdate = new Game(1, "Game Name", "Description", null, null, null, "Developer", "Publisher", new HashSet<>(), ReleaseStatus.RELEASED, new Date(), 4.5f);
            when(gameDAOMock.get(gameToUpdate.getGameID())).thenReturn(gameToUpdate);
            doNothing().when(gameDAOMock).update(gameToUpdate);

            gameService.updateGame(gameToUpdate);

            verify(gameDAOMock).update(gameToUpdate);
        }

        @Test
        public void updateGame_shouldThrowException_WhenGameIDIsNull() {
            Game gameToUpdate = new Game(null, "Game Name", "Description", null, null, null, "Developer", "Publisher", new HashSet<>(), ReleaseStatus.RELEASED, new Date(), 4.5f);

            assertThrows(IllegalArgumentException.class, () -> gameService.updateGame(gameToUpdate));
        }

        @Test
        public void updateGame_shouldThrowException_WhenGameDoesNotExist() throws SQLException{
            Game gameToUpdate = new Game(1, "Game Name", "Description", null, null, null, "Developer", "Publisher", new HashSet<>(), ReleaseStatus.RELEASED, new Date(), 4.5f);
            when(gameDAOMock.get(gameToUpdate.getGameID())).thenReturn(null);

            assertThrows(ResourceDoesNotExistException.class, () -> gameService.updateGame(gameToUpdate));
        }

    }

    @Nested
    class DeleteGameTests {

        @Test
        public void deleteGame_shouldDeleteGame_WhenGameExists() throws SQLException {
            int gameIdToDelete = 1;
            when(gameDAOMock.get(gameIdToDelete)).thenReturn(new Game());
            doNothing().when(gameDAOMock).delete(gameIdToDelete);

            gameService.deleteGame(gameIdToDelete);

            verify(gameDAOMock).delete(gameIdToDelete);
        }

        @Test
        public void deleteGame_shouldThrowException_WhenGameDoesNotExist() throws SQLException{
            int gameIdToDelete = 1;
            when(gameDAOMock.get(gameIdToDelete)).thenReturn(null);
            assertThrows(ResourceDoesNotExistException.class, () -> gameService.deleteGame(gameIdToDelete));
        }
    }

    @Nested
    class GetSpecificGameListsTests {

        @Test
        public void getTrending_shouldGetListOfTrendingGames() throws SQLException {
            gameService.getTrending();
            verify(gameDAOMock, times(1)).getTrending();
        }

        @Test
        public void getUpcoming_shouldReturnListOfUpcomingGames() throws SQLException {
            gameService.getUpcoming();
            verify(gameDAOMock, times(1)).getUpcoming();
        }

        @Test
        public void getRecent_shouldReturnListOfRecentGames() throws SQLException {
            gameService.getRecent();
            verify(gameDAOMock, times(1)).getRecent();
        }

        @Test
        public void getHighestRated_shouldReturnListOfHighestRatedGames() throws SQLException {
            gameService.getHighestRated();
            verify(gameDAOMock, times(1)).getHighestRated();
        }
    }

    @Nested
    class GameExistsTests {

        @Test
        public void gameExists_ShouldReturnTrue_WhenGameExists() throws SQLException {
            int gameId = 1;
            when(gameDAOMock.get(gameId)).thenReturn(new Game());

            boolean exists = gameService.gameExists(gameId);

            assertTrue(exists);
            verify(gameDAOMock).get(gameId);
        }

        @Test
        public void gameExists_ShouldReturnFalse_WhenGameDoesNotExist() throws SQLException {

            int gameId = 1;
            when(gameDAOMock.get(gameId)).thenReturn(null);

            boolean exists = gameService.gameExists(gameId);

            assertFalse(exists);
            verify(gameDAOMock).get(gameId);

        }

    }

    @Nested
    class GameIsReleasedTests {

        @Test
        public void gameIsReleased_ShouldReturnTrue_WhenGameIsReleased() throws SQLException {

            int gameId = 1;
            Game releasedGame = new Game(1, "Baldur's Gate 3", "A very good game.. yeah", null, null, null, "Larian Studios", "Larian Studios", new HashSet<>(), ReleaseStatus.RELEASED, new Date(), 5.0f); // Can be null if were inserting a new game
            when(gameDAOMock.get(gameId)).thenReturn(releasedGame);

            boolean isReleased = gameService.gameIsReleased(gameId);

            assertTrue(isReleased);
            verify(gameDAOMock).get(gameId);

        }

        @Test
        public void gameIsReleased_ShouldReturnFalse_WhenGameIsNotReleased() throws SQLException {

            int gameId = 1;
            Game unreleasedGame = new Game(3333, "Baldur's Gate 3333", "A very good game.. yeah", null, null, null, "Larian Studios", "Larian Studios", new HashSet<>(), ReleaseStatus.UNRELEASED, new Date(2333, 11, 3), 5.0f); // Can be null if were inserting a new game
            when(gameDAOMock.get(gameId)).thenReturn(unreleasedGame);

            boolean isReleased = gameService.gameIsReleased(gameId);
            assertFalse(isReleased);

        }

        @Test
        public void gameIsReleased_ShouldThrowException_WhenGameDoesNotExist() throws SQLException {
            int gameId = 1;
            when(gameDAOMock.get(gameId)).thenReturn(null);
            assertThrows(ResourceDoesNotExistException.class, () -> gameService.gameIsReleased(gameId));
        }

    }
}