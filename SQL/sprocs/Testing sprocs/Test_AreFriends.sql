DROP PROCEDURE IF EXISTS Test_AreFriends;

DELIMITER @@
CREATE PROCEDURE Test_AreFriends(
	IN patient_alias_1 VARCHAR(20),
	IN patient_alias_2 VARCHAR(20),
	OUT are_friends INT)
BEGIN

IF EXISTS (SELECT * FROM Friend WHERE Requester = patient_alias_1 AND Requestee = patient_alias_2)
AND EXISTS (SELECT * FROM Friend WHERE Requester = patient_alias_2 AND Requestee = patient_alias_1) THEN
	SET are_friends = 1;
ELSE 
	SET are_friends = 0;
END IF;

END
@@
DELIMITER;