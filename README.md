# movie management api
A REST api for fetching/updating movies from mysql DB using Spring Boot

There are 2 levels of access(hardcoded 2 dummy users):
admin = who can add, remove or edit movies(username - admin, password - password)
users = who can just view the movies(username - user, password - password)

Rest endpoints:
http://localhost:8080/movie-db-proxy-service/startWithName/{name}
http://localhost:8080/movie-db-proxy-service/fetchAll
http://localhost:8080/movie-db-proxy-service/fetch/{movieId}
http://localhost:8080/movie-db-proxy-service/save
http://localhost:8080/movie-db-proxy-service/update/{movieId}
http://localhost:8080/movie-db-proxy-service/delete?movieId={movieId}