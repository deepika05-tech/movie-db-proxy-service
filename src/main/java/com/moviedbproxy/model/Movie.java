package com.moviedbproxy.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by deepika on 27-11-2019.
 */
public class Movie {

	private BigInteger movieId;

	@JsonProperty("99popularity")
    private double popularity99;

    private String director;

    private List<String> genre;

    private double imdb_score;

    private String name;

    public BigInteger getMovieId() {
        return movieId;
    }

    public void setMovieId(BigInteger movieId) {
        this.movieId = movieId;
    }
	 
	public double getPopularity99() {
		return popularity99;
	}
	public void setPopularity99(double popularity99) {
		this.popularity99 = popularity99;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public List<String> getGenre() {
		return genre;
	}
	public void setGenre(List<String> genre) {
		this.genre = genre;
	}
	public double getImdb_score() {
		return imdb_score;
	}
	public void setImdb_score(double imdb_score) {
		this.imdb_score = imdb_score;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "Movie {popularity99=" + popularity99 + ", director=" + director + ", genre=" + genre + ", imdb_score="
				+ imdb_score + ", name=" + name + "}";
	}
	
}
