drop table showclasses cascade;
drop table exhibitors cascade;
drop table exhibits cascade;
drop table entries cascade;
drop table breedcolours cascade;
drop table breeds cascade;
drop table colours cascade;
drop table exhibit_genders cascade;
drop table human_genders cascade;
drop table exhibit_ages cascade;
drop table human_ages cascade;
drop table showsections cascade;
drop table judges cascade;
drop table classcolours;

-- colours table
create table colours (
id integer,
colour varchar(35)unique,
primary key (id)
);

-- Breeds table 
create table breeds(
id integer,
adult_age integer,
top_pen_req integer,
section integer,
breed varchar(30) unique,
primary key (id)
);

-- breed colour table to break 2x 1:M 
create table breedcolours (
breed_id integer,
colour_id integer,
primary key (breed_id, colour_id),
foreign key (breed_id) references breeds(id),
foreign key (colour_id) references colours(id)
);

-- exhibit genders table 
-- this could be coded in the application but for simplicity included in the database
-- as some classes need this information and to make changes to a exhibit animal easier
-- it is currently for rabbits, change to cavy aka Guinea pig 
create table exhibit_genders(
id integer,
gender integer,
gender_text varchar(10),
primary key (id)
);

-- human genders table
-- this could be coded in the application but for simplicity included in the database
-- as some classes need this information
create table human_genders(
id integer,
exhibitor integer,
gender integer,
gender_text varchar(10),
primary key (id)
);

-- exhibit ages table 
-- this could be coded in the application but for simplicity included in the database
-- as some classes need this information and to make changes to exhibit animal easier
-- it is currently for rabbits, change to cavy aka Guinea pig create table exhibit_ages(
create table exhibit_ages(
id integer,
age integer,
age_text varchar(15),
primary key (id)
);

-- human ages table
-- this could be coded in the application but for simplicity included in the database
-- as some classes need this information
create table human_ages(
id integer,
age integer,
age_text varchar(15),
primary key (id)
);
-- Show Sections table 
-- this could be coded in the application but for simplicity included in the database
-- as some classes need this information
create table showsections(
id integer unique,
section integer,
section_text varchar(5),
primary key (id)
);

create table judges(
id integer,
name varchar(40),
sections integer,
primary key (id),
foreign key(sections) references showsections(id)
);

create table exhibitors (
id serial, 
name varchar(20),   
address varchar(200),
phone varchar(16),
email varchar(100),
entry_fees real,
prize_money real,
club_member integer,
age_group integer,
gender integer,
primary key (id)
);

create table exhibits (
pen_no integer,
ring_number varchar(15) unique,  
breed_class integer,
gender integer,
age_group integer,
breed_by_exhibitor integer,
exhibitor_id integer, 
primary key(pen_no),
foreign key (exhibitor_id) references Exhibitors(id)
);

-- Show Classes
create table showclasses ( 
class_no integer, 
name varchar(40),
breed integer,
colour integer,
breed_class integer, 
section integer, 
members_only integer, 
breeders_only integer, 
upsidedown integer,
exhibit_age integer, 
exhibit_gender integer, 
exhibitor_age integer, 
exhibitor_gender integer, 
result_1 integer, 
result_2 integer,  
result_3 integer,  
result_4 integer,  
result_5 integer,   
result_6 integer,  
result_7 integer,
primary key (class_no), 
foreign key (result_1) references exhibits(pen_no),
foreign key (result_2) references exhibits(pen_no),
foreign key (result_3) references exhibits(pen_no),
foreign key (result_4) references exhibits(pen_no),
foreign key (result_5) references exhibits(pen_no),
foreign key (result_6) references exhibits(pen_no),
foreign key (result_7) references exhibits(pen_no),
foreign key (breed) references breeds(id),
foreign key (colour) references colours(id),
foreign key (section) references ShowSections(id),
foreign key (exhibit_age) references exhibit_ages(id),
foreign key (exhibit_gender) references exhibit_genders(id),
foreign key (exhibitor_age) references human_ages(id),
foreign key (exhibitor_gender) references human_genders(id)
);
create table classcolours (
class_no int,
colour_id int,
primary key (class_no, colour_id),
foreign key (class_no) references showclasses (class_no),
foreign key (colour_id) references colours(id)
);

create table entries(
class_no integer,
pen_no integer,
unique (class_no, pen_no),
primary key (class_no, pen_no), 
foreign key (class_no) references ShowClasses(class_no),
foreign key (pen_no) references exhibits(pen_no)
);


insert into breeds (id,adult_age,top_pen_req,section,breed) values (1,3,1,1,'Angora');
insert into breeds (id,adult_age,top_pen_req,section,breed) values (2,3,0,1,'Black Hare');
insert into breeds (id,adult_age,top_pen_req,section,breed) values (3,2,0,1,'Dutch');
insert into breeds (id,adult_age,top_pen_req,section,breed) values (5,3,0,1,'English');
insert into breeds (id,adult_age,top_pen_req,section,breed) values (6,3,0,1,'Flemish Giant');
insert into breeds (id,adult_age,top_pen_req,section,breed) values (7,3,0,1,'Giant Papillon');
insert into breeds (id,adult_age,top_pen_req,section,breed) values (8,3,0,1,'Hare Belgian');
insert into breeds (id,adult_age,top_pen_req,section,breed) values (9,3,0,1,'Hare Tan');
insert into breeds (id,adult_age,top_pen_req,section,breed) values (10,3,0,1,'Harlequin');
insert into breeds (id,adult_age,top_pen_req,section,breed) values (11,3,0,1,'Himalayan');
insert into breeds (id,adult_age,top_pen_req,section,breed) values (12,3,0,1,'Lionhead');
insert into breeds (id,adult_age,top_pen_req,section,breed) values (13,3,0,1,'Netherland Dwarf');
insert into breeds (id,adult_age,top_pen_req,section,breed) values (14,3,0,1,'Polish');
insert into breeds (id,adult_age,top_pen_req,section,breed) values (15,3,0,1,'Rhinelander');
insert into breeds (id,adult_age,top_pen_req,section,breed) values (16,3,0,1,'Silver');
insert into breeds (id,adult_age,top_pen_req,section,breed) values (17,3,0,1,'Tan');
insert into breeds (id,adult_age,top_pen_req,section,breed) values (18,3,0,1,'Thrianta');
insert into breeds (id,adult_age,top_pen_req,section,breed) values (20,3,0,8,'Rex');
insert into breeds (id,adult_age,top_pen_req,section,breed) values (21,3,0,4,'Alaska');
insert into breeds (id,adult_age,top_pen_req,section,breed) values (22,3,0,4,'Argente Bleu');
insert into breeds (id,adult_age,top_pen_req,section,breed) values (23,3,0,4,'Argente Brun');
insert into breeds (id,adult_age,top_pen_req,section,breed) values (24,3,0,4,'Argente Crème');
insert into breeds (id,adult_age,top_pen_req,section,breed) values (25,3,0,4,'Argente de Champagne');
insert into breeds (id,adult_age,top_pen_req,section,breed) values (26,3,0,4,'Argente Noir');
insert into breeds (id,adult_age,top_pen_req,section,breed) values (27,3,0,4,'Beige');
insert into breeds (id,adult_age,top_pen_req,section,breed) values (28,3,0,4,'Beveren');
insert into breeds (id,adult_age,top_pen_req,section,breed) values (29,3,0,4,'Blanc de Bouscat');
insert into breeds (id,adult_age,top_pen_req,section,breed) values (30,3,0,4,'Blanc de Hotot');
insert into breeds (id,adult_age,top_pen_req,section,breed) values (31,3,0,4,'Blanc de Termonde');
insert into breeds (id,adult_age,top_pen_req,section,breed) values (32,3,0,4,'British Giant');
insert into breeds (id,adult_age,top_pen_req,section,breed) values (33,3,0,4,'Californian');
insert into breeds (id,adult_age,top_pen_req,section,breed) values (34,3,0,4,'Chinchilla');
insert into breeds (id,adult_age,top_pen_req,section,breed) values (35,3,0,4,'Chinchilla Giganta');
insert into breeds (id,adult_age,top_pen_req,section,breed) values (36,3,0,4,'Continental Giant coloured');
insert into breeds (id,adult_age,top_pen_req,section,breed) values (37,3,0,4,'Continental Giant white');
insert into breeds (id,adult_age,top_pen_req,section,breed) values (38,3,0,4,'Deilenaar');
insert into breeds (id,adult_age,top_pen_req,section,breed) values (39,3,0,4,'Fox - Silver');
insert into breeds (id,adult_age,top_pen_req,section,breed) values (40,3,0,4,'Golden Glavcot');
insert into breeds (id,adult_age,top_pen_req,section,breed) values (41,3,0,4,'Havana');
insert into breeds (id,adult_age,top_pen_req,section,breed) values (42,3,0,4,'Hulstlander');
insert into breeds (id,adult_age,top_pen_req,section,breed) values (43,3,0,4,'Lilac');
insert into breeds (id,adult_age,top_pen_req,section,breed) values (44,3,0,4,'New Zealand White');
insert into breeds (id,adult_age,top_pen_req,section,breed) values (45,3,0,4,'New Zealand Black');
insert into breeds (id,adult_age,top_pen_req,section,breed) values (46,3,0,4,'New Zealand Blue');
insert into breeds (id,adult_age,top_pen_req,section,breed) values (47,3,0,4,'New Zealand Red');
insert into breeds (id,adult_age,top_pen_req,section,breed) values (48,3,0,4,'Perlfee');
insert into breeds (id,adult_age,top_pen_req,section,breed) values (49,3,0,4,'Pointed Beveren');
insert into breeds (id,adult_age,top_pen_req,section,breed) values (50,3,0,4,'Siamese Sable - Marten Sable');
insert into breeds (id,adult_age,top_pen_req,section,breed) values (51,3,0,4,'Sallander');
insert into breeds (id,adult_age,top_pen_req,section,breed) values (52,3,0,4,'Satin');
insert into breeds (id,adult_age,top_pen_req,section,breed) values (53,3,0,4,'Siberian');
insert into breeds (id,adult_age,top_pen_req,section,breed) values (54,3,0,4,'Smoke Pearl');
insert into breeds (id,adult_age,top_pen_req,section,breed) values (55,3,0,4,'Squirrel');
insert into breeds (id,adult_age,top_pen_req,section,breed) values (56,3,0,4,'Sussex');
insert into breeds (id,adult_age,top_pen_req,section,breed) values (57,3,0,4,'Swiss Fox');
insert into breeds (id,adult_age,top_pen_req,section,breed) values (58,3,0,4,'Thuringer');
insert into breeds (id,adult_age,top_pen_req,section,breed) values (59,3,0,4,'Vienna Coloured');
insert into breeds (id,adult_age,top_pen_req,section,breed) values (60,3,0,4,'Vienna White');
insert into breeds (id,adult_age,top_pen_req,section,breed) values (61,3,0,4,'Wheaten');
insert into breeds (id,adult_age,top_pen_req,section,breed) values (62,3,0,4,'Wheaten Lynx');
insert into breeds (id,adult_age,top_pen_req,section,breed) values (63,3,0,4,'Fauve de Bourgogne');
insert into breeds (id,adult_age,top_pen_req,section,breed) values (64,3,0,4,'Argente St Hubert');
insert into breeds (id,adult_age,top_pen_req,section,breed) values (65,3,0,4,'Miniature Satin');
insert into breeds (id,adult_age,top_pen_req,section,breed) values (66,3,0,8,'Mini Rex');
insert into breeds (id,adult_age,top_pen_req,section,breed) values (67,3,0,2,'Cashmere Lop');
insert into breeds (id,adult_age,top_pen_req,section,breed) values (68,3,0,2,'Miniature Cashmere Lop');
insert into breeds (id,adult_age,top_pen_req,section,breed) values (69,3,0,2,'Dwarf Lop');
insert into breeds (id,adult_age,top_pen_req,section,breed) values (70,3,0,2,'English Lop');
insert into breeds (id,adult_age,top_pen_req,section,breed) values (71,3,0,2,'French Lop');
insert into breeds (id,adult_age,top_pen_req,section,breed) values (72,3,0,2,'German Lop');
insert into breeds (id,adult_age,top_pen_req,section,breed) values (73,3,0,2,'Meissner Lop');
insert into breeds (id,adult_age,top_pen_req,section,breed) values (74,3,0,2,'Miniature Lop');
insert into breeds (id,adult_age,top_pen_req,section,breed) values (75,3,0,2,'Miniature Lion Lop');

insert into colours (id,colour) values (1,'Black');
insert into colours (id,colour) values (2,'Blue');
insert into colours (id,colour) values (3,'Blue Eyed White');
insert into colours (id,colour) values (4,'Chocolate Brown');
insert into colours (id,colour) values (5,'Havana');
insert into colours (id,colour) values (6,'Ermine');
insert into colours (id,colour) values (7,'Ivory');
insert into colours (id,colour) values (8,'Lilac');
insert into colours (id,colour) values (9,'Red Eyed White');
insert into colours (id,colour) values (10,'Smoke');
insert into colours (id,colour) values (11,'Beige-Isabella');
insert into colours (id,colour) values (12,'Blue Cream');
insert into colours (id,colour) values (13,'Bluepoint');
insert into colours (id,colour) values (14,'Bronze');
insert into colours (id,colour) values (15,'Chocolate Tortoiseshell');
insert into colours (id,colour) values (16,'Cream');
insert into colours (id,colour) values (17,'Gold');
insert into colours (id,colour) values (18,'Iron Grey');
insert into colours (id,colour) values (19,'Sealpoint');
insert into colours (id,colour) values (20,'Siamese Sable Dark');
insert into colours (id,colour) values (21,'Siamese Sable Medium');
insert into colours (id,colour) values (22,'Siamese Smoke Dark');
insert into colours (id,colour) values (23,'Siamese Smoke Medium');
insert into colours (id,colour) values (24,'Sooty-Fawn');
insert into colours (id,colour) values (25,'Tortoiseshell');
insert into colours (id,colour) values (26,'Agouti');
insert into colours (id,colour) values (27,'Belgium Hare');
insert into colours (id,colour) values (28,'Blue Grey');
insert into colours (id,colour) values (29,'Brown Grey');
insert into colours (id,colour) values (30,'Castor');
insert into colours (id,colour) values (31,'Chinchilla');
insert into colours (id,colour) values (32,'Cinnamon');
insert into colours (id,colour) values (33,'Flemish');
insert into colours (id,colour) values (34,'Grey');
insert into colours (id,colour) values (35,'Lynx-Wheaten Lynx');
insert into colours (id,colour) values (36,'Opal');
insert into colours (id,colour) values (37,'Perlfee');
insert into colours (id,colour) values (38,'Red Agouti');
insert into colours (id,colour) values (39,'Sandy');
insert into colours (id,colour) values (40,'Squirrel');
insert into colours (id,colour) values (41,'Broken');
insert into colours (id,colour) values (42,'Butterfly');
insert into colours (id,colour) values (43,'Dalmation');
insert into colours (id,colour) values (44,'Dutch');
insert into colours (id,colour) values (45,'English');
insert into colours (id,colour) values (46,'Hotot');
insert into colours (id,colour) values (47,'Papillon');
insert into colours (id,colour) values (48,'Rhinelander');
insert into colours (id,colour) values (49,'Fawn');
insert into colours (id,colour) values (50,'Golden Glavcot');
insert into colours (id,colour) values (52,'Brown');
insert into colours (id,colour) values (55,'Himalayan Black');
insert into colours (id,colour) values (56,'Himalayan Blue');
insert into colours (id,colour) values (57,'Himalayan Chocolate');
insert into colours (id,colour) values (58,'Himalayan Lilac');
insert into colours (id,colour) values (59,'Light Steel');
insert into colours (id,colour) values (60,'Magpie Black');
insert into colours (id,colour) values (61,'Magpie Brown');
insert into colours (id,colour) values (62,'Magpie Blue');
insert into colours (id,colour) values (63,'Magpie Lilac');
insert into colours (id,colour) values (64,'Orange');
insert into colours (id,colour) values (65,'Red');
insert into colours (id,colour) values (66,'Steel');
insert into colours (id,colour) values (67,'Thrianta');
insert into colours (id,colour) values (68,'Wheaten');
insert into colours (id,colour) values (69,'Black Fox');
insert into colours (id,colour) values (70,'Black Otter');
insert into colours (id,colour) values (71,'Black Tan');
insert into colours (id,colour) values (72,'Blue Fox');
insert into colours (id,colour) values (73,'Blue Otter');
insert into colours (id,colour) values (74,'Blue Tan');
insert into colours (id,colour) values (75,'Chocolate Fox');
insert into colours (id,colour) values (76,'Chocolate Otter');
insert into colours (id,colour) values (77,'Chocolate Tan');
insert into colours (id,colour) values (78,'Lilac Fox');
insert into colours (id,colour) values (79,'Lilac Otter');
insert into colours (id,colour) values (80,'Lilac Tan');
insert into colours (id,colour) values (81,'Argente Bleu');
insert into colours (id,colour) values (82,'Argente Brun');
insert into colours (id,colour) values (83,'Argente Crème');
insert into colours (id,colour) values (84,'Argente de Champagne');
insert into colours (id,colour) values (85,'Argente Noir');
insert into colours (id,colour) values (86,'Meissener');
insert into colours (id,colour) values (87,'Silver Blue');
insert into colours (id,colour) values (88,'Silver Brown');
insert into colours (id,colour) values (89,'Silver Fawn');
insert into colours (id,colour) values (90,'Silver Grey');
insert into colours (id,colour) values (91,'Sable dark');
insert into colours (id,colour) values (92,'Sable light');
insert into colours (id,colour) values (93,'Marten light');
insert into colours (id,colour) values (94,'Marten medium');
insert into colours (id,colour) values (95,'Marten dark');
insert into colours (id,colour) values (96,'Chocolate');
insert into colours (id,colour) values (97,'Yellow');
insert into colours (id,colour) values (98,'Pale Grey');
insert into colours (id,colour) values (99,'Steel Grey');
insert into colours (id,colour) values (100,'Tri-colour');
insert into colours (id,colour) values (101,'Smoke Pearl');
insert into colours (id,colour) values (102,'Lynx');
insert into colours (id,colour) values (103,'Tan');
insert into colours (id,colour) values (104,'Otter Black');
insert into colours (id,colour) values (105,'Otter Blue');
insert into colours (id,colour) values (106,'Otter Chocolate');
insert into colours (id,colour) values (107,'Otter Lilac');
insert into colours (id,colour) values (108,'Fox Black');
insert into colours (id,colour) values (109,'Fox Blue');
insert into colours (id,colour) values (110,'Fox Chocolate');
insert into colours (id,colour) values (111,'Fox Lilac');
insert into colours (id,colour) values (114,'Siamese Sable Light');
insert into colours (id,colour) values (115,'Siamese Smoke Pearl');
insert into colours (id,colour) values (116,'Marten Sable Light');
insert into colours (id,colour) values (117,'Marten Sable Medium');
insert into colours (id,colour) values (118,'Marten Sable Dark');
insert into colours (id,colour) values (119,'Marten Smoke Pearl');
insert into colours (id,colour) values (120,'Sable Siamese');
insert into colours (id,colour) values (121,'Marten Sable');
insert into colours (id,colour) values (122,'Blue and Tan');
insert into colours (id,colour) values (123,'Black and Tan');
insert into colours (id,colour) values (124,'Chocolate and Tan');
insert into colours (id,colour) values (125,'Lilac and Tan');
insert into colours (id,colour) values (126,'White');
insert into colours (id,colour) values (127,'Dark Steel Grey');
insert into colours (id,colour) values (128,'Normal');
insert into colours (id,colour) values (129,'Dark Steel');
insert into colours (id,colour) values (132,'Nutria');
insert into colours (id,colour) values (133,'Seal Siamese');
insert into colours (id,colour) values (134,'Fox');
insert into colours (id,colour) values (135,'Sable Marten');
insert into colours (id,colour) values (136,'Otter');
insert into colours (id,colour) values (137,'Harlequin');
insert into colours (id,colour) values (138,'Himalayan');
insert into colours (id,colour) values (139,'Silver Seal');
insert into colours (id,colour) values (140,'Satin Rex');
insert into colours (id,colour) values (141,'Astrex');
insert into colours (id,colour) values (142,'Opossum');


-- These two breeds were removed as they are superflous as there is already a Dutch, and English Breed
--insert into breeds (id,adult_age,top_pen_req,section,breed) values (4,2,0,1,'Dutch Tri-coloured');
--insert into breeds (id,adult_age,top_pen_req,section,breed) values (19,3,0,1,'Tri-coloured English');

insert into breedcolours (breed_id,colour_id) values (1,2);
insert into breedcolours (breed_id,colour_id) values (1,10);
insert into breedcolours (breed_id,colour_id) values (1,12);
insert into breedcolours (breed_id,colour_id) values (1,16);
insert into breedcolours (breed_id,colour_id) values (1,17);
insert into breedcolours (breed_id,colour_id) values (1,28);
insert into breedcolours (breed_id,colour_id) values (1,29);
insert into breedcolours (breed_id,colour_id) values (1,31);
insert into breedcolours (breed_id,colour_id) values (1,32);
insert into breedcolours (breed_id,colour_id) values (1,91);
insert into breedcolours (breed_id,colour_id) values (1,92);
insert into breedcolours (breed_id,colour_id) values (1,93);
insert into breedcolours (breed_id,colour_id) values (1,94);
insert into breedcolours (breed_id,colour_id) values (1,95);
insert into breedcolours (breed_id,colour_id) values (1,96);
insert into breedcolours (breed_id,colour_id) values (3,1);
insert into breedcolours (breed_id,colour_id) values (3,2);
insert into breedcolours (breed_id,colour_id) values (3,25);
insert into breedcolours (breed_id,colour_id) values (3,29);
insert into breedcolours (breed_id,colour_id) values (3,96);
insert into breedcolours (breed_id,colour_id) values (3,97);
insert into breedcolours (breed_id,colour_id) values (3,98);
insert into breedcolours (breed_id,colour_id) values (3,99);
insert into breedcolours (breed_id,colour_id) values (3,100);
insert into breedcolours (breed_id,colour_id) values (5,1);
insert into breedcolours (breed_id,colour_id) values (5,2);
insert into breedcolours (breed_id,colour_id) values (5,25);
insert into breedcolours (breed_id,colour_id) values (5,34);
insert into breedcolours (breed_id,colour_id) values (5,96);
insert into breedcolours (breed_id,colour_id) values (5,100);
insert into breedcolours (breed_id,colour_id) values (10,1);
insert into breedcolours (breed_id,colour_id) values (10,2);
insert into breedcolours (breed_id,colour_id) values (10,8);
insert into breedcolours (breed_id,colour_id) values (10,52);
insert into breedcolours (breed_id,colour_id) values (10,60);
insert into breedcolours (breed_id,colour_id) values (10,61);
insert into breedcolours (breed_id,colour_id) values (10,62);
insert into breedcolours (breed_id,colour_id) values (10,63);
insert into breedcolours (breed_id,colour_id) values (11,2);
insert into breedcolours (breed_id,colour_id) values (11,8);
insert into breedcolours (breed_id,colour_id) values (11,96);
insert into breedcolours (breed_id,colour_id) values (13,1);
insert into breedcolours (breed_id,colour_id) values (13,2);
insert into breedcolours (breed_id,colour_id) values (13,3);
insert into breedcolours (breed_id,colour_id) values (13,8);
insert into breedcolours (breed_id,colour_id) values (13,9);
insert into breedcolours (breed_id,colour_id) values (13,19);
insert into breedcolours (breed_id,colour_id) values (13,21);
insert into breedcolours (breed_id,colour_id) values (13,22);
insert into breedcolours (breed_id,colour_id) values (13,23);
insert into breedcolours (breed_id,colour_id) values (13,25);
insert into breedcolours (breed_id,colour_id) values (13,26);
insert into breedcolours (breed_id,colour_id) values (13,31);
insert into breedcolours (breed_id,colour_id) values (13,36);
insert into breedcolours (breed_id,colour_id) values (13,40);
insert into breedcolours (breed_id,colour_id) values (13,49);
insert into breedcolours (breed_id,colour_id) values (13,52);
insert into breedcolours (breed_id,colour_id) values (13,55);
insert into breedcolours (breed_id,colour_id) values (13,56);
insert into breedcolours (breed_id,colour_id) values (13,57);
insert into breedcolours (breed_id,colour_id) values (13,58);
insert into breedcolours (breed_id,colour_id) values (13,64);
insert into breedcolours (breed_id,colour_id) values (13,66);
insert into breedcolours (breed_id,colour_id) values (13,101);
insert into breedcolours (breed_id,colour_id) values (13,102);
insert into breedcolours (breed_id,colour_id) values (13,103);
insert into breedcolours (breed_id,colour_id) values (13,104);
insert into breedcolours (breed_id,colour_id) values (13,105);
insert into breedcolours (breed_id,colour_id) values (13,106);
insert into breedcolours (breed_id,colour_id) values (13,107);
insert into breedcolours (breed_id,colour_id) values (13,108);
insert into breedcolours (breed_id,colour_id) values (13,109);
insert into breedcolours (breed_id,colour_id) values (13,110);
insert into breedcolours (breed_id,colour_id) values (13,111);
insert into breedcolours (breed_id,colour_id) values (13,114);
insert into breedcolours (breed_id,colour_id) values (13,115);
insert into breedcolours (breed_id,colour_id) values (13,116);
insert into breedcolours (breed_id,colour_id) values (13,117);
insert into breedcolours (breed_id,colour_id) values (13,118);
insert into breedcolours (breed_id,colour_id) values (13,119);
insert into breedcolours (breed_id,colour_id) values (13,122);
insert into breedcolours (breed_id,colour_id) values (13,123);
insert into breedcolours (breed_id,colour_id) values (13,124);
insert into breedcolours (breed_id,colour_id) values (13,125);
insert into breedcolours (breed_id,colour_id) values (14,1);
insert into breedcolours (breed_id,colour_id) values (14,3);
insert into breedcolours (breed_id,colour_id) values (14,8);
insert into breedcolours (breed_id,colour_id) values (14,9);
insert into breedcolours (breed_id,colour_id) values (14,25);
insert into breedcolours (breed_id,colour_id) values (14,26);
insert into breedcolours (breed_id,colour_id) values (14,31);
insert into breedcolours (breed_id,colour_id) values (14,32);
insert into breedcolours (breed_id,colour_id) values (14,36);
insert into breedcolours (breed_id,colour_id) values (14,38);
insert into breedcolours (breed_id,colour_id) values (14,40);
insert into breedcolours (breed_id,colour_id) values (14,49);
insert into breedcolours (breed_id,colour_id) values (14,52);
insert into breedcolours (breed_id,colour_id) values (14,55);
insert into breedcolours (breed_id,colour_id) values (14,56);
insert into breedcolours (breed_id,colour_id) values (14,57);
insert into breedcolours (breed_id,colour_id) values (14,58);
insert into breedcolours (breed_id,colour_id) values (14,64);
insert into breedcolours (breed_id,colour_id) values (14,66);
insert into breedcolours (breed_id,colour_id) values (14,102);
insert into breedcolours (breed_id,colour_id) values (14,104);
insert into breedcolours (breed_id,colour_id) values (14,105);
insert into breedcolours (breed_id,colour_id) values (14,106);
insert into breedcolours (breed_id,colour_id) values (14,107);
insert into breedcolours (breed_id,colour_id) values (14,108);
insert into breedcolours (breed_id,colour_id) values (14,109);
insert into breedcolours (breed_id,colour_id) values (14,110);
insert into breedcolours (breed_id,colour_id) values (14,111);
insert into breedcolours (breed_id,colour_id) values (14,115);
insert into breedcolours (breed_id,colour_id) values (14,119);
insert into breedcolours (breed_id,colour_id) values (14,120);
insert into breedcolours (breed_id,colour_id) values (14,121);
insert into breedcolours (breed_id,colour_id) values (14,122);
insert into breedcolours (breed_id,colour_id) values (14,123);
insert into breedcolours (breed_id,colour_id) values (14,124);
insert into breedcolours (breed_id,colour_id) values (14,125);
insert into breedcolours (breed_id,colour_id) values (16,2);
insert into breedcolours (breed_id,colour_id) values (16,34);
insert into breedcolours (breed_id,colour_id) values (16,49);
insert into breedcolours (breed_id,colour_id) values (16,52);
insert into breedcolours (breed_id,colour_id) values (17,122);
insert into breedcolours (breed_id,colour_id) values (17,123);
insert into breedcolours (breed_id,colour_id) values (17,124);
insert into breedcolours (breed_id,colour_id) values (17,125);
insert into breedcolours (breed_id,colour_id) values (20,1);
insert into breedcolours (breed_id,colour_id) values (20,2);
insert into breedcolours (breed_id,colour_id) values (20,5);
insert into breedcolours (breed_id,colour_id) values (20,6);
insert into breedcolours (breed_id,colour_id) values (20,8);
insert into breedcolours (breed_id,colour_id) values (20,20);
insert into breedcolours (breed_id,colour_id) values (20,21);
insert into breedcolours (breed_id,colour_id) values (20,25);
insert into breedcolours (breed_id,colour_id) values (20,30);
insert into breedcolours (breed_id,colour_id) values (20,31);
insert into breedcolours (breed_id,colour_id) values (20,32);
insert into breedcolours (breed_id,colour_id) values (20,36);
insert into breedcolours (breed_id,colour_id) values (20,43);
insert into breedcolours (breed_id,colour_id) values (20,49);
insert into breedcolours (breed_id,colour_id) values (20,64);
insert into breedcolours (breed_id,colour_id) values (20,101);
insert into breedcolours (breed_id,colour_id) values (20,102);
insert into breedcolours (breed_id,colour_id) values (20,103);
insert into breedcolours (breed_id,colour_id) values (20,114);
insert into breedcolours (breed_id,colour_id) values (20,119);
insert into breedcolours (breed_id,colour_id) values (20,132);
insert into breedcolours (breed_id,colour_id) values (20,133);
insert into breedcolours (breed_id,colour_id) values (20,134);
insert into breedcolours (breed_id,colour_id) values (20,135);
insert into breedcolours (breed_id,colour_id) values (20,136);
insert into breedcolours (breed_id,colour_id) values (20,137);
insert into breedcolours (breed_id,colour_id) values (20,138);
insert into breedcolours (breed_id,colour_id) values (20,139);
insert into breedcolours (breed_id,colour_id) values (20,140);
insert into breedcolours (breed_id,colour_id) values (20,141);
insert into breedcolours (breed_id,colour_id) values (28,1);
insert into breedcolours (breed_id,colour_id) values (28,2);
insert into breedcolours (breed_id,colour_id) values (28,8);
insert into breedcolours (breed_id,colour_id) values (28,52);
insert into breedcolours (breed_id,colour_id) values (28,126);
insert into breedcolours (breed_id,colour_id) values (32,1);
insert into breedcolours (breed_id,colour_id) values (32,2);
insert into breedcolours (breed_id,colour_id) values (32,29);
insert into breedcolours (breed_id,colour_id) values (32,36);
insert into breedcolours (breed_id,colour_id) values (32,126);
insert into breedcolours (breed_id,colour_id) values (32,127);
insert into breedcolours (breed_id,colour_id) values (33,2);
insert into breedcolours (breed_id,colour_id) values (33,8);
insert into breedcolours (breed_id,colour_id) values (33,96);
insert into breedcolours (breed_id,colour_id) values (33,128);
insert into breedcolours (breed_id,colour_id) values (35,1);
insert into breedcolours (breed_id,colour_id) values (35,26);
insert into breedcolours (breed_id,colour_id) values (35,36);
insert into breedcolours (breed_id,colour_id) values (35,38);
insert into breedcolours (breed_id,colour_id) values (35,59);
insert into breedcolours (breed_id,colour_id) values (35,97);
insert into breedcolours (breed_id,colour_id) values (35,129);
insert into breedcolours (breed_id,colour_id) values (52,2);
insert into breedcolours (breed_id,colour_id) values (52,7);
insert into breedcolours (breed_id,colour_id) values (52,8);
insert into breedcolours (breed_id,colour_id) values (52,30);
insert into breedcolours (breed_id,colour_id) values (52,32);
insert into breedcolours (breed_id,colour_id) values (52,52);
insert into breedcolours (breed_id,colour_id) values (52,102);
insert into breedcolours (breed_id,colour_id) values (53,1);
insert into breedcolours (breed_id,colour_id) values (53,2);
insert into breedcolours (breed_id,colour_id) values (53,8);
insert into breedcolours (breed_id,colour_id) values (53,52);
insert into breedcolours (breed_id,colour_id) values (56,16);
insert into breedcolours (breed_id,colour_id) values (56,17);
insert into breedcolours (breed_id,colour_id) values (59,1);
insert into breedcolours (breed_id,colour_id) values (59,2);
insert into breedcolours (breed_id,colour_id) values (59,26);
insert into breedcolours (breed_id,colour_id) values (66,1);
insert into breedcolours (breed_id,colour_id) values (66,2);
insert into breedcolours (breed_id,colour_id) values (66,5);
insert into breedcolours (breed_id,colour_id) values (66,6);
insert into breedcolours (breed_id,colour_id) values (66,8);
insert into breedcolours (breed_id,colour_id) values (66,20);
insert into breedcolours (breed_id,colour_id) values (66,21);
insert into breedcolours (breed_id,colour_id) values (66,25);
insert into breedcolours (breed_id,colour_id) values (66,30);
insert into breedcolours (breed_id,colour_id) values (66,31);
insert into breedcolours (breed_id,colour_id) values (66,32);
insert into breedcolours (breed_id,colour_id) values (66,36);
insert into breedcolours (breed_id,colour_id) values (66,43);
insert into breedcolours (breed_id,colour_id) values (66,49);
insert into breedcolours (breed_id,colour_id) values (66,64);
insert into breedcolours (breed_id,colour_id) values (66,101);
insert into breedcolours (breed_id,colour_id) values (66,102);
insert into breedcolours (breed_id,colour_id) values (66,103);
insert into breedcolours (breed_id,colour_id) values (66,114);
insert into breedcolours (breed_id,colour_id) values (66,119);
insert into breedcolours (breed_id,colour_id) values (66,132);
insert into breedcolours (breed_id,colour_id) values (66,133);
insert into breedcolours (breed_id,colour_id) values (66,134);
insert into breedcolours (breed_id,colour_id) values (66,135);
insert into breedcolours (breed_id,colour_id) values (66,136);
insert into breedcolours (breed_id,colour_id) values (66,137);
insert into breedcolours (breed_id,colour_id) values (66,138);
insert into breedcolours (breed_id,colour_id) values (66,139);
insert into breedcolours (breed_id,colour_id) values (66,140);
insert into breedcolours (breed_id,colour_id) values (66,141);
insert into breedcolours (breed_id,colour_id) values (69,1);
insert into breedcolours (breed_id,colour_id) values (69,2);
insert into breedcolours (breed_id,colour_id) values (69,19);
insert into breedcolours (breed_id,colour_id) values (69,20);
insert into breedcolours (breed_id,colour_id) values (69,21);
insert into breedcolours (breed_id,colour_id) values (69,24);
insert into breedcolours (breed_id,colour_id) values (69,26);
insert into breedcolours (breed_id,colour_id) values (69,31);
insert into breedcolours (breed_id,colour_id) values (69,36);
insert into breedcolours (breed_id,colour_id) values (69,42);
insert into breedcolours (breed_id,colour_id) values (69,49);
insert into breedcolours (breed_id,colour_id) values (69,52);
insert into breedcolours (breed_id,colour_id) values (69,64);
insert into breedcolours (breed_id,colour_id) values (69,66);
insert into breedcolours (breed_id,colour_id) values (69,69);
insert into breedcolours (breed_id,colour_id) values (69,72);
insert into breedcolours (breed_id,colour_id) values (69,75);
insert into breedcolours (breed_id,colour_id) values (69,114);
insert into breedcolours (breed_id,colour_id) values (69,126);
insert into breedcolours (breed_id,colour_id) values (69,135);
--insert into breedcolours (breed_id,colour_id) values (69,143);
insert into breedcolours (breed_id,colour_id) values (71,1);
insert into breedcolours (breed_id,colour_id) values (71,2);
insert into breedcolours (breed_id,colour_id) values (71,20);
insert into breedcolours (breed_id,colour_id) values (71,21);
insert into breedcolours (breed_id,colour_id) values (71,26);
insert into breedcolours (breed_id,colour_id) values (71,31);
insert into breedcolours (breed_id,colour_id) values (71,36);
insert into breedcolours (breed_id,colour_id) values (71,42);
insert into breedcolours (breed_id,colour_id) values (71,49);
insert into breedcolours (breed_id,colour_id) values (71,52);
insert into breedcolours (breed_id,colour_id) values (71,66);
insert into breedcolours (breed_id,colour_id) values (71,114);
insert into breedcolours (breed_id,colour_id) values (71,126);
insert into breedcolours (breed_id,colour_id) values (74,15);
insert into breedcolours (breed_id,colour_id) values (75,1);
insert into breedcolours (breed_id,colour_id) values (75,2);
insert into breedcolours (breed_id,colour_id) values (75,11);
insert into breedcolours (breed_id,colour_id) values (75,13);
insert into breedcolours (breed_id,colour_id) values (75,18);
insert into breedcolours (breed_id,colour_id) values (75,19);
insert into breedcolours (breed_id,colour_id) values (75,20);
insert into breedcolours (breed_id,colour_id) values (75,21);
insert into breedcolours (breed_id,colour_id) values (75,24);
insert into breedcolours (breed_id,colour_id) values (75,26);
insert into breedcolours (breed_id,colour_id) values (75,32);
insert into breedcolours (breed_id,colour_id) values (75,36);
insert into breedcolours (breed_id,colour_id) values (75,42);
insert into breedcolours (breed_id,colour_id) values (75,49);
insert into breedcolours (breed_id,colour_id) values (75,64);
insert into breedcolours (breed_id,colour_id) values (75,66);
insert into breedcolours (breed_id,colour_id) values (75,72);
insert into breedcolours (breed_id,colour_id) values (75,73);
insert into breedcolours (breed_id,colour_id) values (75,96);
insert into breedcolours (breed_id,colour_id) values (75,114);
insert into breedcolours (breed_id,colour_id) values (75,126);
--insert into breedcolours (breed_id,colour_id) values (75,143);

insert into exhibit_genders (id,gender,gender_text) values (1,0,'unknown');
insert into exhibit_genders (id,gender,gender_text) values (2,1,'Buck');
insert into exhibit_genders (id,gender,gender_text) values (3,2,'Doe');
insert into exhibit_genders (id,gender,gender_text) values (4,3,'Open');

insert into human_genders (id,gender,gender_text) values (5,0,'unknown');
insert into human_genders (id,gender,gender_text) values (6,1,'Gents');
insert into human_genders (id,gender,gender_text) values (7,2,'Ladies');
insert into human_genders (id,gender,gender_text) values (8,3,'Open');

insert into exhibit_ages (id,age,age_text) values (1,0,'unknown');
insert into exhibit_ages (id,age,age_text) values (2,1,'under 14 weeks');
insert into exhibit_ages (id,age,age_text) values (3,2,'under 4 months');
insert into exhibit_ages (id,age,age_text) values (4,2,'under 5 months');
insert into exhibit_ages (id,age,age_text) values (5,3,'Any youngster');
insert into exhibit_ages (id,age,age_text) values (6,4,'Adult');
insert into exhibit_ages (id,age,age_text) values (7,7,'Any Age');

insert into ShowSections (id, section, section_text) values (1,1,'Fancy');
insert into ShowSections (id, section, section_text) values (2,2,'Lop');
insert into ShowSections (id, section, section_text) values (3,4,'Fur');
insert into ShowSections (id, section, section_text) values (4,8,'Rex');
insert into ShowSections (id, section, section_text) values (5,15,'Open');
insert into ShowSections (id, section, section_text) values (6,0,'Pet');




