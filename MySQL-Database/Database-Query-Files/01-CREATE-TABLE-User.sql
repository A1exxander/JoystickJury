USE JoystickJury;

DROP TABLE IF EXISTS User;

CREATE TABLE User(

UserID INT NOT NULL UNIQUE AUTO_INCREMENT,
DisplayName VARCHAR(16) NOT NULL UNIQUE,
ProfilePictureLink TEXT DEFAULT("https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png"),
ProfileDescription TEXT NULL,
RegistrationDate DATE NOT NULL,
AccountType ENUM("REVIEWER", "ADMIN") NOT NULL,

INDEX UserIDIndex(UserID),
CONSTRAINT PK Primary Key(UserID)

);
