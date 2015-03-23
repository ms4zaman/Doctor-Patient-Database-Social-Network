DROP PROCEDURE IF EXISTS Test_CheckReviews;

DELIMITER @@
CREATE PROCEDURE Test_CheckReviews(
	IN doc_alias VARCHAR(20),
	OUT avg_star FLOAT,
	OUT num_review INT)
BEGIN

SELECT AVG(r.starRating), COUNT(r.reviewID)
INTO avg_star, num_review
FROM Doctor d
INNER JOIN Review r ON (d.alias = r.reviewee)
WHERE r.reviewee = doc_alias 
GROUP BY r.reviewee;

END
@@
DELIMITER;
