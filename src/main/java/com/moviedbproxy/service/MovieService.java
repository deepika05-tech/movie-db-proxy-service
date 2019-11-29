package com.moviedbproxy.service;

import com.moviedbproxy.model.Movie;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by deepika on 27-11-2019.
 */
public interface MovieService {

    List<Movie> findMovieByNameStartingWith(String name);

    Movie findMovieById(BigInteger movieId);

    List<Movie> findAll();

    Movie saveMovie(Movie movie);

    boolean deleteMovieById(BigInteger movieId);

    Movie updateMovie(BigInteger movieId, Movie movie);

    boolean isNotBlank(String string);
}
