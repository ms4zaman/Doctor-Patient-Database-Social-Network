DROP PROCEDURE IF EXISTS Test_PatientSearch;

DELIMITER $

CREATE PROCEDURE Test_PatientSearch(
    IN prov VARCHAR(20),
    IN city VARCHAR(20),
    OUT num INT)
BEGIN
    SELECT 
        COUNT(alias)
    INTO num
    FROM Patient pat
    INNER JOIN Province prv ON (pat.provinceID = prv.provinceID)
    WHERE pat.city = city 
        AND prv.provinceName = prov;
END
$
DELIMITER;