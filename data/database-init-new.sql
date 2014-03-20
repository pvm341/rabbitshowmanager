DROP DATABASE rsm;

CREATE DATABASE rsm 
  WITH OWNER = postgres
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'en_GB.UTF-8'
       LC_CTYPE = 'en_GB.UTF-8'
       CONNECTION LIMIT = -1;

\c rsm
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

INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (1,3,false,1,'Any Variety');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (2,3,false,1,'Any Other Variety');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (3,3,true,1,'Angora');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (4,3,false,1,'Black Hare');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (5,2,false,1,'Dutch');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (6,3,false,1,'English');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (7,3,false,1,'Flemish Giant');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (8,3,false,1,'Giant Papillon');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (9,3,false,1,'Hare Belgian');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (10,3,false,1,'Hare Tan');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (11,3,false,1,'Harlequin');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (12,3,false,1,'Himalayan');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (13,3,false,1,'Lionhead');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (14,3,false,1,'Netherland Dwarf');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (15,3,false,1,'Polish');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (16,3,false,1,'Rhinelander');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (17,3,false,1,'Silver');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (18,3,false,1,'Tan');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (19,3,false,1,'Thrianta');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (20,3,false,8,'Rex');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (21,3,false,4,'Alaska');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (22,3,false,4,'Argente Bleu');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (23,3,false,4,'Argente Brun');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (24,3,false,4,'Argente Crème');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (25,3,false,4,'Argente de Champagne');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (26,3,false,4,'Argente Noir');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (27,3,false,4,'Beige');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (28,3,false,4,'Beveren');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (29,3,false,4,'Blanc de Bouscat');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (30,3,false,4,'Blanc de Hotot');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (31,3,false,4,'Blanc de Termonde');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (32,3,false,4,'British Giant');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (33,3,false,4,'Californian');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (34,3,false,4,'Chinchilla');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (35,3,false,4,'Chinchilla Giganta');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (36,3,false,4,'Continental Giant coloured');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (37,3,false,4,'Continental Giant white');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (38,3,false,4,'Deilenaar');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (39,3,false,4,'Fox - Silver');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (40,3,false,4,'Golden Glavcot');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (41,3,false,4,'Havana');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (42,3,false,4,'Hulstlander');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (43,3,false,4,'Lilac');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (44,3,false,4,'New Zealand White');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (45,3,false,4,'New Zealand Black');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (46,3,false,4,'New Zealand Blue');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (47,3,false,4,'New Zealand Red');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (48,3,false,4,'Perlfee');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (49,3,false,4,'Pointed Beveren');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (50,3,false,4,'Siamese Sable - Marten Sable');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (51,3,false,4,'Sallander');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (52,3,false,4,'Satin');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (53,3,false,4,'Siberian');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (54,3,false,4,'Smoke Pearl');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (55,3,false,4,'Squirrel');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (56,3,false,4,'Sussex');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (57,3,false,4,'Swiss Fox');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (58,3,false,4,'Thuringer');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (59,3,false,4,'Vienna Coloured');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (60,3,false,4,'Vienna White');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (61,3,false,4,'Wheaten');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (62,3,false,4,'Wheaten Lynx');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (63,3,false,4,'Fauve de Bourgogne');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (64,3,false,4,'Argente St Hubert');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (65,3,false,4,'Miniature Satin');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (66,3,false,8,'Mini Rex');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (67,3,false,2,'Cashmere Lop');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (68,3,false,2,'Miniature Cashmere Lop');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (69,3,false,2,'Dwarf Lop');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (70,3,false,2,'English Lop');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (71,3,false,2,'French Lop');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (72,3,false,2,'German Lop');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (73,3,false,2,'Meissner Lop');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (74,3,false,2,'Miniature Lop');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (75,3,false,2,'Miniature Lion Lop');

INSERT INTO colours (id,colour) VALUES (1,'Any Colour');
INSERT INTO colours (id,colour) VALUES (2,'Any Other Colour');
INSERT INTO colours (id,colour) VALUES (3,'Black');
INSERT INTO colours (id,colour) VALUES (4,'Blue');
INSERT INTO colours (id,colour) VALUES (5,'Blue Eyed White');
INSERT INTO colours (id,colour) VALUES (6,'Chocolate Brown');
INSERT INTO colours (id,colour) VALUES (7,'Havana');
INSERT INTO colours (id,colour) VALUES (8,'Ermine');
INSERT INTO colours (id,colour) VALUES (9,'Ivory');
INSERT INTO colours (id,colour) VALUES (10,'Lilac');
INSERT INTO colours (id,colour) VALUES (11,'Red Eyed White');
INSERT INTO colours (id,colour) VALUES (12,'Smoke');
INSERT INTO colours (id,colour) VALUES (13,'Beige-Isabella');
INSERT INTO colours (id,colour) VALUES (14,'Blue Cream');
INSERT INTO colours (id,colour) VALUES (15,'Bluepoint');
INSERT INTO colours (id,colour) VALUES (16,'Bronze');
INSERT INTO colours (id,colour) VALUES (17,'Chocolate Tortoiseshell');
INSERT INTO colours (id,colour) VALUES (18,'Cream');
INSERT INTO colours (id,colour) VALUES (19,'Gold');
INSERT INTO colours (id,colour) VALUES (20,'Iron Grey');
INSERT INTO colours (id,colour) VALUES (21,'Sealpoint');
INSERT INTO colours (id,colour) VALUES (22,'Siamese Sable Dark');
INSERT INTO colours (id,colour) VALUES (23,'Siamese Sable Medium');
INSERT INTO colours (id,colour) VALUES (24,'Siamese Smoke Dark');
INSERT INTO colours (id,colour) VALUES (25,'Siamese Smoke Medium');
INSERT INTO colours (id,colour) VALUES (26,'Sooty-Fawn');
INSERT INTO colours (id,colour) VALUES (27,'Tortoiseshell');
INSERT INTO colours (id,colour) VALUES (28,'Agouti');
INSERT INTO colours (id,colour) VALUES (29,'Belgium Hare');
INSERT INTO colours (id,colour) VALUES (30,'Blue Grey');
INSERT INTO colours (id,colour) VALUES (31,'Brown Grey');
INSERT INTO colours (id,colour) VALUES (32,'Castor');
INSERT INTO colours (id,colour) VALUES (33,'Chinchilla');
INSERT INTO colours (id,colour) VALUES (34,'Cinnamon');
INSERT INTO colours (id,colour) VALUES (35,'Flemish');
INSERT INTO colours (id,colour) VALUES (36,'Grey');
INSERT INTO colours (id,colour) VALUES (37,'Lynx-Wheaten Lynx');
INSERT INTO colours (id,colour) VALUES (38,'Opal');
INSERT INTO colours (id,colour) VALUES (39,'Perlfee');
INSERT INTO colours (id,colour) VALUES (40,'Red Agouti');
INSERT INTO colours (id,colour) VALUES (41,'Sandy');
INSERT INTO colours (id,colour) VALUES (42,'Squirrel');
INSERT INTO colours (id,colour) VALUES (43,'Broken');
INSERT INTO colours (id,colour) VALUES (44,'Butterfly');
INSERT INTO colours (id,colour) VALUES (45,'Dalmation');
INSERT INTO colours (id,colour) VALUES (46,'Dutch');
INSERT INTO colours (id,colour) VALUES (47,'English');
INSERT INTO colours (id,colour) VALUES (48,'Hotot');
INSERT INTO colours (id,colour) VALUES (49,'Papillon');
INSERT INTO colours (id,colour) VALUES (50,'Rhinelander');
INSERT INTO colours (id,colour) VALUES (51,'Fawn');
INSERT INTO colours (id,colour) VALUES (52,'Golden Glavcot');
INSERT INTO colours (id,colour) VALUES (54,'Brown');
INSERT INTO colours (id,colour) VALUES (57,'Himalayan Black');
INSERT INTO colours (id,colour) VALUES (58,'Himalayan Blue');
INSERT INTO colours (id,colour) VALUES (59,'Himalayan Chocolate');
INSERT INTO colours (id,colour) VALUES (60,'Himalayan Lilac');
INSERT INTO colours (id,colour) VALUES (61,'Light Steel');
INSERT INTO colours (id,colour) VALUES (62,'Magpie Black');
INSERT INTO colours (id,colour) VALUES (63,'Magpie Brown');
INSERT INTO colours (id,colour) VALUES (64,'Magpie Blue');
INSERT INTO colours (id,colour) VALUES (65,'Magpie Lilac');
INSERT INTO colours (id,colour) VALUES (66,'Orange');
INSERT INTO colours (id,colour) VALUES (67,'Red');
INSERT INTO colours (id,colour) VALUES (68,'Steel');
INSERT INTO colours (id,colour) VALUES (69,'Thrianta');
INSERT INTO colours (id,colour) VALUES (70,'Wheaten');
INSERT INTO colours (id,colour) VALUES (71,'Black Fox');
INSERT INTO colours (id,colour) VALUES (72,'Black Otter');
INSERT INTO colours (id,colour) VALUES (73,'Black Tan');
INSERT INTO colours (id,colour) VALUES (74,'Blue Fox');
INSERT INTO colours (id,colour) VALUES (75,'Blue Otter');
INSERT INTO colours (id,colour) VALUES (76,'Blue Tan');
INSERT INTO colours (id,colour) VALUES (77,'Chocolate Fox');
INSERT INTO colours (id,colour) VALUES (78,'Chocolate Otter');
INSERT INTO colours (id,colour) VALUES (79,'Chocolate Tan');
INSERT INTO colours (id,colour) VALUES (80,'Lilac Fox');
INSERT INTO colours (id,colour) VALUES (81,'Lilac Otter');
INSERT INTO colours (id,colour) VALUES (82,'Lilac Tan');
INSERT INTO colours (id,colour) VALUES (83,'Argente Bleu');
INSERT INTO colours (id,colour) VALUES (84,'Argente Brun');
INSERT INTO colours (id,colour) VALUES (85,'Argente Crème');
INSERT INTO colours (id,colour) VALUES (86,'Argente de Champagne');
INSERT INTO colours (id,colour) VALUES (87,'Argente Noir');
INSERT INTO colours (id,colour) VALUES (88,'Meissener');
INSERT INTO colours (id,colour) VALUES (89,'Silver Blue');
INSERT INTO colours (id,colour) VALUES (90,'Silver Brown');
INSERT INTO colours (id,colour) VALUES (91,'Silver Fawn');
INSERT INTO colours (id,colour) VALUES (92,'Silver Grey');
INSERT INTO colours (id,colour) VALUES (93,'Sable dark');
INSERT INTO colours (id,colour) VALUES (94,'Sable light');
INSERT INTO colours (id,colour) VALUES (95,'Marten light');
INSERT INTO colours (id,colour) VALUES (96,'Marten medium');
INSERT INTO colours (id,colour) VALUES (97,'Marten dark');
INSERT INTO colours (id,colour) VALUES (98,'Chocolate');
INSERT INTO colours (id,colour) VALUES (99,'Yellow');
INSERT INTO colours (id,colour) VALUES (100,'Pale Grey');
INSERT INTO colours (id,colour) VALUES (101,'Steel Grey');
INSERT INTO colours (id,colour) VALUES (102,'Tri-colour');
INSERT INTO colours (id,colour) VALUES (103,'Smoke Pearl');
INSERT INTO colours (id,colour) VALUES (104,'Lynx');
INSERT INTO colours (id,colour) VALUES (105,'Tan');
INSERT INTO colours (id,colour) VALUES (106,'Otter Black');
INSERT INTO colours (id,colour) VALUES (107,'Otter Blue');
INSERT INTO colours (id,colour) VALUES (108,'Otter Chocolate');
INSERT INTO colours (id,colour) VALUES (109,'Otter Lilac');
INSERT INTO colours (id,colour) VALUES (110,'Fox Black');
INSERT INTO colours (id,colour) VALUES (111,'Fox Blue');
INSERT INTO colours (id,colour) VALUES (112,'Fox Chocolate');
INSERT INTO colours (id,colour) VALUES (113,'Fox Lilac');
INSERT INTO colours (id,colour) VALUES (116,'Siamese Sable Light');
INSERT INTO colours (id,colour) VALUES (117,'Siamese Smoke Pearl');
INSERT INTO colours (id,colour) VALUES (118,'Marten Sable Light');
INSERT INTO colours (id,colour) VALUES (119,'Marten Sable Medium');
INSERT INTO colours (id,colour) VALUES (120,'Marten Sable Dark');
INSERT INTO colours (id,colour) VALUES (121,'Marten Smoke Pearl');
INSERT INTO colours (id,colour) VALUES (122,'Sable Siamese');
INSERT INTO colours (id,colour) VALUES (123,'Marten Sable');
INSERT INTO colours (id,colour) VALUES (124,'Blue and Tan');
INSERT INTO colours (id,colour) VALUES (125,'Black and Tan');
INSERT INTO colours (id,colour) VALUES (126,'Chocolate and Tan');
INSERT INTO colours (id,colour) VALUES (127,'Lilac and Tan');
INSERT INTO colours (id,colour) VALUES (128,'White');
INSERT INTO colours (id,colour) VALUES (129,'Dark Steel Grey');
INSERT INTO colours (id,colour) VALUES (130,'Normal');
INSERT INTO colours (id,colour) VALUES (131,'Dark Steel');
INSERT INTO colours (id,colour) VALUES (134,'Nutria');
INSERT INTO colours (id,colour) VALUES (135,'Seal Siamese');
INSERT INTO colours (id,colour) VALUES (136,'Fox');
INSERT INTO colours (id,colour) VALUES (137,'Sable Marten');
INSERT INTO colours (id,colour) VALUES (138,'Otter');
INSERT INTO colours (id,colour) VALUES (139,'Harlequin');
INSERT INTO colours (id,colour) VALUES (140,'Himalayan');
INSERT INTO colours (id,colour) VALUES (141,'Silver Seal');
INSERT INTO colours (id,colour) VALUES (142,'Satin Rex');
INSERT INTO colours (id,colour) VALUES (143,'Astrex');
INSERT INTO colours (id,colour) VALUES (144,'Opossum');


-- These two breeds were removed as they are superflous as there is already a Dutch, and English Breed
--INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (6,2,0,1,'Dutch Tri-coloured');
--INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (21,3,0,1,'Tri-coloured English');

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
INSERT INTO breedcolours (breed_id,colour_id) VALUES (5,3);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (5,4);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (5,27);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (5,31);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (5,98);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (5,99);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (5,100);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (5,101);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (5,102);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (6,3);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (6,4);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (6,27);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (6,36);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (6,98);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (6,102);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (11,3);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (11,4);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (11,10);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (11,54);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (11,62);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (11,63);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (11,64);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (11,65);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (12,4);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (12,10);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (12,98);
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
INSERT INTO breedcolours (breed_id,colour_id) VALUES (17,4);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (17,36);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (17,51);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (17,54);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (18,124);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (18,125);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (18,126);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (18,127);
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
INSERT INTO breedcolours (breed_id,colour_id) VALUES (28,3);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (28,4);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (28,10);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (28,54);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (28,128);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (32,3);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (32,4);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (32,31);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (32,38);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (32,128);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (32,129);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (33,4);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (33,10);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (33,98);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (33,130);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (35,3);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (35,28);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (35,38);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (35,40);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (35,61);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (35,99);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (35,131);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (52,4);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (52,9);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (52,10);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (52,32);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (52,34);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (52,54);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (52,104);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (53,3);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (53,4);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (53,10);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (53,54);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (56,18);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (56,19);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (59,3);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (59,4);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (59,28);
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
--INSERT INTO breedcolours (breed_id,colour_id) VALUES (71,145);
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
INSERT INTO breedcolours (breed_id,colour_id) VALUES (74,17);
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

INSERT INTO exhibit_genders (id,gender,gender_class,gender_text) VALUES (1,0,'unknown','undisclosed');
INSERT INTO exhibit_genders (id,gender,gender_class,gender_text) VALUES (2,1,'Bucks','Buck');
INSERT INTO exhibit_genders (id,gender,gender_class,gender_text) VALUES (3,2,'Does','Doe');
INSERT INTO exhibit_genders (id,gender,gender_class,gender_text) VALUES (4,3,'Open','n/a');

INSERT INTO human_genders (id,gender,gender_class,gender_text) VALUES (1,0,'unknown','undisclosed');
INSERT INTO human_genders (id,gender,gender_class,gender_text) VALUES (2,1,'Gents','Gentleman');
INSERT INTO human_genders (id,gender,gender_class,gender_text) VALUES (3,2,'Ladies','Lady');
INSERT INTO human_genders (id,gender,gender_class,gender_text) VALUES (4,3,'Open','Group/Stud');

INSERT INTO human_ages (id,age,age_text) VALUES (1,0,'unknown');
INSERT INTO human_ages (id,age,age_text) VALUES (2,1,'Juvenile');
INSERT INTO human_ages (id,age,age_text) VALUES (3,2,'Adult');
INSERT INTO human_ages (id,age,age_text) VALUES (4,3,'Open');

INSERT INTO exhibit_ages (id,age,age_text) VALUES (1,0,'unknown');
INSERT INTO exhibit_ages (id,age,age_text) VALUES (2,1,'under 14 weeks');
INSERT INTO exhibit_ages (id,age,age_text) VALUES (3,2,'under 4 months');
INSERT INTO exhibit_ages (id,age,age_text) VALUES (4,2,'under 5 months');
INSERT INTO exhibit_ages (id,age,age_text) VALUES (5,3,'Any youngster');
INSERT INTO exhibit_ages (id,age,age_text) VALUES (6,4,'Adult');
INSERT INTO exhibit_ages (id,age,age_text) VALUES (7,7,'Any Age');

INSERT INTO ShowSections (id, section, section_text) VALUES (1,1,'Fancy');
INSERT INTO ShowSections (id, section, section_text) VALUES (2,2,'Lop');
INSERT INTO ShowSections (id, section, section_text) VALUES (3,4,'Fur');
INSERT INTO ShowSections (id, section, section_text) VALUES (4,8,'Rex');
INSERT INTO ShowSections (id, section, section_text) VALUES (5,15,'Open');
INSERT INTO ShowSections (id, section, section_text) VALUES (6,0,'Pet');



