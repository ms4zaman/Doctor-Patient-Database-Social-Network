DROP PROCEDURE IF EXISTS ADDFRIEND;

DELIMITER $

CREATE PROCEDURE AddFriend(
IN friendA VARCHAR(255),
IN friendB VARCHAR(255))
BEGIN

INSERT INTO Friend
VALUES(friendA, friendB);

INSERT INTO Friend
VALUES(friendB, friendA);

END
$
DELIMITER;