DROP PROCEDURE IF EXISTS Test_ResetDB;

DELIMITER $

CREATE PROCEDURE Test_ResetDB()
BEGIN

DELETE FROM Addres;
DELETE FROM Doctor;
DELETE FROM Friend;
DELETE FROM Patient;
DELETE FROM Province;
DELETE FROM Review;
DELETE FROM Specialization;
DELETE FROM Users;
DELETE FROM hasAddress;
DELETE FROM hasSpecialization;

-- Populate Province Table
INSERT INTO Province (provinceID, provinceName) VALUES ('AB','Alberta');
INSERT INTO Province (provinceID, provinceName) VALUES ('BC','British Columbia');
INSERT INTO Province (provinceID, provinceName) VALUES ('MB','Manitoba');
INSERT INTO Province (provinceID, provinceName) VALUES ('NB','New Brunswick');
INSERT INTO Province (provinceID, provinceName) VALUES ('NL','Newfoundland and Labrador');
INSERT INTO Province (provinceID, provinceName) VALUES ('NS','Nova Scotia');
INSERT INTO Province (provinceID, provinceName) VALUES ('NT','Northwest Territories');
INSERT INTO Province (provinceID, provinceName) VALUES ('NU','Nunavut');
INSERT INTO Province (provinceID, provinceName) VALUES ('ON','Ontario');
INSERT INTO Province (provinceID, provinceName) VALUES ('PE','Prince Edward Island');
INSERT INTO Province (provinceID, provinceName) VALUES ('QC','Quebec');
INSERT INTO Province (provinceID, provinceName) VALUES ('SK','Saskatchewan');
INSERT INTO Province (provinceID, provinceName) VALUES ('YT','Yukon');


--Populate initial Doctors
INSERT INTO Users (alias, level) VALUES ('doc_aiken', '1');
INSERT INTO Doctor (alias, firstname, lastname, gender, email, licenseYear) VALUES ('doc_aiken', 'John', 'Aikenhead', 1, 'aiken@head.com', 1990);
INSERT INTO Address (number, streetName, city, provinceID, postalCode) VALUES (1, 'Elizabeth Street', 'Waterloo', 'ON', 'N2L2W8');
INSERT INTO Address (number, streetName, city, provinceID, postalCode) VALUES (2, 'Aikenhead Street', 'Kitchener', 'ON', 'N2P1K2');
INSERT INTO hasAddress (alias, hasAddress) VALUES ('doc_aiken', 1);
INSERT INTO hasAddress (alias, hasAddress) VALUES ('doc_aiken', 2);
INSERT INTO Specialization (specializationName) VALUES ('allergologist');
INSERT INTO Specialization (specializationName) VALUES ('naturopath');
INSERT INTO hasSpecialization (alias, hasSpecialization) VALUES ('doc_aiken', 1);
INSERT INTO hasSpecialization (alias, hasSpecialization) VALUES ('doc_aiken', 2);

INSERT INTO Users (alias, level) VALUES ('doc_amnio', '1');
INSERT INTO Doctor (alias, firstname, lastname, gender, email, licenseYear) VALUES ('doc_amnio', 'Jane', 'Amniotic', 2, 'obgyn_clinic@rogers.com', 2005);
INSERT INTO Address (number, streetName, city, provinceID, postalCode) VALUES (1, 'Jane Street', 'Waterloo', 'ON', 'N2L2W8');
INSERT INTO Address (number, streetName, city, provinceID, postalCode) VALUES (2, 'Amniotic Street', 'Kitchener', 'ON', 'N2P2K5');
INSERT INTO hasAddress (alias, hasAddress) VALUES ('doc_amnio', 3);
INSERT INTO hasAddress (alias, hasAddress) VALUES ('doc_amnio', 4);
INSERT INTO Specialization (specializationName) VALUES ('obstetrician');
INSERT INTO Specialization (specializationName) VALUES ('gynecologist');
INSERT INTO hasSpecialization (alias, hasSpecialization) VALUES ('doc_amnio', 3);
INSERT INTO hasSpecialization (alias, hasSpecialization) VALUES ('doc_amnio', 4);

INSERT INTO Users (alias, level) VALUES ('doc_umbilical', '1');
INSERT INTO Doctor (alias, firstname, lastname, gender, email, licenseYear) VALUES ('doc_umbilical', 'Mary', 'Umbilical', 2, 'obgyn_clinic@rogers.com', 2006);
INSERT INTO Address (number, streetName, city, provinceID, postalCode) VALUES (1, 'Mary Street', 'Cambridge', 'ON', 'N2L1A2');
INSERT INTO Address (number, streetName, city, provinceID, postalCode) VALUES (2, 'Amniotic Street', 'Kitchener', 'ON', 'N2P2K5');
INSERT INTO hasAddress (alias, hasAddress) VALUES ('doc_umbilical', 5);
INSERT INTO hasAddress (alias, hasAddress) VALUES ('doc_umbilical', 6);
INSERT INTO Specialization (specializationName) VALUES ('obstetrician');
INSERT INTO Specialization (specializationName) VALUES ('naturopath');
INSERT INTO hasSpecialization (alias, hasSpecialization) VALUES ('doc_umbilical', 5);
INSERT INTO hasSpecialization (alias, hasSpecialization) VALUES ('doc_umbilical', 6);

INSERT INTO Users (alias, level) VALUES ('doc_heart', '1');
INSERT INTO Doctor (alias, firstname, lastname, gender, email, licenseYear) VALUES ('doc_heart', 'Jack', 'Hearty', 1, 'aiken@head.com', 1980);
INSERT INTO Address (number, streetName, city, provinceID, postalCode) VALUES (1, 'Jack Street', 'Guelph', 'ON', 'N2L1G2');
INSERT INTO Address (number, streetName, city, provinceID, postalCode) VALUES (2, 'Heart Street', 'Waterloo', 'ON', 'N2P2W5');
INSERT INTO hasAddress (alias, hasAddress) VALUES ('doc_heart', 7);
INSERT INTO hasAddress (alias, hasAddress) VALUES ('doc_heart', 8);
INSERT INTO Specialization (specializationName) VALUES ('cardiologist');
INSERT INTO Specialization (specializationName) VALUES ('surgeon');
INSERT INTO hasSpecialization (alias, hasSpecialization) VALUES ('doc_heart', 7);
INSERT INTO hasSpecialization (alias, hasSpecialization) VALUES ('doc_heart', 8);

INSERT INTO Users (alias, level) VALUES ('doc_cutter', '1');
INSERT INTO Doctor (alias, firstname, lastname, gender, email, licenseYear) VALUES ('doc_cutter', 'Beth', 'Cutter', 2, 'beth@tummytuck.com', 2014);
INSERT INTO Address (number, streetName, city, provinceID, postalCode) VALUES (1, 'Beth Street', 'Cambridge', 'ON', 'N2L1C2');
INSERT INTO Address (number, streetName, city, provinceID, postalCode) VALUES (2, 'Cutter Street', 'Kitchener', 'ON', 'N2P2K5');
INSERT INTO hasAddress (alias, hasAddress) VALUES ('doc_cutter', 9);
INSERT INTO hasAddress (alias, hasAddress) VALUES ('doc_cutter', 10);
INSERT INTO Specialization (specializationName) VALUES ('surgeon');
INSERT INTO Specialization (specializationName) VALUES ('psychiatrist');
INSERT INTO hasSpecialization (alias, hasSpecialization) VALUES ('doc_cutter', 9);
INSERT INTO hasSpecialization (alias, hasSpecialization) VALUES ('doc_cutter', 10);


--Populate initial Patients
INSERT INTO Users (alias, level) VALUES ('pat_bob', '0');
INSERT INTO Patient (alias, firstname, lastname, provinceID, city, email) VALUES ('pat_bob', 'Bob', 'Bobberson', 'ON', 'Waterloo', 'thebobbersons@sympatico.ca');

INSERT INTO Users (alias, level) VALUES ('pat_peggy', '0');
INSERT INTO Patient (alias, firstname, lastname, provinceID, city, email) VALUES ('pat_peggy', 'Peggy', 'Bobberson', 'ON', 'Waterloo', 'thebobbersons@sympatico.ca');

INSERT INTO Users (alias, level) VALUES ('pat_homer', '0');
INSERT INTO Patient (alias, firstname, lastname, provinceID, city, email) VALUES ('pat_homer', 'Homer', 'Homerson', 'ON', 'Kitchener', 'homer@rogers.com');

INSERT INTO Users (alias, level) VALUES ('pat_kate', '0');
INSERT INTO Patient (alias, firstname, lastname, provinceID, city, email) VALUES ('pat_kate', 'Kate', 'Katemyer', 'ON', 'Cambridge', 'kate@hello.com');

INSERT INTO Users (alias, level) VALUES ('pat_anne', '0');
INSERT INTO Patient (alias, firstname, lastname, provinceID, city, email) VALUES ('pat_anne', 'Anne', 'MacDonald', 'ON', 'Guelph', 'anne@gmail.com');

END
$
DELIMITER;
