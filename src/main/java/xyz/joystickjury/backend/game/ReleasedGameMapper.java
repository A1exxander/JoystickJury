package xyz.joystickjury.backend.game;


public class ReleasedGameMapper implements iReleasedGameMapper{

    @Override
    public ReleasedGame dtoToEntity(ReleasedGameDTO dtoObject) {
       return new ReleasedGame(
                dtoObject.getGameID(),
                dtoObject.getGameTitle(),
                dtoObject.getGameDescription(),
                dtoObject.getGameBannerArtLink(),
                dtoObject.getGameTrailerLink(),
                dtoObject.getDeveloperName(),
                dtoObject.getPublisherName(),
                dtoObject.getGameGenres(),
                dtoObject.getReleaseDate(),
                dtoObject.getAverageRating()
        );
    }

    @Override
    public ReleasedGameDTO entityToDTO(ReleasedGame entityObject) {
        return new ReleasedGameDTO(
                entityObject.getGameID(),
                entityObject.getGameTitle(),
                entityObject.getGameDescription(),
                entityObject.getGameBannerArtLink(),
                entityObject.getGameTrailerLink(),
                entityObject.getDeveloperName(),
                entityObject.getPublisherName(),
                entityObject.getGameGenres(),
                entityObject.getReleaseDate(),
                entityObject.getAverageRating()
        );
    }

    @Override
    public SimpleReleasedGameDTO entityToSimpleDTO(ReleasedGame entityObject) {
        return new SimpleReleasedGameDTO(entityObject.getGameID(), entityObject.getGameTitle(), entityObject.getGameTrailerLink(), entityObject.getAverageRating());
    }

}
