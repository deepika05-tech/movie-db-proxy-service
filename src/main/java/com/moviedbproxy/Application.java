package com.moviedbproxy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Resources;
import com.moviedbproxy.db.MovieDomainList;
import com.moviedbproxy.db.MovieRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.net.URL;

/**
 * Created by deepika on 27-11-2019.
 */
@SpringBootApplication
@ComponentScan
public class Application {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner initDatabase(MovieRepository movieRepository) {
        return args -> {
            final ObjectMapper objectMapper = new ObjectMapper();
            URL movieDomainListURL = Resources.getResource("movieDomainList.json");
            MovieDomainList movieDomainList = objectMapper.readValue(movieDomainListURL, MovieDomainList.class);
            movieDomainList.forEach(movieDomain->movieRepository.save(movieDomain));
        };
    }
}
