package com.moviedbproxy.db;

import javax.persistence.*;
import java.math.BigInteger;

/**
 * Created by deepika on 27-11-2019.
 */
@Entity
@Table(name = "movie")
public class MovieDomain {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "movieId")
	private BigInteger movieId;

    @Column(name = "popularity99")
    private double popularity99;

    @Column(name = "director")
    private String director;

    @Column(name = "genre")
    private String genre;

    @Column(name = "imdb_score")
    private double imdb_score;

    @Column(name = "name")
    private String name;

    public MovieDomain(){
    	//default constructor
	}
	public MovieDomain(double popularity99, String director, String genre, double imdb_score, String name){
		this.popularity99 = popularity99;
		this.director = director;
		this.genre = genre;
		this.imdb_score = imdb_score;
		this.name = name;
	}

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

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
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
