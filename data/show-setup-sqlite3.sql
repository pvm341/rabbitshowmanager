DROP TABLE showclasses cascade;
DROP TABLE exhibitors cascade;
DROP TABLE exhibits cascade;
DROP TABLE entries cascade;
DROP TABLE breedcolours cascade;
DROP TABLE breeds cascade;
DROP TABLE colours cascade;
DROP TABLE exhibit_genders cascade;
DROP TABLE human_genders cascade;
DROP TABLE exhibit_ages cascade;
DROP TABLE human_ages cascade;
DROP TABLE showsections cascade;
DROP TABLE judges cascade;
DROP TABLE classcolours;

-- colours TABLE
CREATE TABLE colours (
id INTEGER,
colour VARCHAR(35)UNIQUE,
PRIMARY KEY (id)
);

-- Breeds TABLE 
CREATE TABLE breeds(
id INTEGER,
adult_age INTEGER,
top_pen_req INTEGER,
section INTEGER,
breed VARCHAR(30) UNIQUE,
PRIMARY KEY (id)
);

-- breed colour TABLE to break 2x 1:M 
CREATE TABLE breedcolours (
breed_id INTEGER,
colour_id INTEGER,
PRIMARY KEY (breed_id, colour_id),
FOREIGN KEY (breed_id) REFERENCES breeds(id),
FOREIGN KEY (colour_id) REFERENCES colours(id)
);

-- exhibit genders TABLE 
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

-- human genders TABLE
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

-- exhibit ages TABLE 
-- this could be coded in the application but for simplicity included in the database
-- as some classes need this information and to make changes to exhibit animal easier
-- it is currently for rabbits, change to cavy aka Guinea pig CREATE TABLE exhibit_ages(
CREATE TABLE exhibit_ages(
id INTEGER,
age INTEGER,
age_text VARCHAR(15),
PRIMARY KEY (id)
);

-- human ages TABLE
-- this could be coded in the application but for simplicity included in the database
-- as some classes need this information
CREATE TABLE human_ages(
id INTEGER,
age INTEGER,
age_text VARCHAR(15),
PRIMARY KEY (id)
);
-- Show Sections TABLE 
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
id AUTOINCREMENT, 
name VARCHAR(20),   
address VARCHAR(200),
phone VARCHAR(16),
email VARCHAR(100),
booked_in INTEGER,
booked_out INTEGER,
paid_fees INTEGER,
paid_prizes INTEGER,
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
FOREIGN KEY (exhibitor_id) REFERENCES exhibitors(id)
);

-- Show Classes
CREATE TABLE showclasses ( 
class_no INTEGER, 
name VARCHAR(40),
breed INTEGER,
colour INTEGER,
breed_class INTEGER, 
section INTEGER, 
members_only INTEGER, 
breeders_only INTEGER, 
upsidedown INTEGER,
exhibit_age INTEGER, 
exhibit_gender INTEGER, 
exhibitor_age INTEGER, 
exhibitor_gender INTEGER, 
result_1 INTEGER, 
result_2 INTEGER,  
result_3 INTEGER,  
result_4 INTEGER,  
result_5 INTEGER,   
result_6 INTEGER,  
result_7 INTEGER,
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
PRIMARY KEY (class_no),
UNIQUE (class_no, colour_id),
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

INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (1,3,1,1,'Angora');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (2,3,0,1,'Black Hare');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (3,2,0,1,'Dutch');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (4,3,0,1,'English');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (5,3,0,1,'Flemish Giant');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (6,3,0,1,'Giant Papillon');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (7,3,0,1,'Hare Belgian');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (8,3,0,1,'Hare Tan');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (9,3,0,1,'Harlequin');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (10,3,0,1,'Himalayan');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (11,3,0,1,'Lionhead');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (12,3,0,1,'Netherland Dwarf');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (13,3,0,1,'Polish');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (14,3,0,1,'Rhinelander');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (15,3,0,1,'Silver');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (16,3,0,1,'Tan');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (17,3,0,1,'Thrianta');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (18,3,0,8,'Rex');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (19,3,0,4,'Alaska');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (20,3,0,4,'Argente Bleu');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (21,3,0,4,'Argente Brun');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (22,3,0,4,'Argente Crème');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (23,3,0,4,'Argente de Champagne');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (24,3,0,4,'Argente Noir');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (25,3,0,4,'Beige');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (26,3,0,4,'Beveren');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (27,3,0,4,'Blanc de Bouscat');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (28,3,0,4,'Blanc de Hotot');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (29,3,0,4,'Blanc de Termonde');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (30,3,0,4,'British Giant');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (31,3,0,4,'Californian');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (32,3,0,4,'Chinchilla');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (33,3,0,4,'Chinchilla Giganta');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (34,3,0,4,'Continental Giant coloured');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (35,3,0,4,'Continental Giant white');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (36,3,0,4,'Deilenaar');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (37,3,0,4,'Fox - Silver');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (38,3,0,4,'Golden Glavcot');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (39,3,0,4,'Havana');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (40,3,0,4,'Hulstlander');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (41,3,0,4,'Lilac');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (42,3,0,4,'New Zealand White');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (43,3,0,4,'New Zealand Black');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (44,3,0,4,'New Zealand Blue');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (45,3,0,4,'New Zealand Red');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (46,3,0,4,'Perlfee');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (47,3,0,4,'Pointed Beveren');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (48,3,0,4,'Siamese Sable - Marten Sable');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (49,3,0,4,'Sallander');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (50,3,0,4,'Satin');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (51,3,0,4,'Siberian');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (52,3,0,4,'Smoke Pearl');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (53,3,0,4,'Squirrel');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (54,3,0,4,'Sussex');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (55,3,0,4,'Swiss Fox');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (56,3,0,4,'Thuringer');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (57,3,0,4,'Vienna Coloured');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (58,3,0,4,'Vienna White');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (59,3,0,4,'Wheaten');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (60,3,0,4,'Wheaten Lynx');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (61,3,0,4,'Fauve de Bourgogne');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (62,3,0,4,'Argente St Hubert');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (63,3,0,4,'Miniature Satin');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (64,3,0,8,'Mini Rex');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (65,3,0,2,'Cashmere Lop');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (66,3,0,2,'Miniature Cashmere Lop');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (67,3,0,2,'Dwarf Lop');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (68,3,0,2,'English Lop');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (69,3,0,2,'French Lop');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (70,3,0,2,'German Lop');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (71,3,0,2,'Meissner Lop');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (72,3,0,2,'Miniature Lop');
INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (73,3,0,2,'Miniature Lion Lop');

INSERT INTO colours (id,colour) VALUES (1,'Black');
INSERT INTO colours (id,colour) VALUES (2,'Blue');
INSERT INTO colours (id,colour) VALUES (3,'Blue Eyed White');
INSERT INTO colours (id,colour) VALUES (4,'Chocolate Brown');
INSERT INTO colours (id,colour) VALUES (5,'Havana');
INSERT INTO colours (id,colour) VALUES (6,'Ermine');
INSERT INTO colours (id,colour) VALUES (7,'Ivory');
INSERT INTO colours (id,colour) VALUES (8,'Lilac');
INSERT INTO colours (id,colour) VALUES (9,'Red Eyed White');
INSERT INTO colours (id,colour) VALUES (10,'Smoke');
INSERT INTO colours (id,colour) VALUES (11,'Beige-Isabella');
INSERT INTO colours (id,colour) VALUES (12,'Blue Cream');
INSERT INTO colours (id,colour) VALUES (13,'Bluepoint');
INSERT INTO colours (id,colour) VALUES (14,'Bronze');
INSERT INTO colours (id,colour) VALUES (15,'Chocolate Tortoiseshell');
INSERT INTO colours (id,colour) VALUES (16,'Cream');
INSERT INTO colours (id,colour) VALUES (17,'Gold');
INSERT INTO colours (id,colour) VALUES (18,'Iron Grey');
INSERT INTO colours (id,colour) VALUES (19,'Sealpoint');
INSERT INTO colours (id,colour) VALUES (20,'Siamese Sable Dark');
INSERT INTO colours (id,colour) VALUES (21,'Siamese Sable Medium');
INSERT INTO colours (id,colour) VALUES (22,'Siamese Smoke Dark');
INSERT INTO colours (id,colour) VALUES (23,'Siamese Smoke Medium');
INSERT INTO colours (id,colour) VALUES (24,'Sooty-Fawn');
INSERT INTO colours (id,colour) VALUES (25,'Tortoiseshell');
INSERT INTO colours (id,colour) VALUES (26,'Agouti');
INSERT INTO colours (id,colour) VALUES (27,'Belgium Hare');
INSERT INTO colours (id,colour) VALUES (28,'Blue Grey');
INSERT INTO colours (id,colour) VALUES (29,'Brown Grey');
INSERT INTO colours (id,colour) VALUES (30,'Castor');
INSERT INTO colours (id,colour) VALUES (31,'Chinchilla');
INSERT INTO colours (id,colour) VALUES (32,'Cinnamon');
INSERT INTO colours (id,colour) VALUES (33,'Flemish');
INSERT INTO colours (id,colour) VALUES (34,'Grey');
INSERT INTO colours (id,colour) VALUES (35,'Lynx-Wheaten Lynx');
INSERT INTO colours (id,colour) VALUES (36,'Opal');
INSERT INTO colours (id,colour) VALUES (37,'Perlfee');
INSERT INTO colours (id,colour) VALUES (38,'Red Agouti');
INSERT INTO colours (id,colour) VALUES (39,'Sandy');
INSERT INTO colours (id,colour) VALUES (40,'Squirrel');
INSERT INTO colours (id,colour) VALUES (41,'Broken');
INSERT INTO colours (id,colour) VALUES (42,'Butterfly');
INSERT INTO colours (id,colour) VALUES (43,'Dalmation');
INSERT INTO colours (id,colour) VALUES (44,'Dutch');
INSERT INTO colours (id,colour) VALUES (45,'English');
INSERT INTO colours (id,colour) VALUES (46,'Hotot');
INSERT INTO colours (id,colour) VALUES (47,'Papillon');
INSERT INTO colours (id,colour) VALUES (48,'Rhinelander');
INSERT INTO colours (id,colour) VALUES (49,'Fawn');
INSERT INTO colours (id,colour) VALUES (50,'Golden Glavcot');
INSERT INTO colours (id,colour) VALUES (52,'Brown');
INSERT INTO colours (id,colour) VALUES (55,'Himalayan Black');
INSERT INTO colours (id,colour) VALUES (56,'Himalayan Blue');
INSERT INTO colours (id,colour) VALUES (57,'Himalayan Chocolate');
INSERT INTO colours (id,colour) VALUES (58,'Himalayan Lilac');
INSERT INTO colours (id,colour) VALUES (59,'Light Steel');
INSERT INTO colours (id,colour) VALUES (60,'Magpie Black');
INSERT INTO colours (id,colour) VALUES (61,'Magpie Brown');
INSERT INTO colours (id,colour) VALUES (62,'Magpie Blue');
INSERT INTO colours (id,colour) VALUES (63,'Magpie Lilac');
INSERT INTO colours (id,colour) VALUES (64,'Orange');
INSERT INTO colours (id,colour) VALUES (65,'Red');
INSERT INTO colours (id,colour) VALUES (66,'Steel');
INSERT INTO colours (id,colour) VALUES (67,'Thrianta');
INSERT INTO colours (id,colour) VALUES (68,'Wheaten');
INSERT INTO colours (id,colour) VALUES (69,'Black Fox');
INSERT INTO colours (id,colour) VALUES (70,'Black Otter');
INSERT INTO colours (id,colour) VALUES (71,'Black Tan');
INSERT INTO colours (id,colour) VALUES (72,'Blue Fox');
INSERT INTO colours (id,colour) VALUES (73,'Blue Otter');
INSERT INTO colours (id,colour) VALUES (74,'Blue Tan');
INSERT INTO colours (id,colour) VALUES (75,'Chocolate Fox');
INSERT INTO colours (id,colour) VALUES (76,'Chocolate Otter');
INSERT INTO colours (id,colour) VALUES (77,'Chocolate Tan');
INSERT INTO colours (id,colour) VALUES (78,'Lilac Fox');
INSERT INTO colours (id,colour) VALUES (79,'Lilac Otter');
INSERT INTO colours (id,colour) VALUES (80,'Lilac Tan');
INSERT INTO colours (id,colour) VALUES (81,'Argente Bleu');
INSERT INTO colours (id,colour) VALUES (82,'Argente Brun');
INSERT INTO colours (id,colour) VALUES (83,'Argente Crème');
INSERT INTO colours (id,colour) VALUES (84,'Argente de Champagne');
INSERT INTO colours (id,colour) VALUES (85,'Argente Noir');
INSERT INTO colours (id,colour) VALUES (86,'Meissener');
INSERT INTO colours (id,colour) VALUES (87,'Silver Blue');
INSERT INTO colours (id,colour) VALUES (88,'Silver Brown');
INSERT INTO colours (id,colour) VALUES (89,'Silver Fawn');
INSERT INTO colours (id,colour) VALUES (90,'Silver Grey');
INSERT INTO colours (id,colour) VALUES (91,'Sable dark');
INSERT INTO colours (id,colour) VALUES (92,'Sable light');
INSERT INTO colours (id,colour) VALUES (93,'Marten light');
INSERT INTO colours (id,colour) VALUES (94,'Marten medium');
INSERT INTO colours (id,colour) VALUES (95,'Marten dark');
INSERT INTO colours (id,colour) VALUES (96,'Chocolate');
INSERT INTO colours (id,colour) VALUES (97,'Yellow');
INSERT INTO colours (id,colour) VALUES (98,'Pale Grey');
INSERT INTO colours (id,colour) VALUES (99,'Steel Grey');
INSERT INTO colours (id,colour) VALUES (100,'Tri-colour');
INSERT INTO colours (id,colour) VALUES (101,'Smoke Pearl');
INSERT INTO colours (id,colour) VALUES (102,'Lynx');
INSERT INTO colours (id,colour) VALUES (103,'Tan');
INSERT INTO colours (id,colour) VALUES (104,'Otter Black');
INSERT INTO colours (id,colour) VALUES (105,'Otter Blue');
INSERT INTO colours (id,colour) VALUES (106,'Otter Chocolate');
INSERT INTO colours (id,colour) VALUES (107,'Otter Lilac');
INSERT INTO colours (id,colour) VALUES (108,'Fox Black');
INSERT INTO colours (id,colour) VALUES (109,'Fox Blue');
INSERT INTO colours (id,colour) VALUES (110,'Fox Chocolate');
INSERT INTO colours (id,colour) VALUES (111,'Fox Lilac');
INSERT INTO colours (id,colour) VALUES (114,'Siamese Sable Light');
INSERT INTO colours (id,colour) VALUES (115,'Siamese Smoke Pearl');
INSERT INTO colours (id,colour) VALUES (116,'Marten Sable Light');
INSERT INTO colours (id,colour) VALUES (117,'Marten Sable Medium');
INSERT INTO colours (id,colour) VALUES (118,'Marten Sable Dark');
INSERT INTO colours (id,colour) VALUES (119,'Marten Smoke Pearl');
INSERT INTO colours (id,colour) VALUES (120,'Sable Siamese');
INSERT INTO colours (id,colour) VALUES (121,'Marten Sable');
INSERT INTO colours (id,colour) VALUES (122,'Blue and Tan');
INSERT INTO colours (id,colour) VALUES (123,'Black and Tan');
INSERT INTO colours (id,colour) VALUES (124,'Chocolate and Tan');
INSERT INTO colours (id,colour) VALUES (125,'Lilac and Tan');
INSERT INTO colours (id,colour) VALUES (126,'White');
INSERT INTO colours (id,colour) VALUES (127,'Dark Steel Grey');
INSERT INTO colours (id,colour) VALUES (128,'Normal');
INSERT INTO colours (id,colour) VALUES (129,'Dark Steel');
INSERT INTO colours (id,colour) VALUES (132,'Nutria');
INSERT INTO colours (id,colour) VALUES (133,'Seal Siamese');
INSERT INTO colours (id,colour) VALUES (134,'Fox');
INSERT INTO colours (id,colour) VALUES (135,'Sable Marten');
INSERT INTO colours (id,colour) VALUES (136,'Otter');
INSERT INTO colours (id,colour) VALUES (137,'Harlequin');
INSERT INTO colours (id,colour) VALUES (138,'Himalayan');
INSERT INTO colours (id,colour) VALUES (139,'Silver Seal');
INSERT INTO colours (id,colour) VALUES (140,'Satin Rex');
INSERT INTO colours (id,colour) VALUES (141,'Astrex');
INSERT INTO colours (id,colour) VALUES (142,'Opossum');


-- These two breeds were removed as they are superflous as there is already a Dutch, and English Breed
--INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (4,2,0,1,'Dutch Tri-coloured');
--INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (19,3,0,1,'Tri-coloured English');

INSERT INTO breedcolours (breed_id,colour_id) VALUES (1,2);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (1,10);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (1,12);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (1,16);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (1,17);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (1,28);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (1,29);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (1,31);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (1,32);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (1,91);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (1,92);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (1,93);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (1,94);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (1,95);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (1,96);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (3,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (3,2);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (3,25);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (3,29);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (3,96);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (3,97);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (3,98);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (3,99);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (3,100);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (4,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (4,2);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (4,25);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (4,34);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (4,96);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (4,100);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (9,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (9,2);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (9,8);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (9,52);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (9,60);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (9,61);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (9,62);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (9,63);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (10,2);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (10,8);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (10,96);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (12,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (12,2);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (12,3);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (12,8);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (12,9);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (12,19);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (12,21);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (12,22);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (12,23);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (12,25);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (12,26);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (12,31);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (12,36);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (12,40);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (12,49);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (12,52);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (12,55);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (12,56);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (12,57);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (12,58);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (12,64);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (12,66);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (12,101);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (12,102);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (12,103);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (12,104);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (12,105);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (12,106);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (12,107);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (12,108);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (12,109);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (12,110);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (12,111);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (12,114);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (12,115);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (12,116);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (12,117);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (12,118);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (12,119);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (12,122);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (12,123);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (12,124);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (12,125);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (13,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (13,3);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (13,8);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (13,9);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (13,25);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (13,26);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (13,31);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (13,32);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (13,36);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (13,38);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (13,40);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (13,49);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (13,52);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (13,55);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (13,56);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (13,57);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (13,58);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (13,64);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (13,66);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (13,102);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (13,104);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (13,105);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (13,106);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (13,107);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (13,108);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (13,109);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (13,110);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (13,111);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (13,115);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (13,119);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (13,120);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (13,121);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (13,122);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (13,123);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (13,124);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (13,125);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (15,2);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (15,34);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (15,49);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (15,52);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (16,122);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (16,123);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (16,124);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (16,125);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (18,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (18,2);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (18,5);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (18,6);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (18,8);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (18,20);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (18,21);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (18,25);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (18,30);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (18,31);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (18,32);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (18,36);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (18,43);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (18,49);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (18,64);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (18,101);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (18,102);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (18,103);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (18,114);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (18,119);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (18,132);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (18,133);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (18,134);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (18,135);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (18,136);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (18,137);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (18,138);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (18,139);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (18,140);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (18,141);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (26,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (26,2);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (26,8);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (26,52);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (26,126);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (30,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (30,2);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (30,29);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (30,36);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (30,126);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (30,127);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (31,2);
INSERT INTO breedcolours (breed_id,colour_id) VALUESgit rm  (31,8);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (31,96);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (31,128);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (33,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (33,26);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (33,36);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (33,38);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (33,59);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (33,97);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (33,129);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (50,2);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (50,7);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (50,8);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (50,30);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (50,32);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (50,52);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (50,102);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (51,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (51,2);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (51,8);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (51,52);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (54,16);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (54,17);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (57,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (57,2);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (57,26);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (64,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (64,2);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (64,5);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (64,6);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (64,8);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (64,20);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (64,21);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (64,25);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (64,30);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (64,31);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (64,32);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (64,36);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (64,43);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (64,49);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (64,64);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (64,101);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (64,102);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (64,103);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (64,114);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (64,119);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (64,132);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (64,133);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (64,134);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (64,135);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (64,136);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (64,137);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (64,138);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (64,139);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (64,140);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (64,141);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (67,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (67,2);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (67,19);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (67,20);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (67,21);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (67,24);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (67,26);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (67,31);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (67,36);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (67,42);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (67,49);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (67,52);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (67,64);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (67,66);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (67,69);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (67,72);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (67,75);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (67,114);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (67,126);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (67,135);
--INSERT INTO breedcolours (breed_id,colour_id) VALUES (69,143);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (69,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (69,2);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (69,20);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (69,21);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (69,26);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (69,31);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (69,36);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (69,42);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (69,49);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (69,52);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (69,66);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (69,114);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (69,126);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (72,15);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (73,1);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (73,2);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (73,11);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (73,13);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (73,18);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (73,19);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (73,20);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (73,21);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (73,24);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (73,26);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (73,32);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (73,36);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (73,42);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (73,49);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (73,64);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (73,66);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (73,72);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (73,73);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (73,96);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (73,114);
INSERT INTO breedcolours (breed_id,colour_id) VALUES (73,126);
--INSERT INTO breedcolours (breed_id,colour_id) VALUES (73,143);

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


