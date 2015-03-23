DROP PROCEDURE IF EXISTS Test_RequestFriend;

DELIMITER @@
CREATE PROCEDURE Test_RequestFriend(
	IN requestor_alias VARCHAR(20),
	IN requestee_alias VARCHAR(20))
BEGIN

INSERT INTO Friend
VALUES (requestor_alias, requestee_alias);

END
@@
DELIMITER;