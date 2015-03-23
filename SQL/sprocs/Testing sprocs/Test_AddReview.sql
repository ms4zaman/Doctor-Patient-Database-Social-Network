DROP PROCEDURE IF EXISTS Test_AddReview;

DELIMITER $
CREATE PROCEDURE Test_AddReview(
IN patient_alias VARCHAR(20),
IN doctor_alias VARCHAR(20),
IN star_rating FLOAT,
IN comments VARCHAR(1000))
BEGIN
INSERT INTO Review(
    reviewer,
    reviewee,
    starRating,
    comments,
    creationDate
)
VALUES (
    patient_alias,
    doctor_alias,
    star_rating,
    comments,
    NOW()
);
END
$
DELIMITER;