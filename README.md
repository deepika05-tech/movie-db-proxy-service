# movie management api(movie-db-proxy-service)
A REST api for fetching/updating movies from mysql DB using Spring Boot


There are 2 levels of access(hardcoded 2 dummy users): 

admin = who can add, remove or edit movies(username - admin, password - password). 

users = who can just view the movies(username - user, password - password).


Rest endpoints: 

To get all movie list:
http://localhost:8080/movie-db-proxy-service/fetchAll,

To get movies based on start with name:
http://localhost:8080/movie-db-proxy-service/startWithName/{name},

To get movies based on Id: 
http://localhost:8080/movie-db-proxy-service/fetch/{movieId}, 

To insert new movie details:
http://localhost:8080/movie-db-proxy-service/save, 

To update movie details of the given Id:
http://localhost:8080/movie-db-proxy-service/update/{movieId}, 

To delete movie of the given Id:
http://localhost:8080/movie-db-proxy-service/delete?movieId={movieId}
