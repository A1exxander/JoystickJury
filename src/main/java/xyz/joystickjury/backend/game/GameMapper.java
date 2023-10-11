package xyz.joystickjury.backend.game;

import org.springframework.stereotype.Component;
import xyz.joystickjury.backend.utils.iMapper;
import javax.validation.constraints.NotNull;


@Component
public class GameMapper implements iMapper<Game, GameDTO> {

    @Override
    public Game dtoToEntity(@NotNull GameDTO dtoObject) {
        return new Game(dtoObject.getGameID(), dtoObject.getGameTitle(), dtoObject.getGameBannerArtLink(), dtoObject.getGameDescription(), dtoObject.getGameTrailerLink(), dtoObject.getDeveloperName(), dtoObject.getPublisherName(), dtoObject.getGameGenres(), dtoObject.getReleaseStatus(), dtoObject.getReleaseDate(), dtoObject.getAverageRating());
    }

    @Override
    public GameDTO entityToDTO(@NotNull Game entityObject) {
        return new GameDTO(entityObject.getGameID(), entityObject.getGameTitle(), entityObject.getGameBannerArtLink(), entityObject.getGameDescription(), entityObject.getGameTrailerLink(), entityObject.getDeveloperName(), entityObject.getPublisherName(), entityObject.getGameGenres(), entityObject.getReleaseStatus(), entityObject.getReleaseDate(), entityObject.getAverageRating());
    }

}

