DROP PROCEDURE IF EXISTS PatientView;

DELIMITER $
CREATE PROCEDURE PatientView(
    IN alias VARCHAR(255),
    OUT o_alias VARCHAR(255),
    OUT o_emailAddress VARCHAR(255))
BEGIN
SELECT
    p.alias,
    p.emailAddress
INTO o_alias, o_emailAddress
FROM Friend f
INNER JOIN Patient p ON (p.alias = f.requester)
WHERE f.requestee = alias
AND f.requestee NOT IN (
    SELECT f2.requester 
    FROM Friend f2 
    WHERE f2.requester = alias
    AND f2.requestee = f.requester);
END
$
DELIMITER;