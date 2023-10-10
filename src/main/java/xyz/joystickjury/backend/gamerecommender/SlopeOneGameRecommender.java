package xyz.joystickjury.backend.gamerecommender;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.joystickjury.backend.exception.IllegalOperationException;
import xyz.joystickjury.backend.game.Game;
import xyz.joystickjury.backend.game.GameService;
import xyz.joystickjury.backend.gamereview.GameReview;
import xyz.joystickjury.backend.gamereview.GameReviewService;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;
import javafx.util.Pair;


@Component
@AllArgsConstructor @NoArgsConstructor
public class SlopeOneGameRecommender implements iGameRecommenderStrategy {

    @Autowired
    private GameService gameService;
    @Autowired
    private GameReviewService gameReviewService;

    @Override
    public List<Game> recommendGames(@Min(1) int userID) throws SQLException {

        List<GameReview> userGameReviews = gameReviewService.getAllGameReviewsByUser(userID); // Checks if our user exists for us already
        if (userGameReviews.isEmpty()) { throw new IllegalOperationException("Error. User must post at least 1 review before they can be recommended games"); }

        List<Game> allGames = gameService.getAllGames();

        HashMap<Pair<Integer, Integer>, Float> avgPrefDiffs = computeAvgPrefDiffs(allGames);
        HashMap<Game, Float> predictedPreferenceDiffs = predictPreferenceDiffs(allGames, userGameReviews, avgPrefDiffs);

        List<Game> recommendedGames = predictedPreferenceDiffs.entrySet()
                .stream()
                .sorted(Map.Entry.<Game, Float>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        return recommendedGames;

    }

    private HashMap<Pair<Integer, Integer>, Float> computeAvgPrefDiffs(@NotNull List<Game> allGames) throws SQLException {

        if (allGames.isEmpty()) { throw new IllegalArgumentException("Error. List of games is empty."); }

        HashMap<Integer, List<GameReview>> allGameReviews = new HashMap<>(gameReviewService.getAllGameReviews().stream().collect(Collectors.groupingBy(GameReview::getGameID, Collectors.toList())));
        HashMap<Pair<Integer, Integer>, Float> avgPrefDiffs = new HashMap<>(); // Determines how much better or worse GameOne is to GameTwo on average

        for (Game currentGame : allGames) {
            for (Game otherGame : allGames){
                if (currentGame == otherGame) { continue; }
                int gameOneID = currentGame.getGameID();
                int gameTwoID = otherGame.getGameID();
                Pair<Integer,Integer> key = new Pair<>(gameOneID, gameTwoID);
                avgPrefDiffs.put(key, computeAvgPrefDiff(allGameReviews.get(gameOneID), allGameReviews.get(gameTwoID)));
            }
        }

        return avgPrefDiffs;

    }

    private Float computeAvgPrefDiff(@NotNull List<GameReview> gameOneReviews, @NotNull List<GameReview> gameTwoReviews) throws SQLException {

        if (gameOneReviews.equals(gameOneReviews)){ throw new IllegalOperationException("Error. Cannot compute the preference difference of the same game."); } // We are expecting 2 lists representing reviews of 2 SEPARATE games

        Set<Integer> gameOneReviewers = gameOneReviews.stream().map(currentGameReview -> currentGameReview.getUserID()).collect(Collectors.toSet());
        Set<Integer> gameTwoReviewers = gameTwoReviews.stream().map(currentGameReview -> currentGameReview.getUserID()).collect(Collectors.toSet());

        // Filter GameOneReviews by users who have also reviewed GameTwo and filter GameTwoReviews by users who have also reviewed GameTwo
        List<GameReview> filteredGameOneReviews = gameOneReviews.stream().filter(currentGameReview -> gameTwoReviewers.contains(currentGameReview.getUserID())).collect(Collectors.toList());
        List<GameReview> filteredGameTwoReviews = gameTwoReviews.stream().filter(currentGameReview -> gameOneReviewers.contains(currentGameReview.getUserID())).collect(Collectors.toList());

        int totalReviewScoreDiff = 0;
        int totalReviewScoreDiffCount = gameOneReviews.size(); // GameOneReviews & GameTwoReviews should be equal in size after filtering & ordered by UserID

        if (totalReviewScoreDiffCount == 0) { return null; }

        for (int i = 0; i < totalReviewScoreDiffCount; ++i) {
            totalReviewScoreDiff += gameOneReviews.get(i).getReviewScore() - gameTwoReviews.get(i).getReviewScore();
        }

        return Float.valueOf(totalReviewScoreDiff / totalReviewScoreDiffCount);

    }

    private HashMap<Game, Float> predictPreferenceDiffs(@NotNull List<Game> allGames, @NotNull List<GameReview> userGameReviews, @NotNull HashMap<Pair<Integer, Integer>, Float> avgPreferenceDiffs) throws SQLException {

        Set<Integer> reviewedGameIDs = userGameReviews.stream().map(currentGame -> currentGame.getGameID()).collect(Collectors.toSet());
        HashMap<Game, Float> predictedPrefDiffs = new HashMap<>();

        for (Game currentGame : allGames) {

            if (reviewedGameIDs.contains(currentGame.getGameID())) { continue; }
            Float predictPrefDiff = predictPreferenceDiff(currentGame.getGameID(), userGameReviews, avgPreferenceDiffs);
            predictedPrefDiffs.put(currentGame, predictPrefDiff);

        }

        return predictedPrefDiffs;

    }

    private Float predictPreferenceDiff(@Min(1) int currentGameID, @NotNull List<GameReview> userGameReviews, @NotNull HashMap<Pair<Integer, Integer>, Float> avgPreferenceDiffs) throws SQLException {

        float totalAvgPrefDiffs = 0.0f;
        int totalAvgPrefDiffsCount = 0;

        for (GameReview currentGameReview : userGameReviews) {

            Float avgPreferenceDiff = avgPreferenceDiffs.get(new Pair<>(currentGameID, currentGameReview.getGameID()));
            if (avgPreferenceDiff != null) {
                totalAvgPrefDiffs += avgPreferenceDiff;
                ++totalAvgPrefDiffsCount;
            }

        }

        if (totalAvgPrefDiffsCount == 0) { return null; } else { return totalAvgPrefDiffs / totalAvgPrefDiffsCount; }

    }

}