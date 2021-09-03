# Ben Jordan
# 7/22/21
# SQL database configuration file

create database athletes;
use athletes;
create table athlete_list (
fullname varchar(255),
benchpress smallint,
bench_date date,
squat smallint,
squat_date date,
deadlift smallint,
dl_date date
);

### DEFINE NEWENTRY PROCEDURE
DELIMITER $$
CREATE PROCEDURE `newEntry`(fullname varchar(255), benchpress smallint, squat smallint, deadlift smallint)
BEGIN
Insert into athlete_list values(fullname, benchpress, CURRENT_DATE(), squat, CURRENT_DATE(), deadlift, CURRENT_DATE());
END$$

### DEFINE FETCHATHLETE PROCEDURE

CREATE PROCEDURE `fetchAthlete` (fullname varchar(255))
BEGIN
select * from athlete_list where fullname = fullname;
END$$

### DEFINE DELETEATHLETE PROCEDURE

USE `athletes`$$
CREATE PROCEDURE `deleteAthlete` (fullname varchar(255))
BEGIN
delete from athlete_list where fullname = fullname;
END$$