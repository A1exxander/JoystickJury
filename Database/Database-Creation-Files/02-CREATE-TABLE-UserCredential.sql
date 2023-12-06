USE JoystickJury;

DROP TABLE IF EXISTS UserCredential;

CREATE TABLE UserCredential(

UserCredentialID INT NOT NULL UNIQUE AUTO_INCREMENT,
UserID INT NOT NULL UNIQUE, 
Email VARCHAR(64) NOT NULL,
HashedPassword VARCHAR(64) NOT NULL, -- Dont worry about storing salts they are already apart of our hashed password

INDEX UserIDIndex(UserID),
INDEX EmailIndex(Email),
CONSTRAINT MinimumPasswordLength CHECK (LENGTH(HashedPassword) >= 8),
CONSTRAINT FKUserCredentialToUser FOREIGN KEY(UserID) REFERENCES User(UserID) ON UPDATE NO ACTION ON DELETE CASCADE
	
);