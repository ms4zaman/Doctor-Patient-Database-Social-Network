DROP PROCEDURE IF EXISTS UserAuthentication;

DELIMITER $

CREATE PROCEDURE UserAuthentication 
    (IN iUserID VARCHAR(255),   
     IN iPswd VARCHAR (255),
    OUT valid INT) 
BEGIN   
    DECLARE vSalt VARCHAR (4000);
    DECLARE vHash VARCHAR (4000);
    SELECT Salt INTO vSalt  FROM GENERATEDSALTS WHERE userID = iUserID;

    SET vHash = SHA2(CONCAT(iPswd, vSalt),256);

    If (SELECT 1 FROM GENERATEDHASHS WHERE hash = vHash AND userID = iUserID) THEN
        SET valid = 1;
    Else
        SET valid = 0;
    END IF;
END
$
DELIMITER;