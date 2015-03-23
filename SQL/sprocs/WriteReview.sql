DROP PROCEDURE IF EXISTS WriteReview;

DELIMITER $
CREATE PROCEDURE WriteReview(
IN patAlias VARCHAR(255),
IN docAlais VARCHAR(255),
IN starRating INT,
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
    patAlias,
    docAlais,
    starRating,
    comments,
    NOW()
);
END
$
DELIMITER;