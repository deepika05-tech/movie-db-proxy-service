package com.moviedbproxy.controller;

import com.moviedbproxy.exception.CustomException;
import com.moviedbproxy.model.Movie;
import com.moviedbproxy.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by deepika on 27-11-2019.
 */
@RestController
@RequestMapping("/movie-db-proxy-service")
public class MovieController {

    @Autowired
    private MovieService movieService;

    public static final Logger logger = LoggerFactory.getLogger(MovieController.class);

    /**
     * fetch movie by movieId
     * @param movieId
     * @return ResponseEntity object
     */
    @GetMapping("fetch/{movieId}")
    public ResponseEntity<?> fetchMovieById(@PathVariable("movieId")BigInteger movieId) {
        if(null != movieId) {
            Movie movie = movieService.findMovieById(movieId);
            if (null == movie) {
                return new ResponseEntity(new CustomException("Movie with id " + movieId
                        + " is not found"), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<Movie>(movie, HttpStatus.OK);
        } else {
            return new ResponseEntity(new CustomException("Kindly provide valid movieId"), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * list of all movie
     * @return ResponseEntity object
     */
    @GetMapping("fetchAll")
    public ResponseEntity<?> fetchAllMovies() {
        List<Movie> movies = movieService.findAll();
        if (null == movies && movies.size() == 0) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Movie>>(movies, HttpStatus.OK);
    }

    /**
     * search movie start with name
     * @param name
     * @return ResponseEntity object
     */
    @GetMapping("startWithName/{name}")
    public ResponseEntity<?> fetchMovieByNameStartingWith(@PathVariable("name")String name){
        if(movieService.isNotBlank(name)){
            List<Movie> movies = movieService.findMovieByNameStartingWith(name);
            if (null == movies || movies.size() == 0) {
                return new ResponseEntity(new CustomException("Start with movie name: " + name + " is not found"),HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<List<Movie>>(movies, HttpStatus.OK);
        } else {
            return new ResponseEntity(new CustomException("Kindly provide valid movie name"), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * add new movie
     * @param movie
     * @return ResponseEntity object
     */
    @PostMapping("save")
    public ResponseEntity<?> saveMovie(@RequestBody Movie movie){
        if(null != movie){
            Movie movieResponse = movieService.saveMovie(movie);
            if(null == movieResponse){
                logger.error("Unable to add as movie already exist with name: "+ movie.getName());
                return new ResponseEntity(new CustomException
                        ("Unable to add as movie already exist with name: "+ movie.getName()), HttpStatus.BAD_REQUEST);
            }
            logger.info("Added movie with id: "+ movieResponse.getMovieId());
            return new ResponseEntity<Movie>(movieResponse, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(new CustomException("Kindly provide valid movie details"), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * update specific movie using movie id
     * @param movieId
     * @param movie
     * @return ResponseEntity object
     */
    @PutMapping("update/{movieId}")
    public ResponseEntity<?> updateMovie(@PathVariable("movieId")BigInteger movieId, @RequestBody Movie movie) {
        if(null != movie && null != movieId && movieId != movie.getMovieId()){
            Movie updatedMovie = movieService.updateMovie(movieId, movie);
            if (null == updatedMovie) {
                logger.error("Unable to update as movie not found with Id: "+ movieId);
                return new ResponseEntity(new CustomException
                        ("Unable to update as movie not found with Id: "+ movieId),HttpStatus.NOT_FOUND);
            }
            logger.info("Updated movie with id: "+ movieId);
            return new ResponseEntity<Movie>(updatedMovie, HttpStatus.OK);
        } else {
            return new ResponseEntity(new CustomException("Kindly provide valid movieId and movie details"), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * delete specific movie using movie id
     * @param movieId
     * @return ResponseEntity object
     */
    @DeleteMapping("delete")
    public ResponseEntity<?> deleteMovie(@RequestParam("movieId")BigInteger movieId) {
        if(null != movieId){
            boolean isDeleted = movieService.deleteMovieById(movieId);
            if (!isDeleted) {
                logger.error("Unable to delete as movie not found with Id: "+ movieId);
                return new ResponseEntity(new CustomException
                        ("Unable to delete as movie not found with Id: "+ movieId), HttpStatus.NOT_FOUND);
            }
            logger.info("Deleted movie with id: "+ movieId);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(new CustomException("Kindly provide valid movieId"), HttpStatus.BAD_REQUEST);
        }
    }
}