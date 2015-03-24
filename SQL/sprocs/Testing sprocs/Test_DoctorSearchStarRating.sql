DROP PROCEDURE IF EXISTS Test_DoctorSearchStarRating;

DELIMITER @@
CREATE PROCEDURE Test_DoctorSearchStarRating(
	IN avg_star_rating FLOAT,
	OUT num_matches INT)
BEGIN

SELECT 
    COUNT(val) 
INTO num_matches
FROM (
    SELECT AVG(r.starRating) AS val
    FROM Doctor d
    INNER JOIN Review r ON (d.alias = r.reviewee)
    GROUP BY r.reviewee) as Temp
WHERE val >= avg_star_rating;


END
@@
DELIMITER;