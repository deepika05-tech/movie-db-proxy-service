package com.moviedbproxy.service;

import com.moviedbproxy.db.MovieDomain;
import com.moviedbproxy.db.MovieRepository;
import com.moviedbproxy.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by deepika on 27-11-2019.
 */
@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Override
    public List<Movie> findAll() {
        List<MovieDomain> movieDomainList = movieRepository.findAll();
        List<Movie> movieList = new ArrayList<>();
        if(null != movieDomainList && movieDomainList.size() > 0) {
            for (MovieDomain movieDomain : movieDomainList){
                movieList.add(generateMovie(movieDomain));
            }
        }
        return movieList;
    }


    @Override
    public List<Movie> findMovieByNameStartingWith(String name) {
        List<MovieDomain> movieDomainList = movieRepository.findMovieByNameStartingWith(name);
        List<Movie> movieList = new ArrayList<>();
        if(null != movieDomainList && movieDomainList.size() > 0) {
            for (MovieDomain movieDomain : movieDomainList){
                movieList.add(generateMovie(movieDomain));
            }
        }
        return movieList;
    }

    @Override
    public Movie findMovieById(BigInteger movieId) {
        MovieDomain movieDomain = movieRepository.findMovieByMovieId(movieId);
        if(null != movieDomain) {
            Movie movie = generateMovie(movieDomain);
            return movie;
        }
        return null;
    }

    @Override
    public boolean deleteMovieById(BigInteger movieId){
        MovieDomain movieDomain = movieRepository.findMovieByMovieId(movieId);
        if(null != movieDomain){
            movieRepository.delete(movieDomain);
            return true;
        }
        return false;
    }

    @Override
    public Movie updateMovie(BigInteger movieId, Movie movie){
        MovieDomain movieDomain = movieRepository.findMovieByMovieId(movieId);
        if(null != movieDomain){
            if(isNotBlank(movie.getDirector()))
                movieDomain.setDirector(movie.getDirector());

            if(null != movie.getGenre() && movie.getGenre().size() > 0)
                movieDomain.setGenre(String.join(",", movie.getGenre()));

            if(0.0 != movie.getImdb_score())
                movieDomain.setImdb_score(movie.getImdb_score());

            if(isNotBlank(movie.getName()))
                movieDomain.setName(movie.getName());

            if(0.0 != movie.getPopularity99())
                movieDomain.setPopularity99(movie.getPopularity99());

            MovieDomain movieDomainResponse = movieRepository.save(movieDomain);
            return generateMovie(movieDomainResponse);
        }
        return null;
    }

    @Override
    public Movie saveMovie(Movie movie) {
        MovieDomain movieDomain = movieRepository.findMovieByName(movie.getName());
        if(null == movieDomain){
            MovieDomain newMovieDomain = generateMovieDomain(movie);
            MovieDomain movieDomainResponse = movieRepository.save(newMovieDomain);
            movie.setMovieId(movieDomainResponse.getMovieId());
            return movie;
        }
        return null;
    }

    /**
     * Generate Movie object from MovieDomain object
     * @param movieDomain
     * @return
     */
    private Movie generateMovie(MovieDomain movieDomain) {
        Movie movie = new Movie();
        movie.setMovieId(movieDomain.getMovieId());
        movie.setDirector(movieDomain.getDirector());
        if (isNotBlank(movieDomain.getGenre()))
            movie.setGenre(Arrays.asList(movieDomain.getGenre().split(",")));
        movie.setImdb_score(movieDomain.getImdb_score());
        movie.setName(movieDomain.getName());
        movie.setPopularity99(movieDomain.getPopularity99());
        return movie;
    }

    /**
     * Generate MovieDomain object from Movie object
     * @param movie object
     * @return MovieDomain object
     */
    public MovieDomain generateMovieDomain(Movie movie) {
        MovieDomain movieDomain = new MovieDomain();
        movieDomain.setDirector(movie.getDirector());
        if(null != movie.getGenre())
            movieDomain.setGenre(String.join(",", movie.getGenre()));
        movieDomain.setImdb_score(movie.getImdb_score());
        movieDomain.setName(movie.getName());
        movieDomain.setPopularity99(movie.getPopularity99());
        return movieDomain;
    }

    @Override
    public boolean isNotBlank(String string){
        return string != null && string.trim().length() > 0;
    }
}
