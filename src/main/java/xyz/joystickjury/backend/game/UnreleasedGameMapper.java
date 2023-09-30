package xyz.joystickjury.backend.game;

public class UnreleasedGameMapper implements iUnreleasedGameMapper{

    @Override
    public UnreleasedGame dtoToEntity(UnreleasedGameDTO dtoObject) {
        return new UnreleasedGame(
                dtoObject.getGameID(),
                dtoObject.getGameTitle(),
                dtoObject.getGameDescription(),
                dtoObject.getGameBannerArtLink(),
                dtoObject.getGameTrailerLink(),
                dtoObject.getDeveloperName(),
                dtoObject.getPublisherName(),
                dtoObject.getGameGenres(),
                dtoObject.getAnticipatedReleaseDate()
        );
    }

    @Override
    public UnreleasedGameDTO entityToDTO(UnreleasedGame entityObject) {
        return new UnreleasedGameDTO(
                entityObject.getGameID(),
                entityObject.getGameTitle(),
                entityObject.getGameDescription(),
                entityObject.getGameBannerArtLink(),
                entityObject.getGameTrailerLink(),
                entityObject.getDeveloperName(),
                entityObject.getPublisherName(),
                entityObject.getGameGenres(),
                entityObject.getAnticipatedReleaseDate()
        );
    }

    @Override
    public SimpleGameDTO entityToSimpleDTO(UnreleasedGame entityObject) {
        return new SimpleGameDTO(entityObject.getGameID(), entityObject.getGameTitle(), entityObject.getGameBannerArtLink());
    }

}
