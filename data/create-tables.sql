DROP TABLE entries ;
DROP TABLE classcolours ;
DROP TABLE showclasses;
DROP TABLE exhibits;
DROP TABLE exhibitors;
DROP TABLE judges;
DROP TABLE showsections;
DROP TABLE human_ages;
DROP TABLE exhibit_ages;
DROP TABLE human_genders;
DROP TABLE exhibit_genders;
DROP TABLE breedcolours;
DROP TABLE breeds;
DROP TABLE colours;

-- colours table
CREATE TABLE colours (
id INTEGER,
colour VARCHAR(35)UNIQUE,
PRIMARY KEY (id)
);

-- Breeds table 
CREATE TABLE breeds(
id INTEGER,
adult_age INTEGER,
top_pen_req BOOL,
section INTEGER,
breed VARCHAR(30) UNIQUE,
PRIMARY KEY (id)
);

-- breed colour table to break 2x 1:M 
CREATE TABLE breedcolours (
breed_id INTEGER,
colour_id INTEGER,
PRIMARY KEY (breed_id, colour_id),
FOREIGN KEY (breed_id) REFERENCES breeds(id),
FOREIGN KEY (colour_id) REFERENCES colours(id)
);

-- exhibit genders table 
-- this could be coded in the application but for simplicity included in the database
-- as some classes need this information and to make changes to a exhibit animal easier
-- it is currently for rabbits, change to cavy aka Guinea pig 
CREATE TABLE exhibit_genders(
id INTEGER,
gender INTEGER,
gender_class VARCHAR(10),
gender_text VARCHAR(14),
PRIMARY KEY (id)
);

-- human genders table
-- this could be coded in the application but for simplicity included in the database
-- as some classes need this information
CREATE TABLE human_genders(
id INTEGER,
exhibitor INTEGER,
gender INTEGER,
gender_class VARCHAR(10),
gender_text VARCHAR(14),
PRIMARY KEY (id)
);

-- exhibit ages table 
-- this could be coded in the application but for simplicity included in the database
-- as some classes need this information and to make changes to exhibit animal easier
-- it is currently for rabbits, change to cavy aka Guinea pig CREATE TABLE exhibit_ages(
CREATE TABLE exhibit_ages(
id INTEGER,
age INTEGER,
age_text VARCHAR(15),
PRIMARY KEY (id)
);

-- human ages table
-- this could be coded in the application but for simplicity included in the database
-- as some classes need this information
CREATE TABLE human_ages(
id INTEGER,
age INTEGER,
age_text VARCHAR(15),
PRIMARY KEY (id)
);
-- Show Sections table 
-- this could be coded in the application but for simplicity included in the database
-- as some classes need this information
CREATE TABLE showsections(
id INTEGER UNIQUE,
section INTEGER,
section_text VARCHAR(5),
PRIMARY KEY (id)
);

CREATE TABLE judges(
id INTEGER,
name VARCHAR(40),
sections INTEGER,
PRIMARY KEY (id),
FOREIGN KEY(sections) REFERENCES showsections(id)
);

CREATE TABLE exhibitors (
id SERIAL, 
name VARCHAR(20),   
address VARCHAR(200),
phone VARCHAR(16),
email VARCHAR(100),
booked_in BOOL,
booked_out BOOL,
paid_fees BOOL,
paid_prizes BOOL, 
entry_fees REAL,
prize_money REAL,
club_member INTEGER,
age_group INTEGER,
gender INTEGER,
PRIMARY KEY (id)
);

CREATE TABLE exhibits (
pen_no INTEGER,
ring_number VARCHAR(15) UNIQUE,  
breed_class INTEGER,
gender INTEGER,
age_group INTEGER,
breed_by_exhibitor INTEGER,
exhibitor_id INTEGER, 
PRIMARY KEY (pen_no),
FOREIGN KEY (exhibitor_id) REFERENCES Exhibitors(id)
);

-- show classes  breed_class & members = 3 suggestions 
-- breed_class 1
-- members     2
-- breeders    4
-- upsidedown  8
-- section     0=Pets 1=Fancy 2=Lop, 4=Fur, 8=Rex 15=all but pet section challenges
-- Show Classes
CREATE TABLE showclasses ( 
class_no INTEGER, 
name VARCHAR(40),
breed INTEGER,
breed_class BOOL, 
section INTEGER, 
members_only BOOL, 
breeders_only BOOL, 
upsidedown BOOL,
exhibit_age INTEGER, 
exhibit_gender INTEGER, 
exhibitor_age INTEGER, 
exhibitor_gender INTEGER, 
results INTEGER ARRAY[7], 
PRIMARY KEY (class_no), 
FOREIGN KEY (breed) REFERENCES breeds(id),
--FOREIGN KEY (colour) REFERENCES classcolours(id),
FOREIGN KEY (section) REFERENCES ShowSections(id),
FOREIGN KEY (exhibit_age) REFERENCES exhibit_ages(id),
FOREIGN KEY (exhibit_gender) REFERENCES exhibit_genders(id),
FOREIGN KEY (exhibitor_age) REFERENCES human_ages(id),
FOREIGN KEY (exhibitor_gender) REFERENCES human_genders(id)
);
CREATE TABLE classcolours (
class_no int,
colour_id int,
PRIMARY KEY (class_no, colour_id),
FOREIGN KEY (class_no) REFERENCES showclasses (class_no),
FOREIGN KEY (colour_id) REFERENCES colours(id)
);

CREATE TABLE entries(
class_no INTEGER,
pen_no INTEGER,
UNIQUE (class_no, pen_no),
PRIMARY KEY (class_no, pen_no), 
FOREIGN KEY (class_no) REFERENCES showclasses (class_no),
FOREIGN KEY (pen_no) REFERENCES exhibits(pen_no)
);

