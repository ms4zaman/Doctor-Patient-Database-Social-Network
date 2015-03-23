DROP PROCEDURE IF EXISTS FriendSearch;

DELIMITER $
CREATE PROCEDURE FriendSearch
    (IN friendA VARCHAR(255),   
     IN friendB VARCHAR (255),
    OUT val BIT)
BEGIN
    --SELECT CASE WHEN
    IF (EXISTS (
        SELECT 1
        FROM Friend
        WHERE requester = friendA
            AND requestee = friendB
    ) AND EXISTS (
        SELECT 1
        FROM Friend
        WHERE requester = friendB
            AND requestee = friendA
    ))
    THEN
        BEGIN
            SET val=1;
        END;
    ELSE
        BEGIN 
            SET val=0;
        END;
    END IF;
END
$
DELIMITER ;