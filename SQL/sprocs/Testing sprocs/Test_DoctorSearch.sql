DROP PROCEDURE IF EXISTS Test_DoctorSearch;

DELIMITER $

CREATE PROCEDURE Test_DoctorSearch(
    IN gender VARCHAR(20),
    IN city VARCHAR(20),
    IN specializtion VARCHAR(20),
    IN num_years_liscensed INT,
    OUT num_matches INT)
BEGIN

DECLARE licYear INT;
DECLARE genNum INT;
SET licYear = YEAR(CURDATE()) - num_years_liscensed;
IF gender = "male" THEN
    SET genNum = 1;
ELSEIF gender = "female" THEN
    SET genNum = 2;
END IF;

SELECT 
    COUNT(d.alias)
INTO num_matches
FROM Doctor d
INNER JOIN hasAddress hasA ON (hasA.alias = d.alias)
INNER JOIN Address a ON (a.addressID = hasA.hasAddress)
INNER JOIN hasSpecialization hasS ON (hasS.alias = d.alias)
INNER JOIN Specialization s ON (s.specializationID = hasS.hasSpecialization)
WHERE 
    d.gender = genNum
    AND a.city = city
    AND s.specializationName = specializtion
    AND d.licenseYear <= licYear;

END
$
DELIMITER;
    
