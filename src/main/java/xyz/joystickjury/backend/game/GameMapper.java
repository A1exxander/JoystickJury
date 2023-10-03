package xyz.joystickjury.backend.game;

import org.springframework.stereotype.Component;
import java.util.Set;

@Component
public class GameMapper implements iGameMapper {

    @Override
    public Game dtoToEntity(GameDTO dtoObject) {
        return new Game(dtoObject.getGameID(), dtoObject.getGameTitle(), dtoObject.getGameBannerArtLink(), dtoObject.getGameDescription(), dtoObject.getGameTrailerLink(), dtoObject.getDeveloperName(), dtoObject.getPublisherName(), dtoObject.getGameGenres(), dtoObject.getReleaseStatus(), dtoObject.getReleaseDate(), dtoObject.getAverageRating());
    }

    @Override
    public GameDTO entityToDTO(Game entityObject) {
        return new GameDTO(entityObject.getGameID(), entityObject.getGameTitle(), entityObject.getGameBannerArtLink(), entityObject.getGameDescription(), entityObject.getGameTrailerLink(), entityObject.getDeveloperName(), entityObject.getPublisherName(), entityObject.getGameGenres(), entityObject.getReleaseStatus(), entityObject.getReleaseDate(), entityObject.getAverageRating());
    }

    @Override
    public GameDTO entityToDTO(Game entityObject, Set<String> fields) { // Just awful, ideally we should have Hibernate do this for us. Best hope the morons calling this send the fields using camelCase

        if (!fields.contains("id")){ entityObject.setGameID(null); }
        if (!fields.contains("gameTitle")){ entityObject.setGameTitle(null); }
        if (!fields.contains("gameDescription")){ entityObject.setGameDescription(null); }
        if (!fields.contains("gameBannerArtLink")){ entityObject.setGameBannerArtLink(null); }
        if (!fields.contains("gameTrailerLink")){ entityObject.setGameTrailerLink(null); }
        if (!fields.contains("developerName")) { entityObject.setDeveloperName(null); }
        if (!fields.contains("publisherName")) { entityObject.setPublisherName(null); }
        if (!fields.contains("gameGenres")) { entityObject.setGameGenres(null); }
        if (!fields.contains("releaseStatus")) { entityObject.setReleaseStatus(null); }
        if (!fields.contains("releaseDate")) { entityObject.setReleaseDate(null);}
        if (!fields.contains("averageRating")) {entityObject.setAverageRating(null);}

        return entityToDTO(entityObject);

    }

}

