DROP PROCEDURE IF EXISTS SeeFriendRequests;

DELIMITER $
CREATE PROCEDURE SeeFriendRequests
  (IN i_alias VARCHAR(255))
BEGIN
  SELECT
    f.requester,
    p.email
  FROM Friend f
  LEFT JOIN Patient p ON (p.alias = f.requestee)
  WHERE f.requestee = i_alias
    AND NOT EXISTS(
      SELECT 1
      FROM Friend inner_f
      WHERE inner_f.requester = i_alias
        AND inner_f.requestee = f.requester
    );
END
$
DELIMITER ;