package xyz.joystickjury.backend.gamerecommender;

import lombok.AllArgsConstructor;
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
import org.apache.commons.lang3.tuple.Pair;

@Component
@AllArgsConstructor
public class SlopeOneGameRecommender implements iGameRecommenderStrategy {

    @Autowired
    private final GameService gameService;
    @Autowired
    private final GameReviewService gameReviewService;

    @Override
    public List<Game> recommendGames(@Min(1) int userID) throws SQLException {

        List<GameReview> userGameReviews = gameReviewService.getAllGameReviewsByUser(userID); // Checks if our user exists for us already
        if (userGameReviews.isEmpty()) { throw new IllegalOperationException("User must post at least 1 review before they can be recommended games"); }

        List<Game> allGames = gameService.getAllGames();

        HashMap<Pair<Integer, Integer>, Float> avgPrefDiffs = computeAvgPrefDiffs(allGames); // Computes the average preference difference between Game X and Game Y
        HashMap<Game, Float> predictedPrefDiffs = predictPrefDiffs(allGames, userGameReviews, avgPrefDiffs); // Determines a predicted preference difference for Game X where X is a Game not rated by our user based off other games the user has expressed a preference toward

        List<Game> recommendedGames = predictedPrefDiffs.entrySet()  // Convert our hashmap into a list ordered by the predicated preference difference descending
                .stream()
                .sorted(Map.Entry.<Game, Float>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        return recommendedGames;

    }

    private HashMap<Pair<Integer, Integer>, Float> computeAvgPrefDiffs(@NotNull List<Game> allGames) throws SQLException {

        HashMap<Integer, List<GameReview>> allGameReviews = new HashMap<>(gameReviewService.getAllGameReviews().stream().collect(Collectors.groupingBy(GameReview::getGameID, Collectors.toList())));
        HashMap<Pair<Integer, Integer>, Float> avgPrefDiffs = new HashMap<>(); // Determines how much better or worse GameOne is to GameTwo on average

        for (Game currentGame : allGames) {
            for (Game otherGame : allGames){
                if (currentGame == otherGame) { continue; } // No reason to compute the average preference difference between the same 2 games, so skip it
                int gameOneID = currentGame.getGameID();
                int gameTwoID = otherGame.getGameID();
                Pair<Integer, Integer> key = Pair.of(gameOneID, gameTwoID);
                avgPrefDiffs.put(key, computeAvgPrefDiff(allGameReviews.get(gameOneID), allGameReviews.get(gameTwoID))); // Compute the preference difference between two separate games
            }
        }

        return avgPrefDiffs;

    }

    private Float computeAvgPrefDiff(@NotNull List<GameReview> gameOneReviews, @NotNull List<GameReview> gameTwoReviews) throws SQLException {

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

    private HashMap<Game, Float> predictPrefDiffs(@NotNull List<Game> allGames, @NotNull List<GameReview> userGameReviews, @NotNull HashMap<Pair<Integer, Integer>, Float> avgPreferenceDiffs) throws SQLException {

        Set<Integer> reviewedGameIDs = userGameReviews.stream().map(currentGame -> currentGame.getGameID()).collect(Collectors.toSet());
        HashMap<Game, Float> predictedPrefDiffs = new HashMap<>();

        for (Game currentGame : allGames) {

            if (reviewedGameIDs.contains(currentGame.getGameID())) { continue; }
            Float predictPrefDiff = predictPrefDiff(currentGame.getGameID(), userGameReviews, avgPreferenceDiffs);
            predictedPrefDiffs.put(currentGame, predictPrefDiff);

        }

        return predictedPrefDiffs;

    }

    private Float predictPrefDiff(@Min(1) int currentGameID, @NotNull List<GameReview> userGameReviews, @NotNull HashMap<Pair<Integer, Integer>, Float> avgPreferenceDiffs) throws SQLException {

        float totalAvgPrefDiffs = 0.0f;
        int totalAvgPrefDiffsCount = 0;

        for (GameReview currentGameReview : userGameReviews) {
            Pair<Integer, Integer> key = Pair.of(currentGameID, currentGameReview.getGameID());
            Float avgPreferenceDiff = avgPreferenceDiffs.get(key);
            if (avgPreferenceDiff != null) {
                totalAvgPrefDiffs += avgPreferenceDiff;
                ++totalAvgPrefDiffsCount;
            }

        }

        if (totalAvgPrefDiffsCount == 0) { return null; } else { return totalAvgPrefDiffs / totalAvgPrefDiffsCount; }

    }

}
