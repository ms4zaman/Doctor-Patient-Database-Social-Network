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
    FROM Patient p
    WHERE p.city = city 
        AND p.provinceID = prov;
END
$
DELIMITER;