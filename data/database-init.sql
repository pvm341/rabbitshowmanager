--
-- Copyright (C) 2014 paul
--
-- This program is free software: you can redistribute it and/or modify
-- it under the terms of the GNU General Public License as published by
-- the Free Software Foundation, either version 3 of the License, or
-- (at your option) any later version.
--
-- This program is distributed in the hope that it will be useful,
-- but WITHOUT ANY WARRANTY; without even the implied warranty of
-- MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
-- GNU General Public License for more details.
--
-- You should have received a copy of the GNU General Public License
-- along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
-- human genders table
-- this could be coded in the application but for simplicity included in 
-- the database as some classes need this information
CREATE TABLE human_genders(
id INTEGER,
exhibitor INTEGER,
gender INTEGER,
gender_class VARCHAR(10),
gender_text VARCHAR(14),
PRIMARY KEY (id)
);

-- exhibit ages table 
-- this could be coded in the application but for simplicity included in the
-- database as some classes need this information and to make changes to exhibit
-- animal easier it is currently for rabbits, change to cavy aka Guinea pig 
CREATE TABLE exhibit_ages(
id INTEGER,
age INTEGER,
age_text VARCHAR(15),
abbrev VARCHAR(6),
PRIMARY KEY (id)
);

-- human ages table
-- this could be coded in the application but for simplicity included in the
-- database as some classes need this information
CREATE TABLE human_ages(
id INTEGER,
age INTEGER,
age_text VARCHAR(15),
abbrev VARCHAR(6),
PRIMARY KEY (id)
);

-- colours table
CREATE TABLE colours (
id INTEGER,
colour VARCHAR(35)UNIQUE,
abbrev VARCHAR(4) UNIQUE,
PRIMARY KEY (id)
);

-- Breeds table 
CREATE TABLE breeds(
id INTEGER,
adult_age INTEGER,
top_pen_req BOOL,
section INTEGER,
breed VARCHAR(30) UNIQUE,
PRIMARY KEY (id),
FOREIGN KEY (adult_age) references exhibit_ages(id)
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
-- this could be coded in the application but for simplicity included in the
-- database as some classes need this information and to make changes to a
-- exhibit animal easier it is currently for rabbits,
-- change to cavy aka Guinea pig 
CREATE TABLE exhibit_genders(
id INTEGER,
gender INTEGER,
gender_class VARCHAR(10),
gender_text VARCHAR(14),
PRIMARY KEY (id)
);

-- Show Sections table 
-- this could be coded in the application but for simplicity included in the
-- database as some classes need this information
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

CREATE TABLE availablecolours(
colour INTEGER,
PRIMARY KEY (colour),
FOREIGN KEY (colour) REFERENCES colours(id)
);

INSERT INTO exhibit_genders (id,gender,gender_class,gender_text) VALUES (1,3,'Open','n/a');
INSERT INTO exhibit_genders (id,gender,gender_class,gender_text) VALUES (2,1,'Bucks','Buck');
INSERT INTO exhibit_genders (id,gender,gender_class,gender_text) VALUES (3,2,'Does','Doe');

INSERT INTO human_genders (id,gender,gender_class,gender_text) VALUES (1,3,'Open','Group/Stud');
INSERT INTO human_genders (id,gender,gender_class,gender_text) VALUES (2,1,'Gents','Gentleman');
INSERT INTO human_genders (id,gender,gender_class,gender_text) VALUES (3,2,'Ladies','Lady');

INSERT INTO human_ages (id,age,age_text,abbrev) VALUES (1,3,'Open','DUP');
INSERT INTO human_ages (id,age,age_text,abbrev) VALUES (2,1,'Juvenile','Juv');
INSERT INTO human_ages (id,age,age_text,abbrev) VALUES (3,2,'Adult','ADT');

INSERT INTO exhibit_ages (id,age,age_text,abbrev) VALUES (1,7,'Any Age','AA');
INSERT INTO exhibit_ages (id,age,age_text,abbrev) VALUES (2,1,'under 14 weeks','u/14w');
INSERT INTO exhibit_ages (id,age,age_text,abbrev) VALUES (3,3,'under 4 months','u/4m');
INSERT INTO exhibit_ages (id,age,age_text,abbrev) VALUES (4,3,'under 5 months','u/5m');
INSERT INTO exhibit_ages (id,age,age_text,abbrev) VALUES (5,4,'Adult','Adt');

INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (1,4,false,1,'Any Variety');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (2,4,false,1,'Any Other Variety');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (3,4,true,1,'Angora');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (4,4,false,1,'Black Hare');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (5,3,false,1,'Dutch');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (6,4,false,1,'English');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (7,4,false,1,'Flemish Giant');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (8,4,false,1,'Giant Papillon');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (9,4,false,1,'Hare Belgian');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (10,4,false,1,'Hare Tan');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (11,4,false,1,'Harlequin');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (12,4,false,1,'Himalayan');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (13,4,false,1,'Lionhead');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (14,4,false,1,'Netherland Dwarf');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (15,4,false,1,'Polish');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (16,4,false,1,'Rhinelander');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (17,4,false,1,'Silver');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (18,4,false,1,'Tan');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (19,4,false,1,'Thrianta');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (20,4,false,8,'Rex');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (21,4,false,4,'Alaska');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (22,4,false,4,'Argente Bleu');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (23,4,false,4,'Argente Brun');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (24,4,false,4,'Argente Crème');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (25,4,false,4,'Argente de Champagne');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (26,4,false,4,'Argente Noir');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (27,4,false,4,'Beige');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (28,4,false,4,'Beveren');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (29,4,false,4,'Blanc de Bouscat');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (30,4,false,4,'Blanc de Hotot');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (31,4,false,4,'Blanc de Termonde');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (32,4,false,4,'British Giant');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (33,4,false,4,'Californian');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (34,4,false,4,'Chinchilla');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (35,4,false,4,'Chinchilla Giganta');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (36,4,false,4,'Continental Giant coloured');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (37,4,false,4,'Continental Giant white');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (38,4,false,4,'Deilenaar');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (39,4,false,4,'Fox - Silver');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (40,4,false,4,'Golden Glavcot');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (41,4,false,4,'Havana');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (42,4,false,4,'Hulstlander');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (43,4,false,4,'Lilac');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (44,4,false,4,'New Zealand White');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (45,4,false,4,'New Zealand Black');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (46,4,false,4,'New Zealand Blue');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (47,4,false,4,'New Zealand Red');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (48,4,false,4,'Perlfee');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (49,4,false,4,'Pointed Beveren');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (50,4,false,4,'Siamese Sable - Marten Sable');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (51,4,false,4,'Sallander');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (52,4,false,4,'Satin');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (53,4,false,4,'Siberian');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (54,4,false,4,'Smoke Pearl');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (55,4,false,4,'Squirrel');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (56,4,false,4,'Sussex');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (57,4,false,4,'Swiss Fox');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (58,4,false,4,'Thuringer');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (59,4,false,4,'Vienna Coloured');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (60,4,false,4,'Vienna White');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (61,4,false,4,'Wheaten');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (62,4,false,4,'Wheaten Lynx');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (63,4,false,4,'Fauve de Bourgogne');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (64,4,false,4,'Argente St Hubert');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (65,4,false,4,'Miniature Satin');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (66,4,false,8,'Mini Rex');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (67,4,false,2,'Cashmere Lop');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (68,4,false,2,'Miniature Cashmere Lop');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (69,4,false,2,'Dwarf Lop');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (70,4,false,2,'English Lop');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (71,4,false,2,'French Lop');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (72,4,false,2,'German Lop');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (73,4,false,2,'Meissner Lop');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (74,4,false,2,'Miniature Lop');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (75,4,false,2,'Miniature Lion Lop');

INSERT INTO colours (id,colour,abbrev) VALUES (1,'Any Colour','AC');
INSERT INTO colours (id,colour,abbrev) VALUES (2,'Any Other Colour','AOC');
INSERT INTO colours (id,colour,abbrev) VALUES (3,'Black','Blk');
INSERT INTO colours (id,colour,abbrev) VALUES (4,'Blue','Blu');
INSERT INTO colours (id,colour,abbrev) VALUES (5,'Blue Eyed White','BEW');
INSERT INTO colours (id,colour,abbrev) VALUES (6,'Chocolate Brown','ChB');
INSERT INTO colours (id,colour,abbrev) VALUES (7,'Havana','Hav');
INSERT INTO colours (id,colour,abbrev) VALUES (8,'Ermine','Erm');
INSERT INTO colours (id,colour,abbrev) VALUES (9,'Ivory','Ivy');
INSERT INTO colours (id,colour,abbrev) VALUES (10,'Lilac','Llc');
INSERT INTO colours (id,colour,abbrev) VALUES (11,'Red Eyed White','REW');
INSERT INTO colours (id,colour,abbrev) VALUES (12,'Smoke','Smk');
INSERT INTO colours (id,colour,abbrev) VALUES (13,'Beige-Isabella','BIs');
INSERT INTO colours (id,colour,abbrev) VALUES (14,'Blue Cream','BlC');
INSERT INTO colours (id,colour,abbrev) VALUES (15,'Bluepoint','BlP');
INSERT INTO colours (id,colour,abbrev) VALUES (16,'Bronze','Brz');
INSERT INTO colours (id,colour,abbrev) VALUES (17,'Chocolate Tortoiseshell','ChT');
INSERT INTO colours (id,colour,abbrev) VALUES (18,'Cream','Crm');
INSERT INTO colours (id,colour,abbrev) VALUES (19,'Gold','Gld');
INSERT INTO colours (id,colour,abbrev) VALUES (20,'Iron Grey','IGr');
INSERT INTO colours (id,colour,abbrev) VALUES (21,'Sealpoint','Spt');
INSERT INTO colours (id,colour,abbrev) VALUES (22,'Siamese Sable Dark','SSsD');
INSERT INTO colours (id,colour,abbrev) VALUES (23,'Siamese Sable Medium','SSsM');
INSERT INTO colours (id,colour,abbrev) VALUES (24,'Siamese Smoke Dark','SSmD');
INSERT INTO colours (id,colour,abbrev) VALUES (25,'Siamese Smoke Medium','SSmM');
INSERT INTO colours (id,colour,abbrev) VALUES (26,'Sooty-Fawn','SFwn');
INSERT INTO colours (id,colour,abbrev) VALUES (27,'Tortoiseshell','Tort');
INSERT INTO colours (id,colour,abbrev) VALUES (28,'Agouti','Agti');
INSERT INTO colours (id,colour,abbrev) VALUES (29,'Belgium Hare','BelH');
INSERT INTO colours (id,colour,abbrev) VALUES (30,'Blue Grey','BuGr');
INSERT INTO colours (id,colour,abbrev) VALUES (31,'Brown Grey','BrGr');
INSERT INTO colours (id,colour,abbrev) VALUES (32,'Castor','Cast');
INSERT INTO colours (id,colour,abbrev) VALUES (33,'Chinchilla','Chin');
INSERT INTO colours (id,colour,abbrev) VALUES (34,'Cinnamon','Cinn');
INSERT INTO colours (id,colour,abbrev) VALUES (35,'Flemish','Flem');
INSERT INTO colours (id,colour,abbrev) VALUES (36,'Grey','Grey');
INSERT INTO colours (id,colour,abbrev) VALUES (37,'Lynx-Wheaten Lynx','LyWL');
INSERT INTO colours (id,colour,abbrev) VALUES (38,'Opal','Opal');
INSERT INTO colours (id,colour,abbrev) VALUES (39,'Perlfee','PerF');
INSERT INTO colours (id,colour,abbrev) VALUES (40,'Red Agouti','RedA');
INSERT INTO colours (id,colour,abbrev) VALUES (41,'Sandy','Sand');
INSERT INTO colours (id,colour,abbrev) VALUES (42,'Squirrel','Squl');
INSERT INTO colours (id,colour,abbrev) VALUES (43,'Broken','Brkn');
INSERT INTO colours (id,colour,abbrev) VALUES (44,'Butterfly','ButF');
INSERT INTO colours (id,colour,abbrev) VALUES (45,'Dalmation','Dalm');
INSERT INTO colours (id,colour,abbrev) VALUES (46,'Dutch','Dtch');
INSERT INTO colours (id,colour,abbrev) VALUES (47,'English','Engl');
INSERT INTO colours (id,colour,abbrev) VALUES (48,'Hotot','Hott');
INSERT INTO colours (id,colour,abbrev) VALUES (49,'Papillon','Papl');
INSERT INTO colours (id,colour,abbrev) VALUES (50,'Rhinelander','Rhin');
INSERT INTO colours (id,colour,abbrev) VALUES (51,'Fawn','Fawn');
INSERT INTO colours (id,colour,abbrev) VALUES (52,'Golden Glavcot','GdGt');
INSERT INTO colours (id,colour,abbrev) VALUES (54,'Brown','Brwn');
INSERT INTO colours (id,colour,abbrev) VALUES (57,'Himalayan Black','HBlk');
INSERT INTO colours (id,colour,abbrev) VALUES (58,'Himalayan Blue','HBlu');
INSERT INTO colours (id,colour,abbrev) VALUES (59,'Himalayan Chocolate','HCho');
INSERT INTO colours (id,colour,abbrev) VALUES (60,'Himalayan Lilac','HLil');
INSERT INTO colours (id,colour,abbrev) VALUES (61,'Light Steel','LStl');
INSERT INTO colours (id,colour,abbrev) VALUES (62,'Magpie Black','MBlk');
INSERT INTO colours (id,colour,abbrev) VALUES (63,'Magpie Brown','MBrn');
INSERT INTO colours (id,colour,abbrev) VALUES (64,'Magpie Blue','MBlu');
INSERT INTO colours (id,colour,abbrev) VALUES (65,'Magpie Lilac','MLil');
INSERT INTO colours (id,colour,abbrev) VALUES (66,'Orange','Oran');
INSERT INTO colours (id,colour,abbrev) VALUES (67,'Red','Red');
INSERT INTO colours (id,colour,abbrev) VALUES (68,'Steel','Stel');
INSERT INTO colours (id,colour,abbrev) VALUES (69,'Thrianta','Thri');
INSERT INTO colours (id,colour,abbrev) VALUES (70,'Wheaten','Whet');
INSERT INTO colours (id,colour,abbrev) VALUES (71,'Black Fox','BlFx');
INSERT INTO colours (id,colour,abbrev) VALUES (72,'Black Otter','BlOt');
INSERT INTO colours (id,colour,abbrev) VALUES (73,'Black Tan','BlTn');
INSERT INTO colours (id,colour,abbrev) VALUES (74,'Blue Fox','BuFx');
INSERT INTO colours (id,colour,abbrev) VALUES (75,'Blue Otter','BuOt');
INSERT INTO colours (id,colour,abbrev) VALUES (76,'Blue Tan','BuTn');
INSERT INTO colours (id,colour,abbrev) VALUES (77,'Chocolate Fox','ChoF');
INSERT INTO colours (id,colour,abbrev) VALUES (78,'Chocolate Otter','ChOt');
INSERT INTO colours (id,colour,abbrev) VALUES (79,'Chocolate Tan','ChTn');
INSERT INTO colours (id,colour,abbrev) VALUES (80,'Lilac Fox','LFox');
INSERT INTO colours (id,colour,abbrev) VALUES (81,'Lilac Otter','LiOt');
INSERT INTO colours (id,colour,abbrev) VALUES (82,'Lilac Tan','LiTn');
INSERT INTO colours (id,colour,abbrev) VALUES (83,'Argente Bleu','ArBu');
INSERT INTO colours (id,colour,abbrev) VALUES (84,'Argente Brun','ArBr');
INSERT INTO colours (id,colour,abbrev) VALUES (85,'Argente Crème','ArCr');
INSERT INTO colours (id,colour,abbrev) VALUES (86,'Argente de Champagne','ArCh');
INSERT INTO colours (id,colour,abbrev) VALUES (87,'Argente Noir','ArNr');
INSERT INTO colours (id,colour,abbrev) VALUES (88,'Meissener','Meis');
INSERT INTO colours (id,colour,abbrev) VALUES (89,'Silver Blue','SiBu');
INSERT INTO colours (id,colour,abbrev) VALUES (90,'Silver Brown','SiBr');
INSERT INTO colours (id,colour,abbrev) VALUES (91,'Silver Fawn','SiFn');
INSERT INTO colours (id,colour,abbrev) VALUES (92,'Silver Grey','SiGy');
INSERT INTO colours (id,colour,abbrev) VALUES (93,'Sable Dark','SabD');
INSERT INTO colours (id,colour,abbrev) VALUES (94,'Sable Light','Sabl');
INSERT INTO colours (id,colour,abbrev) VALUES (95,'Marten Light','MarL');
INSERT INTO colours (id,colour,abbrev) VALUES (96,'Marten Medium','MarM');
INSERT INTO colours (id,colour,abbrev) VALUES (97,'Marten Dark','MarD');
INSERT INTO colours (id,colour,abbrev) VALUES (98,'Chocolate','Choc');
INSERT INTO colours (id,colour,abbrev) VALUES (99,'Yellow','Yell');
INSERT INTO colours (id,colour,abbrev) VALUES (100,'Pale Grey','PaGy');
INSERT INTO colours (id,colour,abbrev) VALUES (101,'Steel Grey','StGy');
INSERT INTO colours (id,colour,abbrev) VALUES (102,'Tri-colour','TriC');
INSERT INTO colours (id,colour,abbrev) VALUES (103,'Smoke Pearl','SmkP');
INSERT INTO colours (id,colour,abbrev) VALUES (104,'Lynx','Lynx');
INSERT INTO colours (id,colour,abbrev) VALUES (105,'Tan','Tan');
INSERT INTO colours (id,colour,abbrev) VALUES (106,'Otter Black','OBlk');
INSERT INTO colours (id,colour,abbrev) VALUES (107,'Otter Blue','OBlu');
INSERT INTO colours (id,colour,abbrev) VALUES (108,'Otter Chocolate','OChc');
INSERT INTO colours (id,colour,abbrev) VALUES (109,'Otter Lilac','OLil');
INSERT INTO colours (id,colour,abbrev) VALUES (110,'Fox Black','FBlk');
INSERT INTO colours (id,colour,abbrev) VALUES (111,'Fox Blue','FBlu');
INSERT INTO colours (id,colour,abbrev) VALUES (112,'Fox Chocolate','FCho');
INSERT INTO colours (id,colour,abbrev) VALUES (113,'Fox Lilac','FLil');
INSERT INTO colours (id,colour,abbrev) VALUES (116,'Siamese Sable Light','SSaL');
INSERT INTO colours (id,colour,abbrev) VALUES (117,'Siamese Smoke Pearl','SSPe');
INSERT INTO colours (id,colour,abbrev) VALUES (118,'Marten Sable Light','MSaL');
INSERT INTO colours (id,colour,abbrev) VALUES (119,'Marten Sable Medium','MSaM');
INSERT INTO colours (id,colour,abbrev) VALUES (120,'Marten Sable Dark','MSaD');
INSERT INTO colours (id,colour,abbrev) VALUES (121,'Marten Smoke Pearl','MSmP');
INSERT INTO colours (id,colour,abbrev) VALUES (122,'Sable Siamese','SSia');
INSERT INTO colours (id,colour,abbrev) VALUES (123,'Marten Sable','MSab');
INSERT INTO colours (id,colour,abbrev) VALUES (124,'Blue and Tan','BluT');
INSERT INTO colours (id,colour,abbrev) VALUES (125,'Black and Tan','BlkT');
INSERT INTO colours (id,colour,abbrev) VALUES (126,'Chocolate and Tan','ChoT');
INSERT INTO colours (id,colour,abbrev) VALUES (127,'Lilac and Tan','LilT');
INSERT INTO colours (id,colour,abbrev) VALUES (128,'White','Wht');
INSERT INTO colours (id,colour,abbrev) VALUES (129,'Dark Steel Grey','DSGy');
INSERT INTO colours (id,colour,abbrev) VALUES (130,'Normal','Norm');
INSERT INTO colours (id,colour,abbrev) VALUES (131,'Dark Steel','DStl');
INSERT INTO colours (id,colour,abbrev) VALUES (134,'Nutria','Nutr');
INSERT INTO colours (id,colour,abbrev) VALUES (135,'Seal Siamese','SeaS');
INSERT INTO colours (id,colour,abbrev) VALUES (136,'Fox','Fox');
INSERT INTO colours (id,colour,abbrev) VALUES (137,'Sable Marten','SMar');
INSERT INTO colours (id,colour,abbrev) VALUES (138,'Otter','Ottr');
INSERT INTO colours (id,colour,abbrev) VALUES (139,'Harlequin','Harl');
INSERT INTO colours (id,colour,abbrev) VALUES (140,'Himalayan','Himl');
INSERT INTO colours (id,colour,abbrev) VALUES (141,'Silver Seal','SiSe');
INSERT INTO colours (id,colour,abbrev) VALUES (142,'Satin Rex','SaRe');
INSERT INTO colours (id,colour,abbrev) VALUES (143,'Astrex','Astx');
INSERT INTO colours (id,colour,abbrev) VALUES (144,'Opossum','Opos');

INSERT INTO breedcolours (breed_id,colour_id) VALUES (1,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (3,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (3,2);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (3,4);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (3,12);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (3,14);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (3,18);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (3,19);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (3,30);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (3,31);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (3,33);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (3,34);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (3,93);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (3,94);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (3,95);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (3,96);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (3,97);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (3,98);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (4,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (5,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (5,2);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (5,3);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (5,4);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (5,27);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (5,31);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (5,98);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (5,99);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (5,100);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (5,101);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (5,102);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (6,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (6,2);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (6,3);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (6,4);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (6,27);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (6,36);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (6,98);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (6,102);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (7,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (8,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (9,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (10,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (11,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (11,2);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (11,3);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (11,4);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (11,10);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (11,54);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (11,62);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (11,63);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (11,64);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (11,65);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (12,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (12,2);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (12,4);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (12,10);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (12,98);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (13,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (14,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (14,2);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (14,3);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (14,4);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (14,5);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (14,10);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (14,11);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (14,21);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (14,23);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (14,24);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (14,25);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (14,27);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (14,28);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (14,33);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (14,38);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (14,42);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (14,51);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (14,54);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (14,57);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (14,58);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (14,59);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (14,60);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (14,66);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (14,68);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (14,103);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (14,104);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (14,105);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (14,106);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (14,107);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (14,108);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (14,109);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (14,110);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (14,111);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (14,112);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (14,113);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (14,116);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (14,117);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (14,118);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (14,119);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (14,120);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (14,121);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (14,124);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (14,125);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (14,126);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (14,127);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (15,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (15,2);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (15,3);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (15,5);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (15,10);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (15,11);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (15,27);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (15,28);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (15,33);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (15,34);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (15,38);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (15,40);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (15,42);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (15,51);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (15,54);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (15,57);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (15,58);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (15,59);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (15,60);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (15,66);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (15,68);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (15,104);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (15,106);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (15,107);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (15,108);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (15,109);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (15,110);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (15,111);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (15,112);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (15,113);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (15,117);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (15,121);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (15,122);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (15,123);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (15,124);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (15,125);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (15,126);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (15,127);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (16,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (17,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (17,2);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (17,4);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (17,36);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (17,51);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (17,54);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (18,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (18,2);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (18,124);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (18,125);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (18,126);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (18,127);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (19,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (20,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (20,2);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (20,3);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (20,4);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (20,7);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (20,8);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (20,10);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (20,22);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (20,23);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (20,27);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (20,32);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (20,33);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (20,34);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (20,38);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (20,45);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (20,51);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (20,66);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (20,103);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (20,104);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (20,105);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (20,116);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (20,121);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (20,134);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (20,135);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (20,136);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (20,137);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (20,138);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (20,139);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (20,140);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (20,141);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (20,142);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (20,143);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (21,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (22,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (23,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (24,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (25,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (26,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (27,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (28,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (28,2);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (28,3);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (28,4);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (28,10);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (28,54);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (28,128);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (29,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (30,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (31,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (32,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (32,2);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (32,3);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (32,4);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (32,31);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (32,38);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (32,128);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (32,129);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (33,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (33,2);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (33,4);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (33,10);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (33,98);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (33,130);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (34,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (35,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (35,2);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (35,3);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (35,28);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (35,38);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (35,40);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (35,61);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (35,99);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (35,131);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (36,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (37,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (38,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (39,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (40,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (41,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (42,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (43,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (44,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (45,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (46,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (47,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (48,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (49,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (50,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (51,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (52,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (52,2);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (52,4);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (52,9);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (52,10);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (52,32);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (52,34);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (52,54);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (52,104);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (53,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (53,2);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (53,3);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (53,4);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (53,10);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (53,54);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (54,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (55,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (56,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (56,2);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (56,18);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (56,19);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (57,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (58,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (59,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (59,2);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (59,3);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (59,4);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (59,28);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (60,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (61,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (62,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (63,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (64,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (65,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (66,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (66,2);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (66,3);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (66,4);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (66,7);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (66,8);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (66,10);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (66,22);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (66,23);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (66,27);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (66,32);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (66,33);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (66,34);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (66,38);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (66,45);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (66,51);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (66,66);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (66,103);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (66,104);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (66,105);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (66,116);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (66,121);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (66,134);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (66,135);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (66,136);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (66,137);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (66,138);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (66,139);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (66,140);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (66,141);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (66,142);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (66,143);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (67,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (68,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (69,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (69,2);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (69,3);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (69,4);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (69,21);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (69,22);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (69,23);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (69,26);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (69,28);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (69,33);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (69,38);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (69,44);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (69,51);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (69,54);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (69,66);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (69,68);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (69,71);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (69,74);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (69,77);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (69,116);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (69,128);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (69,137);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (70,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (71,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (71,2);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (71,3);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (71,4);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (71,22);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (71,23);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (71,28);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (71,33);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (71,38);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (71,44);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (71,51);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (71,54);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (71,68);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (71,116);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (71,128);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (72,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (73,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (74,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (75,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (75,2);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (75,3);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (75,4);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (75,13);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (75,15);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (75,20);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (75,21);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (75,22);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (75,23);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (75,26);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (75,28);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (75,34);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (75,38);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (75,44);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (75,51);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (75,66);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (75,68);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (75,74);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (75,75);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (75,98);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (75,116);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (75,128);
--INSERT INTO breedcolours (breed_id,colour_id) VALUES (75,145);

INSERT INTO ShowSections (id, section, section_text) VALUES (1,1,'Fancy');
INSERT INTO ShowSections (id, section, section_text) VALUES (2,2,'Lop');
INSERT INTO ShowSections (id, section, section_text) VALUES (3,4,'Fur');
INSERT INTO ShowSections (id, section, section_text) VALUES (4,8,'Rex');
INSERT INTO ShowSections (id, section, section_text) VALUES (5,15,'Open');
INSERT INTO ShowSections (id, section, section_text) VALUES (6,0,'Pet');
