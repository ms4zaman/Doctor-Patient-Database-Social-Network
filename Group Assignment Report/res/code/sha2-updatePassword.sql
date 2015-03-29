DROP PROCEDURE IF EXISTS UpdatePassword;

DELIMITER $

CREATE PROCEDURE UpdatePassword
    (IN iUserID VARCHAR(255),   
    IN oldPswd VARCHAR (255),
    IN newPswd VARCHAR (255),
    OUT valid INT) 
BEGIN
    Declare vSalt VARCHAR (4000);
    Declare oldHash VARCHAR (4000);
    Declare newHash VARCHAR (4000);

    Select Salt Into vSalt  FROM GENERATEDSALTS WHERE userID = iUserID;

    SET oldHash = SHA2(CONCAT(oldPswd, vSalt),256);
    SET newHash = SHA2(CONCAT (newPswd, vSalt),256);

    If (Select 1 from GENERATEDHASHS where Hash = oldHash and userID = iUserID) Then 
        Update GENERATEDHASHS 
        Set hash = newHash
        Where hash = oldHash and userID = iUserID;
        Set valid = 1;
    ELSE    
        Set valid = 0;
    END IF;
END
$
DELIMITER;
