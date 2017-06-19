CREATE TABLE TEACHER (
    TEACHER_ID INT PRIMARY KEY,
    FIRST_NAME VARCHAR(20),
    LAST_NAME VARCHAR(20)
);
 
CREATE TABLE SUBJECT (
    SUBJECT_ID INT PRIMARY KEY,
    TITLE VARCHAR(40)
);
 
CREATE TABLE "GROUP" (
    GROUP_ID INT PRIMARY KEY,
    "NAME" VARCHAR(20)
);
 
CREATE TABLE SUBJ_TEACH (
    ST_ID INT PRIMARY KEY,
    SUBJECT_ID INT CONSTRAINT FK_ST_SUBJ REFERENCES SUBJECT,
    TEACHER_ID INT CONSTRAINT FK_ST_TECH REFERENCES TEACHER,
    GROUP_ID INT CONSTRAINT FK_ST_GRP REFERENCES "GROUP"
);
 
CREATE TABLE STUDENT (
    STUDENT_ID INT PRIMARY KEY,
    FIRST_NAME VARCHAR(20),
    LAST_NAME VARCHAR(20),
    GROUP_ID INT CONSTRAINT FK_STUD_GRP REFERENCES "GROUP"
);
 
CREATE TABLE MARK (
    MARK_ID INT PRIMARY KEY,
    STUDENT_ID INT CONSTRAINT FK_MRK_STUD REFERENCES STUDENT,
    SUBJECT_ID INT CONSTRAINT FK_MRK_SUBJ REFERENCES SUBJECT,
    "DATE" TIMESTAMP,
    MARK INT
);
 
INSERT INTO TEACHER VALUES
(1, 'Egyik', 'Tanár'),
(2, 'Másik', 'Tanár'),
(3, 'Harmadik', 'Tanár');
 
INSERT INTO SUBJECT VALUES
(1, 'Egyik tantárgy'),
(2, 'Másik tantárgy'),
(3, 'Harmadik tantárgy');
 
INSERT INTO "GROUP" VALUES
(1, 'Az egyetlen csoport');
 
INSERT INTO SUBJ_TEACH VALUES
(1, 1, 1, 1),
(2, 2, 3, 1),
(3, 3, 2, 1);
 
INSERT INTO STUDENT VALUES
(1, 'Egyik', 'Tanuló', 1),
(2, 'Másik', 'Tanuló', 1),
(3, 'Harmadik', 'Tanuló', 1);
 
INSERT INTO MARK VALUES
(1, 1, 1, '2013-11-30 10:31:03', 5),
(2, 2, 1, '2013-11-30 10:31:05', 5),
(3, 3, 1, '2013-11-30 10:31:07', 5),
(4, 1, 2, '2013-12-03 14:16:16', 4),
(5, 2, 2, '2013-12-03 14:16:18', 3),
(6, 3, 2, '2013-12-03 14:16:20', 4),
(7, 2, 3, '2013-12-06 08:54:32', 2),
(8, 3, 3, '2013-12-07 11:01:33', 5);
 
SELECT * FROM MARK;
 
SELECT FIRST_NAME || ' ' || LAST_NAME, MARK FROM STUDENT JOIN MARK on STUDENT.STUDENT_ID = MARK.STUDENT_ID;
 
SELECT
    STUDENT.FIRST_NAME || ' ' || STUDENT.LAST_NAME AS STUDENT_NAME,
    MARK,
    "DATE",
    SUBJECT.TITLE,
    TEACHER.FIRST_NAME || ' ' || TEACHER.LAST_NAME AS TEACHER_NAME
FROM
    STUDENT
    JOIN MARK ON STUDENT.STUDENT_ID = MARK.STUDENT_ID
    JOIN SUBJECT ON MARK.SUBJECT_ID = SUBJECT.SUBJECT_ID
    JOIN SUBJ_TEACH ON STUDENT.GROUP_ID = SUBJ_TEACH.GROUP_ID AND SUBJECT.SUBJECT_ID = SUBJ_TEACH.SUBJECT_ID
    JOIN TEACHER ON SUBJ_TEACH.TEACHER_ID = TEACHER.TEACHER_ID
ORDER BY SUBJECT.TITLE;
