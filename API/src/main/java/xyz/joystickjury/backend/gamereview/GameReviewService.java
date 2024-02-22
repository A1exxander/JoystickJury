package xyz.joystickjury.backend.gamereview;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.joystickjury.backend.exception.IllegalRequestException;
import xyz.joystickjury.backend.exception.ResourceAlreadyExistsException;
import xyz.joystickjury.backend.exception.ResourceDoesNotExistException;
import xyz.joystickjury.backend.game.GameService;
import xyz.joystickjury.backend.user.UserService;
import javax.validation.constraints.Min;
import java.sql.SQLException;
import java.util.List;

@Service
@AllArgsConstructor
public class GameReviewService implements iGameReviewService {

    @Autowired
    private GameReviewDAO gameReviewDAO;
    @Autowired
    private GameService gameService;
    @Autowired
    private UserService userService;

    @Override
    public GameReview getGameReview(@Min(1) int gameReviewID) throws SQLException {
        GameReview gameReview = gameReviewDAO.get(gameReviewID);
        if (gameReview == null){ throw new ResourceDoesNotExistException("Game review with the ID of : " + gameReviewID + "does not exist." ); }
        return gameReview;
    }

    @Override
    public GameReview getGameReview(@Min(1) int userID, @Min(1) int gameID) throws SQLException {
        GameReview gameReview = gameReviewDAO.get(userID, gameID);
        if (gameReview == null){ throw new ResourceDoesNotExistException("Game review with the user ID of : " + userID + " and game ID of : " + gameID + " does not exist." ); }
        return gameReview;
    }

    @Override
    public List<GameReview> getAllGameReviews() throws SQLException {
        return gameReviewDAO.getAll();
    }

    @Override
    public List<GameReview> getAllGameReviewsByUser(@Min(1) int userID) throws SQLException {
        if (!userService.userExists(userID)) { throw new ResourceDoesNotExistException("User with the ID of : " + userID + "does not exist." ); }
        return gameReviewDAO.getAllByUserID(userID);
    }

    @Override
    public List<GameReview> getAllGameReviewsByGame(@Min(1) int gameID) throws SQLException {
        if (!gameService.gameExists(gameID)) { throw new ResourceDoesNotExistException("Game with the ID of : " + gameID + "does not exist." ); }
        else if (!gameService.gameIsReleased(gameID)) { throw new IllegalRequestException("Game must be released in order to have reviews"); }
        return gameReviewDAO.getAllByGameID(gameID);
    }

    @Override
    public void saveGameReview(GameReview gameReview) throws SQLException {

        int gameID = gameReview.getGameID();
        int userID = gameReview.getUserID();

        if (!gameService.gameExists(gameID)) { throw new ResourceDoesNotExistException("Game with the ID of : " + gameReview.getGameID() + "does not exist." ); }
        else if (!userService.userExists(userID)) { throw new ResourceDoesNotExistException("User with the ID of : " + gameReview.getGameID() + "does not exist." ); }
        else if (!gameService.gameIsReleased(gameID)) { throw new IllegalRequestException("Game must be released in order to post a review"); }
        else if (gameReviewExists(userID, gameID)) { throw new ResourceAlreadyExistsException("User " + userID + "has already left a review for" + gameID +". Please update review instead."); };

        gameReviewDAO.save(gameReview);

    }

    @Override
    public void updateGameReview(GameReview gameReview) throws SQLException {
        if (gameReviewExists(gameReview.getUserID(), gameReview.getGameID())) { throw new ResourceDoesNotExistException("User " + gameReview.getUserID() + "has not left a review for" + gameReview.getGameID() + "."); };
        gameReviewDAO.update(gameReview);
    }

    @Override
    public void deleteGameReview(@Min(1) int gameReviewID) throws SQLException {
        if (!gameReviewExists(gameReviewID)) { throw new ResourceDoesNotExistException("No game review with the ID of " + gameReviewID + " exists."); };
        gameReviewDAO.delete(gameReviewID);
    }

    @Override
    public void deleteGameReview(@Min(1) int userID, @Min(1) int gameReviewID) throws SQLException { // Useful to make sure that user only deletes their own reviews
        GameReview gameReview = getGameReview(gameReviewID);
        if (gameReview != null && gameReview.getUserID() != userID) { throw new ResourceDoesNotExistException("No game review with the ID of " + gameReviewID + " left by user " + userID + " exists."); };
        gameReviewDAO.delete(gameReviewID);
    }

    @Override
    public boolean gameReviewExists(@Min(1) int userID, @Min(1) int gameID) throws SQLException {
        return gameReviewDAO.get(userID, gameID) != null;
    }

    @Override
    public boolean gameReviewExists(@Min(1) int gameReviewID) throws SQLException {
        return getGameReview(gameReviewID) != null;
    }

}
