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

