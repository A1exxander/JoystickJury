package xyz.joystickjury.backend.game;

import org.springframework.stereotype.Component;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GameMapper implements iGameMapper {

    @Override
    public Game dtoToEntity(@NotNull GameDTO dtoObject) {
        return new Game(dtoObject.getGameID(), dtoObject.getGameTitle(), dtoObject.getGameCoverArtLink(), dtoObject.getGameBannerArtLink(), dtoObject.getGameDescription(), dtoObject.getGameTrailerLink(), dtoObject.getDeveloperName(), dtoObject.getPublisherName(), dtoObject.getGameGenres(), dtoObject.getReleaseStatus(), dtoObject.getReleaseDate(), dtoObject.getAverageRating());
    }

    @Override
    public GameDTO entityToDTO(@NotNull Game entityObject) {
        return new GameDTO(entityObject.getGameID(), entityObject.getGameTitle(), entityObject.getGameCoverArtLink(), entityObject.getGameBannerArtLink(), entityObject.getGameDescription(), entityObject.getGameTrailerLink(), entityObject.getDeveloperName(), entityObject.getPublisherName(), entityObject.getGameGenres(), entityObject.getReleaseStatus(), entityObject.getReleaseDate(), entityObject.getAverageRating());
    }

    @Override
    public List<GameDTO> entityToDTOs(@NotNull List<Game> games) {
        return games.stream().map(game -> entityToDTO(game)).collect(Collectors.toList());
    }

}

