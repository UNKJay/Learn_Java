DROP TABLE IF EXISTS `course`;
CREATE TABLE `course` (
  `courseId` char(5),
  `subjectId` char(4) NOT NULL,
  `courseNumber` integer,
  `title` varchar(50) NOT NULL ,
  `numOfCredits` integer,
  PRIMARY KEY  (`courseId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `ssn` char(9),
  `firstName` varchar(25) ,
  `mi` char(1),
  `lastName` varchar(25),
  `birthDate` date,
  `street` varchar(25),
  `phone` char(11),
  `zipCode` char(5),
  `deptId` char(4),
  PRIMARY KEY  (`ssn`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into student values('111111111', 'aaa','j','111','1998-01-01','wallstreet','139-0000000','12345','d001');
insert into student values('111111112', 'bbb','k','222','1998-02-02','wallstreet','139-0000001','12345','d001');
insert into student values('111111113', 'ccc','l','333','1998-03-03','wallstreet','139-0000002','12345','d001');
insert into student values('111111114', 'aaa','m','111','1998-04-04','wallstreet','139-0000003','12345','d001');
insert into student values('111111115', 'bbb','n','222','1998-05-05','wallstreet','139-0000004','12345','d001');
insert into student values('111111116', 'ccc','o','333','1998-05-05','wallstreet','139-0000005','12345','d001');

DROP TABLE IF EXISTS `enrollment`;
CREATE TABLE `enrollment` (
  `ssn` char(9),
  `courseId` char(5) ,
  `mi` char(1),
  `dateRegistered` date,
  `grade` char(1),
  PRIMARY KEY  (`ssn`,`courseId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

drop function if exists studentFound;
delimiter //
create function studentFound(firstN varchar(20),lastN varchar(20))
	returns int
begin
	declare res int;
	select count(*) into res from student
		where student.firstName = firstN and  student.lastName = lastN;
	return res;
end;
//
delimiter ;

drop procedure if exists p1;
delimiter //
CREATE DEFINER=`root`@`localhost` PROCEDURE `p1`(in n int)
begin
declare total int default 0;
declare num int default 0;
while num < n do
set num:=num+1;
set total:=total+num;
end while;
select total;
end
//
