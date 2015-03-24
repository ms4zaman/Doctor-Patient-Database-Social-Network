DROP PROCEDURE IF EXISTS Test_ConfirmFriendRequest;

DELIMITER @@
CREATE PROCEDURE Test_ConfirmFriendRequest(
	IN requestor_alias VARCHAR(20),
	IN requestee_alias VARCHAR(20))
BEGIN

IF EXISTS(
        SELECT * 
        FROM Friend 
        WHERE Requester=requestor_alias 
        AND Requestee=requestee_alias) AND NOT EXISTS (
        SELECT * 
        FROM Friend 
        WHERE Requester=requestee_alias 
        AND Requestee=requestee_alias) THEN
    INSERT INTO Friend
    VALUES (requestee_alias, requestor_alias);
END IF;

END
@@
DELIMITER;