DROP PROCEDURE IF EXISTS Test_RequestFriend;

DELIMITER @@
CREATE PROCEDURE Test_RequestFriend(
	IN requestor_alias VARCHAR(20),
	IN requestee_alias VARCHAR(20))
BEGIN

IF NOT EXISTS (SELECT * FROM Friend WHERE Requester = requestor_alias AND Requestee = requestee_alias) THEN
	INSERT INTO Friend
	VALUES (requestor_alias, requestee_alias);
END IF;

END
@@
DELIMITER;