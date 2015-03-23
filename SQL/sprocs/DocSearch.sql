DROP PROCEDURE IF EXISTS DocSearch;

DELIMITER $
CREATE PROCEDURE DocSearch(
    IN specialization VARCHAR(255),
    IN postalCode VARCHAR(255),
    IN isReviewedByFriend BIT,
    IN lastName VARCHAR(255),
    IN gender INT,
    IN licenseYear INT,
    IN province VARCHAR(255),
    IN rating INT,
    IN city VARCHAR(255),
    IN comments VARCHAR(255),
    OUT o_firstName VARCHAR(255),
    OUT o_lastName VARCHAR(255),
    OUT o_gender VARCHAR(255),
    OUT o_rating DOUBLE,
    OUT o_reviewNum INT
)
BEGIN
SELECT
    d.firstName,
    d.lastName,
    d.gender,
    AVERAGE(r.starRating),
    COUNT(r.reviewID)
INTO o_firstName, o_lastName, o_gender, o_rating, o_reviewNum
FROM Doctor d
INNER JOIN Review r ON (r.reviewee = d.alias)
INNER JOIN hasAddress ha ON (ha.alias = d.alias)
INNER JOIN Address a ON (a.addressID = ha.hasAddress)
INNER JOIN hasSpecialization hs ON (hs.alias = d.alias)
INNER JOIN Specialization s ON (s.specializationID = hs.hasSpecialization)
WHERE
    s.specializationID = specialization
    AND a.postalCode = postalCode
    AND (isReviewedByFriend = 0
        || (EXISTS (
            SELECT 1
            FROM Friend
            WHERE requester = r.reviewer
                AND requestee = d.alias
        ) AND EXISTS (
            SELECT 1
            FROM Friend
            WHERE requester = d.alias
                AND requestee = r.reviewer
        )
    ))
    AND d.lastName LIKE CONCAT('%', lastName ,'%')
    AND d.gender = gender
    AND d.licenseYear < licenseYear
    AND a.provinceID = provinceID
    AND AVERAGE(r.starRating) >= rating
    AND a.city LIKE CONCAT('%', city ,'%')
    AND LOWER(r.comments) LIKE CONCAT('%', LOWER(comments) , '%');
END
$
DELIMITER;
