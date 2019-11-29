CREATE TABLE `movie` (
  `movieId` bigint(11) NOT NULL auto_increment,
  `popularity99` double default NULL,
  `director` varchar(60) default NULL,
  `genre` varchar(120) default NULL,
  `imdb_score` double default NULL,
  `name` varchar(60) default NULL,
  PRIMARY KEY  (`movieId`)
) 

CREATE INDEX nameIndex
ON movie_schema.movie(name);
