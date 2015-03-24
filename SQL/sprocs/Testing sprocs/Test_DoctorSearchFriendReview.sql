DROP PROCEDURE IF EXISTS Test_DoctorSearchFriendReview;

DELIMITER @@
CREATE PROCEDURE Test_DoctorSearchFriendReview(
    IN patient_alias VARCHAR(20),
    IN review_keyword VARCHAR(20), 
    OUT num_matches INT)
BEGIN

    SELECT 
        COUNT(DISTINCT Reviewee)
    INTO num_matches
    FROM Review
    INNER JOIN Doctor ON (Reviewee = Doctor.alias)
    WHERE Reviewer IN (SELECT f.Requestee
                        FROM Friend f
                        WHERE Requester = patient_alias
                        AND f.Requestee IN (SELECT i.Requester
                                            FROM Friend i
                                            WHERE i.Requestee = patient_alias))
    AND comments LIKE CONCAT('%',review_keyword,'%');

END
@@
DELIMITER;