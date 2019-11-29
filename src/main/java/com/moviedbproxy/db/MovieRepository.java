package com.moviedbproxy.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;


/**
 * Created by deepika on 27-11-2019.
 */
@Repository
@Transactional
public interface MovieRepository extends JpaRepository<MovieDomain,Long> {

    List<MovieDomain> findMovieByNameStartingWith(String name);
    MovieDomain findMovieByMovieId(BigInteger movie);
    MovieDomain findMovieByName(String name);
    List<MovieDomain> findAll();

}
