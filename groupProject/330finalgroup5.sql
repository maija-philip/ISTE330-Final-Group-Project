-- ISTE 330.02 Group 5
-- Final Project DB Script
-- Author: Thomas Pawlak, Max Stein

DROP DATABASE IF EXISTS `330finalgroup5`;
CREATE DATABASE `330finalgroup5`;
USE `330finalgroup5`;

/*
	in the backend code, when you add a user,
	populate the account table, and then depending on which type,
	populate faculty or student respectively
    *MAKE SURE account username is the same as id in the respective table*
*/

-- stores list of login creds, username, password (expecting a sha1 hash value),
-- enum for type, student or faculty
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
	`username` varchar(20) NOT NULL PRIMARY KEY,
    `password` varchar(40) NOT NULL,
    `type` ENUM("F","S") 
);

-- stores all faculty with contact info, id should match a username in accounts
DROP TABLE IF EXISTS `faculty`;
CREATE TABLE `faculty` (
	`id` varchar(20) NOT NULL PRIMARY KEY,
    `name` varchar(30) NOT NULL,
    `phone` varchar(20),
    `email` varchar(30),
    `building` varchar(40),
    `office` varchar(20),
    
    CONSTRAINT faculty_id_fk
		FOREIGN KEY (id) REFERENCES account(username)
        ON DELETE CASCADE
);

-- stores all students with contact info, id should match a username in accounts
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
	`id` varchar(20) NOT NULL PRIMARY KEY,
    `name` varchar(30) NOT NULL,
    `email` varchar(30),
    
    CONSTRAINT student_id_fk
		FOREIGN KEY (id) REFERENCES account(username)
        ON DELETE CASCADE
);


-- list of all abstracts, pk is combo of the id of the faculty and the title
DROP TABLE IF EXISTS `faculty_abstract`;
CREATE TABLE `faculty_abstract` (
	`faculty_id` varchar(20) NOT NULL,
    `title` varchar(100) NOT NULL,
    `content` MEDIUMTEXT,
    CONSTRAINT faculty_abstract_pk
		PRIMARY KEY (faculty_id, title),
    CONSTRAINT faculty_abstract_fk
		FOREIGN KEY (faculty_id) REFERENCES faculty(id)
        ON DELETE CASCADE
);

-- list of all faculty interests, pk is combo of the id of the faculty and the interest
DROP TABLE IF EXISTS `faculty_interests`;
CREATE TABLE `faculty_interests` (
	`faculty_id` varchar(20) NOT NULL,
    `interest` varchar(40) NOT NULL,
    CONSTRAINT faculty_interests_pk
		PRIMARY KEY (faculty_id, interest),
	CONSTRAINT faculty_interest_fk
		FOREIGN KEY (faculty_id) REFERENCES faculty(id)
        ON DELETE CASCADE
);

-- list of all student interests, pk is combo of the id of the student and the interest
DROP TABLE IF EXISTS `student_interests`;
CREATE TABLE `student_interests` (
	`student_id` varchar(20) NOT NULL,
    `interest` varchar(40) NOT NULL,
    CONSTRAINT student_interests_pk
		PRIMARY KEY (student_id, interest),
	CONSTRAINT student_interest_fk
		FOREIGN KEY (student_id) REFERENCES student(id)
        ON DELETE CASCADE
);

-- insert account data
INSERT INTO account (username, password, type)
	VALUES ("mep4741", "password", "S"),
	("tap1142", "password", "S"),
	("mhs8558", "password", "S"),
	("war3857", "password", "S"),
	("jns2613", "password", "S");
    
INSERT INTO account (username, password, type)
	VALUES ("jimhab", "password", "F"),
	("shamas", "password", "F"),
	("bricra", "password", "F"),
	("walwhy", "password", "F"),
	("profx", "password", "F");

-- insert student data
INSERT INTO student (id, name, email)
	VALUES ("mep4741", "Maija Philip", "mep4741@rit.edu"),
	("tap1142", "Thomas Pawlak", "tap1142@rit.edu"),
	("mhs8558", "Max Stein", "mhs8558@rit.edu"),
	("war3857", "Will Rost", "war3857@rit.edu"),
	("jns2613", "Julia Sarun", "jns2613@rit.edu");

-- insert prof data
INSERT INTO faculty (id, name, phone, email, building, office)
	VALUES ("jimhab", "Jim Habermas", '4082341029', "jimhab@rit.edu", 'GOL', '#2342'),
	("shamas", "Sharon Mason", '7160931284', "shamas@rit.edu", 'GOL', '#2341'),
	("bricra", "Brian Cranston", '2345469878', "bricra@rit.edu", 'GOL', '#2345'),
	("walwhy", "Walter Whyte", '7062344554', "walwhy@rit.edu", 'GOL', '#3421'),
	("profx", "Prof X", '6504503412', "profx@rit.edu", 'GOL', '#3410');
    
-- insert student interst data
INSERT INTO student_interests (student_id, interest)
	VALUES ("mep4741", "javascript"),
	("tap1142", "mySQL"),
	("mhs8558", "java"),
	("war3857", "java"),
	("jns2613", "mySQL");
    
-- insert prof interest data
INSERT INTO faculty_interests (faculty_id, interest)
	VALUES ("jimhab", "c++"),
	("shamas", "python"),
	("bricra", "java"),
	("walwhy", "mySQL"),
	("profx", "javascript");

-- insert abstract data
INSERT INTO faculty_abstract (faculty_id,title,content)
    VALUES ("bricra", "Let's teach kids to code", "Today’s children are considered digital natives adept at scrolling through their newsfeeds, watching (or creating) viral videos, and playing online games. But are they actually fluent when it comes to using new technologies? Brain Cranston, LEGO Papert Professor of Learning Research at the MIT Media Lab, develops tools and activities to engage people of all ages in creative learning experiences. In this TED Talk, he explains how coding encourages young users to express themselves and work their way from an idea to a full-fledged project."),
    ("jimhab","The lowest level of code","This book details the creation and uses of assembly code. The book begins with introducing the people who are responsible for creating the assembly language. It then describes what the major uses of assembly are and how it is utilized in computer systems in the modern day. It explains how there are different types of assembly currently being used today and illustrates the most important differences between both their composition and their uses. It contains images of each type of assembly code and explains what the code in each image does. It wraps up with a short tutorial in using x86 assembly and explains how to use registers and basic functions with these registers."),
    ("shamas","Now is the time to get your own wings","Looking for inspiration? Sharon Mason is one of the world’s oldest iPhone app developers. As a retired banker, she took on the responsibility as her mother’s sole caretaker. Worried she would be socially isolated from her friends and neighbors, she decided to buy her first computer at 58 years old, which allowed her to make friends online and create 'Excel art' by coloring cells and lines to create geometric patterns. Two years ago, she decided to develop a game app for senior iPhone users, despite knowing nothing about programming."),
    ("profx","The mind behind Linux","Prof X transformed technology twice -- first with the Linux kernel, which helps power the Internet, and again with Git, the source code management system used by developers worldwide. In a rare interview with TED Curator Chris Anderson, Torvalds discusses with remarkable openness the personality traits that prompted his unique philosophy of work, engineering and life. 'I am not a visionary, I'm an engineer,' Torvalds says. 'I'm perfectly happy with all the people who are walking around and just staring at the clouds ... but I'm looking at the ground, and I want to fix the pothole that's right in front of me before I fall in."),
    ("walwhy","The art of code","Walter Whyte was exposed to the world of coding at a young age. Suffice it to say that, he was thrilled when he was tapped by the creators of an online multiplayer game to become a community leader. After researching high school programming classes, he was disappointed by how much of it lacked hands-on learning. Now the co-founder and executive director of Hack Club, Whyte shares how coding is a form of self-expression; a blank canvas with endless possibilities.");
    
    
-- stored procedure to match students to faculty and vice versa
-- when calling this in java file make sure to input the id/username of the user currently logged in,
-- as well as their search term
DROP PROCEDURE IF EXISTS MatchStudentProfessor;
DELIMITER //
CREATE PROCEDURE MatchStudentProfessor(
    in  input_id varchar(20),
    in  search_term varchar(30))
BEGIN

    DECLARE input_type ENUM("S","F");
 
	SELECT type INTO input_type
		FROM account
			WHERE username = input_id;
 
 -- if the user is a student return matching faculty
	IF input_type = "S" THEN
    
    
    SELECT DISTINCT name, email, building, office 
		FROM faculty JOIN faculty_abstract
				ON faculty.id=faculty_abstract.faculty_id
			JOIN faculty_interests
				ON faculty.id=faculty_interests.faculty_id
		WHERE faculty_abstract.title LIKE CONCAT("%", search_term, "%")
			OR faculty_abstract.content LIKE CONCAT("%", search_term, "%")
			OR faculty_interests.interest LIKE CONCAT("%", search_term, "%");


    

-- if the user is a faculty return matching students
    ELSEIF input_type = "F" THEN
    
    
	SELECT DISTINCT name, email
		FROM student JOIN student_interests
			ON student.id=student_interests.student_id
		WHERE student_interests.interest LIKE CONCAT("%", search_term, "%");

    END IF;            
END//

DELIMITER ;



