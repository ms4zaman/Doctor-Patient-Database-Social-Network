DROP PROCEDURE IF EXISTS SeeFriends;

DELIMITER $
CREATE PROCEDURE SeeFriends
  (IN i_alias VARCHAR(255))
BEGIN
  SELECT
    f.requestee
  FROM Friend f
  WHERE f.requester = i_alias
    AND EXISTS(
      SELECT 1
      FROM Friend inner_f
      WHERE inner_f.requestee = i_alias
        AND inner_f.requester = f.requestee
    );
END
$
DELIMITER ;