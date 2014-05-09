--
--Copyright (C) 2014 paul
--
--This program is free software: you can redistribute it and/or modify
--it under the terms of the GNU General Public License as published by
--the Free Software Foundation, either version 3 of the License, or
--(at your option) any later version.
--
--This program is distributed in the hope that it will be useful,
--but WITHOUT ANY WARRANTY; without even the implied warranty of
--MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
--GNU General Public License for more details.
--
--You should have received a copy of the GNU General Public License
--along with this program.  If not, see <http://www.gnu.org/licenses/>.
--

DROP DATABASE rsm;


CREATE DATABASE rsm 
  WITH OWNER = postgres
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'en_GB.UTF-8'
       LC_CTYPE = 'en_GB.UTF-8'
       CONNECTION LIMIT = -1;


\c rsm

--human genders table
--this could be coded in the application but for simplicity included in 
--the database as some classes need this information
CREATE TABLE human_genders(
id INTEGER,
gender INTEGER,
gender_class VARCHAR(10),
gender_text VARCHAR(14),
PRIMARY KEY (id)
);

--exhibit ages table 
--this could be coded in the application but for simplicity included in the
--database as some classes need this information and to make changes to exhibit
--animal easier it is currently for rabbits, change to cavy aka Guinea pig 
CREATE TABLE exhibit_ages(
id INTEGER,
age INTEGER,
age_text VARCHAR(15),
abbrev VARCHAR(6),
PRIMARY KEY (id)
);

--human ages table
--this could be coded in the application but for simplicity included in the
--database as some classes need this information
CREATE TABLE human_ages(
id INTEGER,
age INTEGER,
age_text VARCHAR(15),
abbrev VARCHAR(6),
PRIMARY KEY (id)
);

--colours table
CREATE TABLE colours (
id INTEGER,
abbrev VARCHAR(4) UNIQUE,
colour VARCHAR(35)UNIQUE,
PRIMARY KEY (id)
);

--Show Sections table 
--this could be coded in the application but for simplicity included in the
--database as some classes need this information
CREATE TABLE showsections(
id INTEGER UNIQUE,
section INTEGER UNIQUE,
section_text VARCHAR(5),
PRIMARY KEY (id)
);

--Breeds table 
CREATE TABLE breeds(
id INTEGER,
youngsters INTEGER,
top_pen_req BOOL,
section INTEGER,
breed VARCHAR(30) UNIQUE,
PRIMARY KEY (id),
FOREIGN KEY (youngsters) references exhibit_ages(id),
FOREIGN KEY (section) references showsections(section)
);

--breed colour table to break 2x 1:M 
CREATE TABLE breedcolours (
breed_id INTEGER,
colour_id INTEGER,
available BOOL,
selected BOOL,
class_no INTEGER,
PRIMARY KEY (breed_id, colour_id),
FOREIGN KEY (breed_id) REFERENCES breeds(id),
FOREIGN KEY (colour_id) REFERENCES colours(id)
);

--exhibit genders table 
--this could be coded in the application but for simplicity included in the
--database as some classes need this information and to make changes to a
--exhibit animal easier it is currently for rabbits,
--change to cavy aka Guinea pig 
CREATE TABLE exhibit_genders(
id INTEGER,
gender INTEGER,
gender_class VARCHAR(10),
gender_text VARCHAR(14),
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

--section     0=Pets 1=Fancy 2=Lop, 4=Fur, 8=Rex 15=all but pet section challenges
--Show Classes
CREATE TABLE showclasses ( 
class_no INTEGER, 
name VARCHAR(60),
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
results1 INTEGER, 
results2 INTEGER, 
results3 INTEGER, 
results4 INTEGER, 
results5 INTEGER, 
results6 INTEGER,
results7 INTEGER,  
PRIMARY KEY (class_no), 
FOREIGN KEY (breed) REFERENCES breeds(id),
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

CREATE TABLE availablecolours(
colour INTEGER,
PRIMARY KEY (colour),        
FOREIGN KEY (colour) REFERENCES colours(id)
);

INSERT INTO exhibit_genders (id,gender,gender_class,gender_text) VALUES (1,3,'Open','n/a'); 		--  0
INSERT INTO exhibit_genders (id,gender,gender_class,gender_text) VALUES (2,1,'Bucks','Buck'); 		--  1
INSERT INTO exhibit_genders (id,gender,gender_class,gender_text) VALUES (3,2,'Does','Doe'); 		--  2

INSERT INTO human_genders (id,gender,gender_class,gender_text) VALUES (1,3,'Open','Open'); 		--  0
INSERT INTO human_genders (id,gender,gender_class,gender_text) VALUES (2,1,'Gents','Gentleman');	--  1
INSERT INTO human_genders (id,gender,gender_class,gender_text) VALUES (3,2,'Ladies','Lady');		--  2
INSERT INTO human_genders (id,gender,gender_class,gender_text) VALUES (4,0,'Group/Stud');		--  3

INSERT INTO human_ages (id,age,age_text,abbrev) VALUES (1,3,'Open','OPN');			--  0
INSERT INTO human_ages (id,age,age_text,abbrev) VALUES (2,1,'Juvenile','JUV');			--  1
INSERT INTO human_ages (id,age,age_text,abbrev) VALUES (3,2,'Adult','ADT');			--  2

INSERT INTO exhibit_ages (id,age,age_text,abbrev) VALUES (1,7,'Any Age','AA');			--  0
INSERT INTO exhibit_ages (id,age,age_text,abbrev) VALUES (2,1,'under 14 weeks','u/14w');	--  1
INSERT INTO exhibit_ages (id,age,age_text,abbrev) VALUES (3,3,'under 4 months','u/4m');		--  2
INSERT INTO exhibit_ages (id,age,age_text,abbrev) VALUES (4,3,'under 5 months','u/5m');		--  3
INSERT INTO exhibit_ages (id,age,age_text,abbrev) VALUES (5,4,'Adult','Adt');			--  4

INSERT INTO ShowSections (id, section, section_text) VALUES (1,1,'Fancy');			--  0
INSERT INTO ShowSections (id, section, section_text) VALUES (2,2,'Lop');			--  1
INSERT INTO ShowSections (id, section, section_text) VALUES (3,4,'Fur');			--  2
INSERT INTO ShowSections (id, section, section_text) VALUES (4,8,'Rex');			--  3
INSERT INTO ShowSections (id, section, section_text) VALUES (5,15,'Open');			--  4
INSERT INTO ShowSections (id, section, section_text) VALUES (6,0,'Pet');			--  5

INSERT INTO breeds (id,youngsters,top_pen_req,section,breed) VALUES (1,4,false,15,'Any Variety');		--  0
INSERT INTO breeds (id,youngsters,top_pen_req,section,breed) VALUES (2,4,false,15,'Any Other Variety');		--  1
INSERT INTO breeds (id,youngsters,top_pen_req,section,breed) VALUES (3,4,true,1,'Angora');			--  2
INSERT INTO breeds (id,youngsters,top_pen_req,section,breed) VALUES (4,4,false,1,'Black Hare');			--  3
INSERT INTO breeds (id,youngsters,top_pen_req,section,breed) VALUES (5,3,false,1,'Dutch');			--  4
INSERT INTO breeds (id,youngsters,top_pen_req,section,breed) VALUES (6,4,false,1,'English');			--  5
INSERT INTO breeds (id,youngsters,top_pen_req,section,breed) VALUES (7,4,false,1,'Flemish Giant');		--  6
INSERT INTO breeds (id,youngsters,top_pen_req,section,breed) VALUES (8,4,false,1,'Giant Papillon');		--  7
INSERT INTO breeds (id,youngsters,top_pen_req,section,breed) VALUES (9,4,false,1,'Hare Belgian');		--  8
INSERT INTO breeds (id,youngsters,top_pen_req,section,breed) VALUES (10,4,false,1,'Hare Tan');			--  9
INSERT INTO breeds (id,youngsters,top_pen_req,section,breed) VALUES (11,4,false,1,'Harlequin');			-- 10
INSERT INTO breeds (id,youngsters,top_pen_req,section,breed) VALUES (12,4,false,1,'Himalayan');			-- 11
INSERT INTO breeds (id,youngsters,top_pen_req,section,breed) VALUES (13,4,false,1,'Lionhead');			-- 12
INSERT INTO breeds (id,youngsters,top_pen_req,section,breed) VALUES (14,4,false,1,'Netherland Dwarf');		-- 13
INSERT INTO breeds (id,youngsters,top_pen_req,section,breed) VALUES (15,4,false,1,'Polish');			-- 14
INSERT INTO breeds (id,youngsters,top_pen_req,section,breed) VALUES (16,4,false,1,'Rhinelander');		-- 15
INSERT INTO breeds (id,youngsters,top_pen_req,section,breed) VALUES (17,4,false,1,'Silver');			-- 16
INSERT INTO breeds (id,youngsters,top_pen_req,section,breed) VALUES (18,4,false,1,'Tan');			-- 17
INSERT INTO breeds (id,youngsters,top_pen_req,section,breed) VALUES (19,4,false,1,'Thrianta');			-- 18
INSERT INTO breeds (id,youngsters,top_pen_req,section,breed) VALUES (20,4,false,8,'Rex');			-- 19
INSERT INTO breeds (id,youngsters,top_pen_req,section,breed) VALUES (21,4,false,4,'Alaska');			-- 20
INSERT INTO breeds (id,youngsters,top_pen_req,section,breed) VALUES (22,4,false,4,'Argente Bleu');		-- 21
INSERT INTO breeds (id,youngsters,top_pen_req,section,breed) VALUES (23,4,false,4,'Argente Brun');		-- 22
INSERT INTO breeds (id,youngsters,top_pen_req,section,breed) VALUES (24,4,false,4,'Argente Crème');		-- 23
INSERT INTO breeds (id,youngsters,top_pen_req,section,breed) VALUES (25,4,false,4,'Argente de Champagne');	-- 24
INSERT INTO breeds (id,youngsters,top_pen_req,section,breed) VALUES (26,4,false,4,'Argente Noir');		-- 25
INSERT INTO breeds (id,youngsters,top_pen_req,section,breed) VALUES (27,4,false,4,'Beige');			-- 26
INSERT INTO breeds (id,youngsters,top_pen_req,section,breed) VALUES (28,4,false,4,'Beveren');			-- 27
INSERT INTO breeds (id,youngsters,top_pen_req,section,breed) VALUES (29,4,false,4,'Blanc de Bouscat');		-- 28
INSERT INTO breeds (id,youngsters,top_pen_req,section,breed) VALUES (30,4,false,4,'Blanc de Hotot');		-- 29
INSERT INTO breeds (id,youngsters,top_pen_req,section,breed) VALUES (31,4,false,4,'Blanc de Termonde');		-- 30
INSERT INTO breeds (id,youngsters,top_pen_req,section,breed) VALUES (32,4,false,4,'British Giant');		-- 31
INSERT INTO breeds (id,youngsters,top_pen_req,section,breed) VALUES (33,4,false,4,'Californian');		-- 32
INSERT INTO breeds (id,youngsters,top_pen_req,section,breed) VALUES (34,4,false,4,'Chinchilla');		-- 33
INSERT INTO breeds (id,youngsters,top_pen_req,section,breed) VALUES (35,4,false,4,'Chinchilla Giganta');	-- 34
INSERT INTO breeds (id,youngsters,top_pen_req,section,breed) VALUES (36,4,false,4,'Continental Giant coloured');-- 35
INSERT INTO breeds (id,youngsters,top_pen_req,section,breed) VALUES (37,4,false,4,'Continental Giant white');	-- 36
INSERT INTO breeds (id,youngsters,top_pen_req,section,breed) VALUES (38,4,false,4,'Deilenaar');			-- 37
INSERT INTO breeds (id,youngsters,top_pen_req,section,breed) VALUES (39,4,false,4,'Fox - Silver');		-- 38
INSERT INTO breeds (id,youngsters,top_pen_req,section,breed) VALUES (40,4,false,4,'Golden Glavcot');		-- 39
INSERT INTO breeds (id,youngsters,top_pen_req,section,breed) VALUES (41,4,false,4,'Havana');			-- 40
INSERT INTO breeds (id,youngsters,top_pen_req,section,breed) VALUES (42,4,false,4,'Hulstlander');		-- 41
INSERT INTO breeds (id,youngsters,top_pen_req,section,breed) VALUES (43,4,false,4,'Lilac');			-- 42
INSERT INTO breeds (id,youngsters,top_pen_req,section,breed) VALUES (44,4,false,4,'New Zealand White');		-- 43
INSERT INTO breeds (id,youngsters,top_pen_req,section,breed) VALUES (45,4,false,4,'New Zealand Black');		-- 44
INSERT INTO breeds (id,youngsters,top_pen_req,section,breed) VALUES (46,4,false,4,'New Zealand Blue');		-- 45
INSERT INTO breeds (id,youngsters,top_pen_req,section,breed) VALUES (47,4,false,4,'New Zealand Red');		-- 46
INSERT INTO breeds (id,youngsters,top_pen_req,section,breed) VALUES (48,4,false,4,'Perlfee');			-- 47
INSERT INTO breeds (id,youngsters,top_pen_req,section,breed) VALUES (49,4,false,4,'Pointed Beveren');		-- 48
INSERT INTO breeds (id,youngsters,top_pen_req,section,breed) VALUES (50,4,false,4,'Siamese Sable - Marten Sable');-- 49
INSERT INTO breeds (id,youngsters,top_pen_req,section,breed) VALUES (51,4,false,4,'Sallander');			-- 50
INSERT INTO breeds (id,youngsters,top_pen_req,section,breed) VALUES (52,4,false,4,'Satin');			-- 51
INSERT INTO breeds (id,youngsters,top_pen_req,section,breed) VALUES (53,4,false,4,'Siberian');			-- 52
INSERT INTO breeds (id,youngsters,top_pen_req,section,breed) VALUES (54,4,false,4,'Smoke Pearl');		-- 53
INSERT INTO breeds (id,youngsters,top_pen_req,section,breed) VALUES (55,4,false,4,'Squirrel');			-- 54
INSERT INTO breeds (id,youngsters,top_pen_req,section,breed) VALUES (56,4,false,4,'Sussex');			-- 55
INSERT INTO breeds (id,youngsters,top_pen_req,section,breed) VALUES (57,4,false,4,'Swiss Fox');			-- 56
INSERT INTO breeds (id,youngsters,top_pen_req,section,breed) VALUES (58,4,false,4,'Thuringer');			-- 57
INSERT INTO breeds (id,youngsters,top_pen_req,section,breed) VALUES (59,4,false,4,'Vienna Coloured');		-- 58
INSERT INTO breeds (id,youngsters,top_pen_req,section,breed) VALUES (60,4,false,4,'Vienna White');		-- 59
INSERT INTO breeds (id,youngsters,top_pen_req,section,breed) VALUES (61,4,false,4,'Wheaten');			-- 60
INSERT INTO breeds (id,youngsters,top_pen_req,section,breed) VALUES (62,4,false,4,'Wheaten Lynx');		-- 61
INSERT INTO breeds (id,youngsters,top_pen_req,section,breed) VALUES (63,4,false,4,'Fauve de Bourgogne');	-- 62
INSERT INTO breeds (id,youngsters,top_pen_req,section,breed) VALUES (64,4,false,4,'Argente St Hubert');		-- 63
INSERT INTO breeds (id,youngsters,top_pen_req,section,breed) VALUES (65,4,false,4,'Miniature Satin');		-- 64
INSERT INTO breeds (id,youngsters,top_pen_req,section,breed) VALUES (66,4,false,8,'Mini Rex');			-- 65
INSERT INTO breeds (id,youngsters,top_pen_req,section,breed) VALUES (67,4,false,2,'Cashmere Lop');		-- 66
INSERT INTO breeds (id,youngsters,top_pen_req,section,breed) VALUES (68,4,false,2,'Miniature Cashmere Lop');	-- 67
INSERT INTO breeds (id,youngsters,top_pen_req,section,breed) VALUES (69,4,false,2,'Dwarf Lop');			-- 68
INSERT INTO breeds (id,youngsters,top_pen_req,section,breed) VALUES (70,4,false,2,'English Lop');		-- 69
INSERT INTO breeds (id,youngsters,top_pen_req,section,breed) VALUES (71,4,false,2,'French Lop');		-- 70
INSERT INTO breeds (id,youngsters,top_pen_req,section,breed) VALUES (72,4,false,2,'German Lop');		-- 71
INSERT INTO breeds (id,youngsters,top_pen_req,section,breed) VALUES (73,4,false,2,'Meissner Lop');		-- 72
INSERT INTO breeds (id,youngsters,top_pen_req,section,breed) VALUES (74,4,false,2,'Miniature Lop');		-- 73
INSERT INTO breeds (id,youngsters,top_pen_req,section,breed) VALUES (75,4,false,2,'Miniature Lion Lop');	-- 74

INSERT INTO colours (id,colour,abbrev) VALUES (1,'Any Colour','AC');			--  0
INSERT INTO colours (id,colour,abbrev) VALUES (2,'Any Other Colour','AOC');		--  1 
INSERT INTO colours (id,colour,abbrev) VALUES (3,'Black','Blk');			--  2
INSERT INTO colours (id,colour,abbrev) VALUES (4,'Blue','Blu');				--  3
INSERT INTO colours (id,colour,abbrev) VALUES (5,'Blue Eyed White','BEW');		--  4
INSERT INTO colours (id,colour,abbrev) VALUES (6,'Chocolate Brown','ChB');		--  5
INSERT INTO colours (id,colour,abbrev) VALUES (7,'Havana','Hav');			--  6
INSERT INTO colours (id,colour,abbrev) VALUES (8,'Ermine','Erm');			--  7
INSERT INTO colours (id,colour,abbrev) VALUES (9,'Ivory','Ivy');			--  8
INSERT INTO colours (id,colour,abbrev) VALUES (10,'Lilac','Llc');			--  9
INSERT INTO colours (id,colour,abbrev) VALUES (11,'Red Eyed White','REW');		-- 10
INSERT INTO colours (id,colour,abbrev) VALUES (12,'Smoke','Smk');			-- 11
INSERT INTO colours (id,colour,abbrev) VALUES (13,'Beige-Isabella','BIs');		-- 12
INSERT INTO colours (id,colour,abbrev) VALUES (14,'Blue Cream','BlC');			-- 13
INSERT INTO colours (id,colour,abbrev) VALUES (15,'Bluepoint','BlP');			-- 14
INSERT INTO colours (id,colour,abbrev) VALUES (16,'Bronze','Brz');			-- 15
INSERT INTO colours (id,colour,abbrev) VALUES (17,'Chocolate Tortoiseshell','ChT');	-- 16
INSERT INTO colours (id,colour,abbrev) VALUES (18,'Cream','Crm');			-- 17
INSERT INTO colours (id,colour,abbrev) VALUES (19,'Gold','Gld');			-- 18
INSERT INTO colours (id,colour,abbrev) VALUES (20,'Iron Grey','IGr');			-- 19
INSERT INTO colours (id,colour,abbrev) VALUES (21,'Sealpoint','Spt');			-- 20
INSERT INTO colours (id,colour,abbrev) VALUES (22,'Siamese Sable Dark','SSsD');		-- 21
INSERT INTO colours (id,colour,abbrev) VALUES (23,'Siamese Sable Medium','SSsM');	-- 22
INSERT INTO colours (id,colour,abbrev) VALUES (24,'Siamese Smoke Dark','SSmD');		-- 23
INSERT INTO colours (id,colour,abbrev) VALUES (25,'Siamese Smoke Medium','SSmM');	-- 24
INSERT INTO colours (id,colour,abbrev) VALUES (26,'Sooty-Fawn','SFwn');			-- 25
INSERT INTO colours (id,colour,abbrev) VALUES (27,'Tortoiseshell','Tort');		-- 26
INSERT INTO colours (id,colour,abbrev) VALUES (28,'Agouti','Agti');			-- 27
INSERT INTO colours (id,colour,abbrev) VALUES (29,'Belgium Hare','BelH');		-- 28
INSERT INTO colours (id,colour,abbrev) VALUES (30,'Blue Grey','BuGr');			-- 29
INSERT INTO colours (id,colour,abbrev) VALUES (31,'Brown Grey','BrGr');			-- 30
INSERT INTO colours (id,colour,abbrev) VALUES (32,'Castor','Cast');			-- 31
INSERT INTO colours (id,colour,abbrev) VALUES (33,'Chinchilla','Chin');			-- 32
INSERT INTO colours (id,colour,abbrev) VALUES (34,'Cinnamon','Cinn');			-- 33
INSERT INTO colours (id,colour,abbrev) VALUES (35,'Flemish','Flem');			-- 34
INSERT INTO colours (id,colour,abbrev) VALUES (36,'Grey','Grey');			-- 35
INSERT INTO colours (id,colour,abbrev) VALUES (37,'Lynx-Wheaten Lynx','LyWL');		-- 36
INSERT INTO colours (id,colour,abbrev) VALUES (38,'Opal','Opal');			-- 37
INSERT INTO colours (id,colour,abbrev) VALUES (39,'Perlfee','PerF');			-- 38
INSERT INTO colours (id,colour,abbrev) VALUES (40,'Red Agouti','RedA');			-- 39
INSERT INTO colours (id,colour,abbrev) VALUES (41,'Sandy','Sand');			-- 40
INSERT INTO colours (id,colour,abbrev) VALUES (42,'Squirrel','Squl');			-- 41
INSERT INTO colours (id,colour,abbrev) VALUES (43,'Broken','Brkn');			-- 42
INSERT INTO colours (id,colour,abbrev) VALUES (44,'Butterfly','ButF');			-- 43
INSERT INTO colours (id,colour,abbrev) VALUES (45,'Dalmation','Dalm');			-- 44
INSERT INTO colours (id,colour,abbrev) VALUES (46,'Dutch','Dtch');			-- 45
INSERT INTO colours (id,colour,abbrev) VALUES (47,'English','Engl');			-- 46
INSERT INTO colours (id,colour,abbrev) VALUES (48,'Hotot','Hott');			-- 47
INSERT INTO colours (id,colour,abbrev) VALUES (49,'Papillon','Papl');			-- 48
INSERT INTO colours (id,colour,abbrev) VALUES (50,'Rhinelander','Rhin');		-- 49
INSERT INTO colours (id,colour,abbrev) VALUES (51,'Fawn','Fawn');			-- 50
INSERT INTO colours (id,colour,abbrev) VALUES (52,'Golden Glavcot','GdGt');		-- 51
INSERT INTO colours (id,colour,abbrev) VALUES (53,'Brown','Brwn');			-- 52
INSERT INTO colours (id,colour,abbrev) VALUES (54,'Himalayan Black','HBlk');		-- 53
INSERT INTO colours (id,colour,abbrev) VALUES (55,'Himalayan Blue','HBlu');		-- 54
INSERT INTO colours (id,colour,abbrev) VALUES (56,'Himalayan Chocolate','HCho');	-- 55
INSERT INTO colours (id,colour,abbrev) VALUES (57,'Himalayan Lilac','HLil');		-- 56
INSERT INTO colours (id,colour,abbrev) VALUES (58,'Light Steel','LStl');		-- 57
INSERT INTO colours (id,colour,abbrev) VALUES (59,'Magpie Black','MBlk');		-- 58
INSERT INTO colours (id,colour,abbrev) VALUES (60,'Magpie Brown','MBrn');		-- 59
INSERT INTO colours (id,colour,abbrev) VALUES (61,'Magpie Blue','MBlu');		-- 60
INSERT INTO colours (id,colour,abbrev) VALUES (62,'Magpie Lilac','MLil');		-- 61
INSERT INTO colours (id,colour,abbrev) VALUES (63,'Orange','Oran');			-- 62
INSERT INTO colours (id,colour,abbrev) VALUES (64,'Red','Red');				-- 63
INSERT INTO colours (id,colour,abbrev) VALUES (65,'Steel','Stel');			-- 64
INSERT INTO colours (id,colour,abbrev) VALUES (66,'Thrianta','Thri');			-- 65
INSERT INTO colours (id,colour,abbrev) VALUES (67,'Wheaten','Whet');			-- 66
INSERT INTO colours (id,colour,abbrev) VALUES (68,'Black Fox','BlFx');			-- 67
INSERT INTO colours (id,colour,abbrev) VALUES (69,'Black Otter','BlOt');		-- 68
INSERT INTO colours (id,colour,abbrev) VALUES (70,'Black Tan','BlTn');			-- 69
INSERT INTO colours (id,colour,abbrev) VALUES (71,'Blue Fox','BuFx');			-- 70
INSERT INTO colours (id,colour,abbrev) VALUES (72,'Blue Otter','BuOt');			-- 71
INSERT INTO colours (id,colour,abbrev) VALUES (73,'Blue Tan','BuTn');			-- 72
INSERT INTO colours (id,colour,abbrev) VALUES (74,'Chocolate Fox','ChoF');		-- 73
INSERT INTO colours (id,colour,abbrev) VALUES (75,'Chocolate Otter','ChOt');		-- 74
INSERT INTO colours (id,colour,abbrev) VALUES (76,'Chocolate Tan','ChTn');		-- 75
INSERT INTO colours (id,colour,abbrev) VALUES (77,'Lilac Fox','LFox');			-- 76
INSERT INTO colours (id,colour,abbrev) VALUES (78,'Lilac Otter','LiOt');		-- 77
INSERT INTO colours (id,colour,abbrev) VALUES (79,'Lilac Tan','LiTn');			-- 78
INSERT INTO colours (id,colour,abbrev) VALUES (80,'Argente Bleu','ArBu');		-- 79
INSERT INTO colours (id,colour,abbrev) VALUES (81,'Argente Brun','ArBr');		-- 80
INSERT INTO colours (id,colour,abbrev) VALUES (82,'Argente Crème','ArCr');		-- 81
INSERT INTO colours (id,colour,abbrev) VALUES (83,'Argente de Champagne','ArCh');	-- 82
INSERT INTO colours (id,colour,abbrev) VALUES (84,'Argente Noir','ArNr');		-- 83
INSERT INTO colours (id,colour,abbrev) VALUES (85,'Meissener','Meis');			-- 84
INSERT INTO colours (id,colour,abbrev) VALUES (86,'Silver Blue','SiBu');		-- 85
INSERT INTO colours (id,colour,abbrev) VALUES (87,'Silver Brown','SiBr');		-- 86
INSERT INTO colours (id,colour,abbrev) VALUES (88,'Silver Fawn','SiFn');		-- 87
INSERT INTO colours (id,colour,abbrev) VALUES (89,'Silver Grey','SiGy');		-- 88
INSERT INTO colours (id,colour,abbrev) VALUES (90,'Sable Dark','SabD');			-- 89
INSERT INTO colours (id,colour,abbrev) VALUES (91,'Sable Light','Sabl');		-- 90
INSERT INTO colours (id,colour,abbrev) VALUES (92,'Marten Light','MarL');		-- 91
INSERT INTO colours (id,colour,abbrev) VALUES (93,'Marten Medium','MarM');		-- 92
INSERT INTO colours (id,colour,abbrev) VALUES (94,'Marten Dark','MarD');		-- 93
INSERT INTO colours (id,colour,abbrev) VALUES (95,'Chocolate','Choc');			-- 94
INSERT INTO colours (id,colour,abbrev) VALUES (96,'Yellow','Yell');			-- 95
INSERT INTO colours (id,colour,abbrev) VALUES (97,'Pale Grey','PaGy');			-- 96
INSERT INTO colours (id,colour,abbrev) VALUES (98,'Steel Grey','StGy');			-- 97
INSERT INTO colours (id,colour,abbrev) VALUES (99,'Tri-colour','TriC');			-- 98
INSERT INTO colours (id,colour,abbrev) VALUES (100,'Smoke Pearl','SmkP');		-- 99
INSERT INTO colours (id,colour,abbrev) VALUES (101,'Lynx','Lynx');			--100
INSERT INTO colours (id,colour,abbrev) VALUES (102,'Tan','Tan');			--101
INSERT INTO colours (id,colour,abbrev) VALUES (103,'Otter Black','OBlk');		--102
INSERT INTO colours (id,colour,abbrev) VALUES (104,'Otter Blue','OBlu');		--103
INSERT INTO colours (id,colour,abbrev) VALUES (105,'Otter Chocolate','OChc');		--104
INSERT INTO colours (id,colour,abbrev) VALUES (106,'Otter Lilac','OLil');		--105
INSERT INTO colours (id,colour,abbrev) VALUES (107,'Fox Black','FBlk');			--106
INSERT INTO colours (id,colour,abbrev) VALUES (108,'Fox Blue','FBlu');			--107
INSERT INTO colours (id,colour,abbrev) VALUES (109,'Fox Chocolate','FCho');		--108
INSERT INTO colours (id,colour,abbrev) VALUES (110,'Fox Lilac','FLil');			--109
INSERT INTO colours (id,colour,abbrev) VALUES (111,'Siamese Sable Light','SSaL');	--110
INSERT INTO colours (id,colour,abbrev) VALUES (112,'Siamese Smoke Pearl','SSPe');	--111
INSERT INTO colours (id,colour,abbrev) VALUES (113,'Marten Sable Light','MSaL');	--112
INSERT INTO colours (id,colour,abbrev) VALUES (114,'Marten Sable Medium','MSaM');	--113
INSERT INTO colours (id,colour,abbrev) VALUES (115,'Marten Sable Dark','MSaD');		--114
INSERT INTO colours (id,colour,abbrev) VALUES (116,'Marten Smoke Pearl','MSmP');	--115
INSERT INTO colours (id,colour,abbrev) VALUES (117,'Sable Siamese','SSia');		--116
INSERT INTO colours (id,colour,abbrev) VALUES (118,'Marten Sable','MSab');		--117
INSERT INTO colours (id,colour,abbrev) VALUES (119,'Blue and Tan','BluT');		--118
INSERT INTO colours (id,colour,abbrev) VALUES (120,'Black and Tan','BlkT');		--119
INSERT INTO colours (id,colour,abbrev) VALUES (121,'Chocolate and Tan','ChoT');		--120
INSERT INTO colours (id,colour,abbrev) VALUES (122,'Lilac and Tan','LilT');		--121
INSERT INTO colours (id,colour,abbrev) VALUES (123,'White','Wht');			--122
INSERT INTO colours (id,colour,abbrev) VALUES (124,'Dark Steel Grey','DSGy');		--123
INSERT INTO colours (id,colour,abbrev) VALUES (125,'Normal','Norm');			--124
INSERT INTO colours (id,colour,abbrev) VALUES (126,'Dark Steel','DStl');		--125
INSERT INTO colours (id,colour,abbrev) VALUES (127,'Nutria','Nutr');			--126
INSERT INTO colours (id,colour,abbrev) VALUES (128,'Seal Siamese','SeaS');		--127
INSERT INTO colours (id,colour,abbrev) VALUES (129,'Fox','Fox');			--128
INSERT INTO colours (id,colour,abbrev) VALUES (130,'Sable Marten','SMar');		--129
INSERT INTO colours (id,colour,abbrev) VALUES (131,'Otter','Ottr');			--130
INSERT INTO colours (id,colour,abbrev) VALUES (132,'Harlequin','Harl');			--131
INSERT INTO colours (id,colour,abbrev) VALUES (133,'Himalayan','Himl');			--132
INSERT INTO colours (id,colour,abbrev) VALUES (134,'Silver Seal','SiSe');		--133
INSERT INTO colours (id,colour,abbrev) VALUES (135,'Satin Rex','SaRe');			--134
INSERT INTO colours (id,colour,abbrev) VALUES (136,'Astrex','Astx');			--135
INSERT INTO colours (id,colour,abbrev) VALUES (137,'Opossum','Opos');			--136

INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (1,1,true,false,0);   	--  0
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (2,1,true,false,0);   	--  1
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (3,1,true,false,0);   	--  2
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (3,2,true,false,0);   	--  3
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (3,4,true,false,0);   	--  4
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (3,12,true,false,0);  	--  5
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (3,14,true,false,0);  	--  6
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (3,18,true,false,0);  	--  7
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (3,19,true,false,0);  	--  8
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (3,30,true,false,0);  	--  9
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (3,31,true,false,0);  	-- 10
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (3,33,true,false,0);  	-- 11
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (3,34,true,false,0);  	-- 12
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (3,90,true,false,0);  	-- 13
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (3,91,true,false,0);  	-- 14
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (3,92,true,false,0);  	-- 15
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (3,94,true,false,0);  	-- 16
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (3,98,true,false,0);  	-- 17
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (3,95,true,false,0);  	-- 18
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (4,1,true,false,0);   	-- 19
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (5,1,true,false,0);   	-- 20
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (5,2,true,false,0);   	-- 21
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (5,3,true,false,0);   	-- 22
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (5,4,true,false,0);   	-- 23
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (5,27,true,false,0);  	-- 24
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (5,31,true,false,0);  	-- 25
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (5,95,true,false,0);  	-- 26
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (5,96,true,false,0);  	-- 27
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (5,97,true,false,0);  	-- 28
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (5,98,true,false,0);  	-- 29
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (5,99,true,false,0);  	-- 30
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (6,1,true,false,0);   	-- 31
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (6,2,true,false,0);   	-- 32
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (6,3,true,false,0);   	-- 33
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (6,4,true,false,0);   	-- 34
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (6,27,true,false,0);  	-- 35
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (6,36,true,false,0);  	-- 36
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (6,95,true,false,0);  	-- 37
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (6,99,true,false,0);  	-- 38
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (7,1,true,false,0);   	-- 39
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (8,1,true,false,0);   	-- 40
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (9,1,true,false,0);   	-- 41
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (10,1,true,false,0);  	-- 42
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (11,1,true,false,0);  	-- 43
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (11,2,true,false,0);  	-- 44
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (11,3,true,false,0);  	-- 45
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (11,4,true,false,0);  	-- 46 
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (11,10,true,false,0); 	-- 47
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (11,53,true,false,0);	-- 48
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (11,59,true,false,0); 	-- 49
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (11,60,true,false,0); 	-- 50
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (11,61,true,false,0); 	-- 51
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (11,62,true,false,0); 	-- 52
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (12,1,true,false,0);  	-- 53
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (12,2,true,false,0);  	-- 54
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (12,4,true,false,0);  	-- 55
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (12,10,true,false,0); 	-- 56
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (12,95,true,false,0); 	-- 57
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (13,1,true,false,0); 	-- 58
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (14,1,true,false,0); 	-- 59
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (14,2,true,false,0); 	-- 60
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (14,3,true,false,0); 	-- 61
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (14,4,true,false,0); 	-- 62
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (14,5,true,false,0); 	-- 63
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (14,10,true,false,0); 	-- 64
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (14,11,true,false,0); 	-- 65
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (14,21,true,false,0); 	-- 66
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (14,23,true,false,0); 	-- 67
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (14,24,true,false,0); 	-- 68
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (14,25,true,false,0); 	-- 69
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (14,27,true,false,0); 	-- 70
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (14,28,true,false,0); 	-- 71
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (14,33,true,false,0); 	-- 72
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (14,38,true,false,0); 	-- 73
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (14,42,true,false,0); 	-- 74
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (14,51,true,false,0); 	-- 75
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (14,53,true,false,0); 	-- 76
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (14,54,true,false,0); 	-- 77
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (14,55,true,false,0); 	-- 78
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (14,56,true,false,0); 	-- 79
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (14,57,true,false,0); 	-- 80
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (14,63,true,false,0); 	-- 81
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (14,65,true,false,0); 	-- 82
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (14,100,true,false,0);	-- 83
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (14,101,true,false,0); -- 84
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (14,102,true,false,0); -- 85
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (14,103,true,false,0); -- 86
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (14,104,true,false,0); -- 87
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (14,105,true,false,0); -- 88
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (14,106,true,false,0); -- 89
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (14,107,true,false,0); -- 90
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (14,108,true,false,0); -- 91
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (14,109,true,false,0); -- 92
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (14,110,true,false,0); -- 93
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (14,111,true,false,0); -- 94
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (14,112,true,false,0); -- 95
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (14,113,true,false,0); -- 96
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (14,114,true,false,0); -- 97
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (14,115,true,false,0); -- 98
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (14,116,true,false,0); -- 99
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (14,119,true,false,0); --100
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (14,120,true,false,0); --101
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (14,121,true,false,0); --102
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (14,122,true,false,0); --103
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (15,1,true,false,0) ;	--104
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (15,2,true,false,0) ;	--105
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (15,3,true,false,0); 	--106
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (15,5,true,false,0); 	--107
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (15,10,true,false,0); 	--108
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (15,11,true,false,0); 	--109
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (15,27,true,false,0); 	--110
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (15,28,true,false,0); 	--111
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (15,33,true,false,0); 	--112
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (15,34,true,false,0);	--113
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (15,38,true,false,0); 	--114
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (15,40,true,false,0); 	--115
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (15,42,true,false,0); 	--116
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (15,51,true,false,0); 	--117
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (15,53,true,false,0); 	--118
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (15,54,true,false,0); 	--119
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (15,55,true,false,0); 	--120
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (15,56,true,false,0); 	--121
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (15,57,true,false,0); 	--122
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (15,63,true,false,0); 	--123
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (15,65,true,false,0); 	--124
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (15,101,true,false,0); --125
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (15,103,true,false,0); --126
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (15,104,true,false,0); --127
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (15,105,true,false,0); --128
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (15,106,true,false,0); --129
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (15,107,true,false,0); --130
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (15,108,true,false,0); --131
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (15,109,true,false,0); --132
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (15,114,true,false,0); --133
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (15,112,true,false,0); --134
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (15,116,true,false,0); --135
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (15,117,true,false,0); --136
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (15,118,true,false,0); --137
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (15,119,true,false,0); --138
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (15,120,true,false,0); --139
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (15,121,true,false,0); --140
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (15,122,true,false,0); --141
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (16,1,true,false,0) ;	--142
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (17,1,true,false,0); 	--143
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (17,2,true,false,0); 	--144
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (17,4,true,false,0); 	--145
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (17,36,true,false,0); 	--146
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (17,51,true,false,0); 	--147
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (17,53,true,false,0); 	--148
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (18,1,true,false,0); 	--149
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (18,2,true,false,0); 	--150
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (18,118,true,false,0); --151
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (18,120,true,false,0); --152
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (18,121,true,false,0); --153
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (18,122,true,false,0); --154
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (19,1,true,false,0) ;	--155
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (20,1,true,false,0); 	--156
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (20,2,true,false,0); 	--157
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (20,3,true,false,0); 	--158
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (20,4,true,false,0); 	--159
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (20,7,true,false,0); 	--160
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (20,8,true,false,0); 	--161
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (20,10,true,false,0); 	--162
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (20,22,true,false,0); 	--163
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (20,23,true,false,0); 	--164
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (20,27,true,false,0); 	--165
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (20,32,true,false,0); 	--166
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (20,33,true,false,0); 	--167
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (20,34,true,false,0); 	--168
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (20,38,true,false,0); 	--169
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (20,45,true,false,0); 	--170
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (20,51,true,false,0); 	--171
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (20,63,true,false,0); 	--172
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (20,100,true,false,0); --173
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (20,101,true,false,0); --174
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (20,102,true,false,0); --175
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (20,111,true,false,0); --176
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (20,116,true,false,0); --177
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (20,127,true,false,0); --178
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (20,128,true,false,0); --179
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (20,129,true,false,0); --180
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (20,130,true,false,0); --181
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (20,131,true,false,0); --182
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (20,132,true,false,0); --183
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (20,133,true,false,0); --184
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (20,134,true,false,0); --185
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (20,135,true,false,0); --186
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (20,136,true,false,0); --187
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (21,1,true,false,0) ;	--188
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (22,1,true,false,0); 	--189
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (23,1,true,false,0); 	--190
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (24,1,true,false,0); 	--191
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (25,1,true,false,0); 	--192
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (26,1,true,false,0); 	--193
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (27,1,true,false,0); 	--194
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (28,1,true,false,0); 	--195
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (28,2,true,false,0); 	--196
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (28,3,true,false,0); 	--197
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (28,4,true,false,0); 	--198
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (28,10,true,false,0); 	--199
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (28,53,true,false,0); 	--200
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (28,123,true,false,0); --201
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (29,1,true,false,0); 	--202
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (30,1,true,false,0); 	--203
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (31,1,true,false,0); 	--204
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (32,1,true,false,0); 	--205
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (32,2,true,false,0); 	--206
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (32,3,true,false,0); 	--207
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (32,4,true,false,0); 	--208
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (32,31,true,false,0); 	--209
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (32,38,true,false,0); 	--210
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (32,123,true,false,0); --211
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (32,124,true,false,0); --212
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (33,1,true,false,0); 	--213
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (33,2,true,false,0); 	--214
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (33,4,true,false,0); 	--215
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (33,10,true,false,0); 	--216
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (33,95,true,false,0); 	--217
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (33,125,true,false,0); --218
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (34,1,true,false,0); 	--219
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (35,1,true,false,0); 	--220
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (35,2,true,false,0); 	--221
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (35,3,true,false,0); 	--222
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (35,28,true,false,0); 	--223
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (35,38,true,false,0); 	--224
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (35,40,true,false,0); 	--225
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (35,58,true,false,0); 	--226
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (35,96,true,false,0); 	--227
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (35,126,true,false,0); --228
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (36,1,true,false,0); 	--229
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (37,1,true,false,0); 	--230
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (38,1,true,false,0); 	--231
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (39,1,true,false,0); 	--232
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (40,1,true,false,0); 	--233
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (41,1,true,false,0); 	--234
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (42,1,true,false,0); 	--235
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (43,1,true,false,0); 	--236
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (44,1,true,false,0); 	--237
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (45,1,true,false,0); 	--238
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (46,1,true,false,0); 	--239
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (47,1,true,false,0); 	--240
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (48,1,true,false,0); 	--241
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (49,1,true,false,0); 	--242
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (50,1,true,false,0); 	--243
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (51,1,true,false,0); 	--244
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (52,1,true,false,0); 	--245
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (52,2,true,false,0); 	--246
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (52,4,true,false,0); 	--247
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (52,9,true,false,0); 	--248
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (52,10,true,false,0); 	--249
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (52,32,true,false,0); 	--250
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (52,34,true,false,0); 	--251
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (52,53,true,false,0); 	--252
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (52,101,true,false,0); --253
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (53,1,true,false,0); 	--254
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (53,2,true,false,0); 	--255
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (53,3,true,false,0); 	--256
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (53,4,true,false,0); 	--257
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (53,10,true,false,0); 	--258
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (53,53,true,false,0); 	--259
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (54,1,true,false,0); 	--260
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (55,1,true,false,0); 	--261
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (56,1,true,false,0); 	--262
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (56,2,true,false,0) ;	--263
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (56,18,true,false,0);	--264
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (56,19,true,false,0); 	--265
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (57,1,true,false,0); 	--266
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (58,1,true,false,0); 	--267
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (59,1,true,false,0); 	--268
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (59,2,true,false,0); 	--269
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (59,3,true,false,0); 	--270
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (59,4,true,false,0); 	--271
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (59,28,true,false,0); 	--272
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (60,1,true,false,0); 	--273
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (61,1,true,false,0); 	--274
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (62,1,true,false,0); 	--275
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (63,1,true,false,0); 	--276
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (64,1,true,false,0); 	--277
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (65,1,true,false,0); 	--278
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (66,1,true,false,0); 	--279
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (66,2,true,false,0); 	--280
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (66,3,true,false,0); 	--281
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (66,4,true,false,0); 	--282
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (66,7,true,false,0); 	--283
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (66,8,true,false,0); 	--284
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (66,10,true,false,0); 	--285
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (66,22,true,false,0); 	--286
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (66,23,true,false,0); 	--287
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (66,27,true,false,0); 	--288
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (66,32,true,false,0); 	--289
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (66,33,true,false,0); 	--290
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (66,34,true,false,0); 	--291
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (66,38,true,false,0); 	--292
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (66,45,true,false,0); 	--293
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (66,51,true,false,0); 	--294
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (66,63,true,false,0); 	--295
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (66,100,true,false,0); --296
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (66,101,true,false,0); --297
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (66,102,true,false,0); --298
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (66,111,true,false,0); --299
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (66,116,true,false,0); --300
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (66,127,true,false,0); --301
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (66,128,true,false,0); --302
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (66,129,true,false,0); --303
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (66,130,true,false,0); --304
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (66,131,true,false,0); --305
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (66,132,true,false,0); --306
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (66,133,true,false,0); --307
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (66,134,true,false,0); --308
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (66,135,true,false,0); --309
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (66,136,true,false,0); --310
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (67,1,true,false,0); 	--311
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (68,1,true,false,0); 	--312
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (69,1,true,false,0); 	--313
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (69,2,true,false,0); 	--314
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (69,3,true,false,0); 	--315
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (69,4,true,false,0); 	--316
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (69,21,true,false,0); 	--317
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (69,22,true,false,0); 	--318
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (69,23,true,false,0); 	--319
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (69,26,true,false,0); 	--320
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (69,28,true,false,0); 	--321
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (69,33,true,false,0); 	--332
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (69,38,true,false,0); 	--333
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (69,44,true,false,0); 	--334
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (69,51,true,false,0); 	--335
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (69,53,true,false,0); 	--336
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (69,63,true,false,0); 	--337
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (69,65,true,false,0); 	--338
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (69,68,true,false,0); 	--339
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (69,71,true,false,0); 	--340
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (69,74,true,false,0); 	--341
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (69,111,true,false,0); --342
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (69,123,true,false,0); --343
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (69,130,true,false,0); --344
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (70,1,true,false,0); 	--345
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (71,1,true,false,0); 	--346
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (71,2,true,false,0); 	--347
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (71,3,true,false,0); 	--348
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (71,4,true,false,0); 	--349
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (71,22,true,false,0); 	--350
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (71,23,true,false,0); 	--351
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (71,28,true,false,0); 	--352
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (71,33,true,false,0); 	--353
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (71,38,true,false,0); 	--354
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (71,44,true,false,0); 	--355
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (71,51,true,false,0); 	--356
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (71,53,true,false,0); 	--357
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (71,65,true,false,0); 	--358
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (71,111,true,false,0); --359
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (71,123,true,false,0); --360
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (72,1,true,false,0);	--361
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (73,1,true,false,0); 	--362
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (74,1,true,false,0); 	--363
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (75,1,true,false,0); 	--364
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (75,2,true,false,0); 	--365
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (75,3,true,false,0); 	--366
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (75,4,true,false,0); 	--367
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (75,13,true,false,0); 	--368
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (75,15,true,false,0); 	--369
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (75,20,true,false,0); 	--370
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (75,21,true,false,0); 	--371
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (75,22,true,false,0); 	--372
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (75,23,true,false,0); 	--373
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (75,26,true,false,0); 	--374
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (75,28,true,false,0); 	--375
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (75,34,true,false,0); 	--376
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (75,38,true,false,0); 	--378
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (75,44,true,false,0); 	--379
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (75,51,true,false,0); 	--380
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (75,63,true,false,0); 	--381
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (75,65,true,false,0); 	--382
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (75,71,true,false,0); 	--383
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (75,72,true,false,0); 	--384
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (75,95,true,false,0); 	--385
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (75,111,true,false,0); --386
INSERT INTO breedcolours (breed_id,colour_id,available,selected,class_no) VALUES (75,123,true,false,0); --387

insert into showclasses (class_no,name,breed,breed_class,section,members_only,breeders_only,upsidedown,  
exhibit_age,exhibit_gender,exhibitor_age,exhibitor_gender,results1,results2,results3,results4,results5,results6,results7) VALUES (100,' ',14,true,1,false,false,false,1,3,3,3,0,0,0,0,0,0,0);
