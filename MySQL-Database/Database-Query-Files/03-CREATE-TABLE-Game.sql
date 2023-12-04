USE JoystickJury;

DROP TABLE IF EXISTS Game;

CREATE TABLE Game(

GameID INT NOT NULL UNIQUE AUTO_INCREMENT, 
GameTitle VARCHAR(128) NOT NULL, 	-- Do not make unique incase we decide to track a user's previous passwords in the future 
GameDescription VARCHAR(512) NULL,
GameTrailerLink VARCHAR(512) NOT NULL UNIQUE,
GameCoverArtLink VARCHAR(512) NOT NULL UNIQUE,
GameBannerArtLink VARCHAR(512) NOT NULL UNIQUE,
DeveloperName VARCHAR(32) NOT NULL,
PublisherName VARCHAR(32) NOT NULL,
ReleaseDate DATE NULL,

INDEX GameIDIndex(GameID),
CONSTRAINT PK Primary Key(GameID)

);