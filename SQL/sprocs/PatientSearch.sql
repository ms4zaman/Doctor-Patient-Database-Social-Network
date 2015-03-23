DROP PROCEDURE IF EXISTS PatientSearch;

DELIMITER $
CREATE PROCEDURE PatientSearch
    (IN alias VARCHAR(255),   
     IN province VARCHAR (255),
     IN city VARCHAR(255),
    OUT o_alias VARCHAR(255),
    OUT o_province VARCHAR(255),
    OUT o_city VARCHAR(255),
    OUT o_count INT,
    OUT o_max DATE)
BEGIN
    SELECT
        pat.alias,
        prov.provinceName,
        pat.city,
        COUNT(r.reviewID),
        MAX(r.creationDate)
        INTO o_alias, o_province, o_city, o_count, o_max
    FROM Patient pat
    INNER JOIN Province prov ON (pat.provinceID = prov.provinceID)
    INNER JOIN Review r ON (r.reviewee = pat.alias)
    WHERE pat.alias = alias
        AND prov.provinceName = province;
        AND pat.city = city;
END
$
DELIMITER ;

--CALL PatientSearch();
