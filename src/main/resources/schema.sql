create schema ecobioanalysis;

CREATE TABLE taxons (
  id  INTEGER auto_increment NOT NULL ,
   name varchar(10),
   values varchar(10),
   valuex varchar(10),
   valueo varchar(10),
   valueb varchar(10),
   valuea varchar(10),
   valuep varchar(10),
   valueG varchar(10),
   value_S varchar(10),
   comment varchar(250));



CREATE TABLE project (
   id  INTEGER auto_increment NOT NULL ,
   name varchar(255));

CREATE TABLE point(
   id  INTEGER auto_increment NOT NULL ,
   name varchar(255),
   project_id INTEGER,
   FOREIGN KEY (project_id) REFERENCES project(id)
);

CREATE TABLE parameter(
  id  INTEGER auto_increment NOT NULL ,
  h varchar(10),
  point_id INTEGER,
  taxon_id INTEGER,
  FOREIGN KEY (point_id) REFERENCES point(id),
  FOREIGN KEY (taxon_id) REFERENCES taxons(id)
 );


