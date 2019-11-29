package com.moviedbproxy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Resources;
import com.moviedbproxy.db.MovieDomain;
import com.moviedbproxy.db.MovieDomainList;
import com.moviedbproxy.db.MovieRepository;
import com.moviedbproxy.model.Movie;
import com.moviedbproxy.model.MovieList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class MovieControllerRestTemplateTest {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    protected MovieDomainList movieDomainList;
    protected MovieDomainList movieDomainListForName;
    protected MovieDomain movieDomain;
    protected String movieDomainStr;
    protected String movieDomainListStr;
    protected String movieDomainListForNameStr;
    protected MovieList movieList;
    protected Movie movie;
    protected String movieStr;
    protected String movieListStr;

    //@WithMockUser is not working with TestRestTemplate
    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private MovieRepository mockRepository;

    @Before
    public void init() throws IOException {
        URL movieDomainListURL = Resources.getResource("mockData/movieDomainList.json");
        movieDomainList = objectMapper.readValue(movieDomainListURL, MovieDomainList.class);

        URL movieListURL = Resources.getResource("mockData/movieList.json");
        movieList = objectMapper.readValue(movieListURL, MovieList.class);

        movieDomain = movieDomainList.get(0);
        movieDomainListStr = movieDomain.toString();
        movieDomainStr = movieDomain.toString();

        movieDomainListForName = new MovieDomainList();
        movieDomainListForName.add(movieDomain);
        movieDomainListForNameStr=movieDomainListForName.toString();

        movie = movieList.get(0);
        movieStr = movie.toString();
        movieListStr = movieList.toString();

        when(mockRepository.findMovieByMovieId(BigInteger.valueOf(1))).thenReturn(movieDomain);
        when(mockRepository.findAll()).thenReturn(movieDomainList);
        when(mockRepository.findMovieByName(movie.getName())).thenReturn(movieDomain);
        when(mockRepository.findMovieByNameStartingWith(movie.getName())).thenReturn(movieDomainListForName);
        when(mockRepository.save(movieDomain)).thenReturn(movieDomain);
    }

    @Test
    public void fetchMovieByIdAndLoginOk() throws Exception {
        ResponseEntity<String> response = restTemplate
                .withBasicAuth("user", "password")
                .getForEntity("/movie-db-proxy-service/fetch/1", String.class);
        printJSON(response);

        assertEquals(MediaType.APPLICATION_JSON_UTF8, response.getHeaders().getContentType());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains(movie.getName()));
    }

    @Test
    public void fetchMovieByIdNologin401() throws Exception {
        ResponseEntity<String> response = restTemplate
                .getForEntity("/movie-db-proxy-service/fetch/1", String.class);
        printJSON(response);

        assertEquals(MediaType.APPLICATION_JSON_UTF8, response.getHeaders().getContentType());
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertTrue(response.getBody().contains(HttpStatus.UNAUTHORIZED.getReasonPhrase()));
    }

    @Test
    public void fetchAllMoviesAndLoginOk() throws Exception {
        ResponseEntity<String> response = restTemplate
                .withBasicAuth("user", "password")
                .getForEntity("/movie-db-proxy-service/fetchAll", String.class);
        printJSON(response);

        assertEquals(MediaType.APPLICATION_JSON_UTF8, response.getHeaders().getContentType());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains(movie.getName()));
    }

    @Test
    public void fetchMoviesByNameStartwithAndLoginOK() throws Exception {
        ResponseEntity<String> response = restTemplate
                .withBasicAuth("user", "password")
                .getForEntity("/movie-db-proxy-service/startWithName/"+movie.getName(), String.class);
        printJSON(response);

        assertEquals(MediaType.APPLICATION_JSON_UTF8, response.getHeaders().getContentType());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains(movie.getName()));
    }

    @Test
    public void saveMoviAndLogin400() throws Exception {
        ResponseEntity<String> response = restTemplate
                .withBasicAuth("admin", "password")
                .postForEntity("/movie-db-proxy-service/save", movie, String.class);
        printJSON(response);

        assertEquals(MediaType.APPLICATION_JSON_UTF8, response.getHeaders().getContentType());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().contains("Unable to add as movie already exist with name"));
    }

    @Test
    public void saveMoviAndLogin403() throws Exception {
        ResponseEntity<String> response = restTemplate
                .withBasicAuth("user", "password")
                .postForEntity("/movie-db-proxy-service/save", movie, String.class);
        printJSON(response);

        assertEquals(MediaType.APPLICATION_JSON_UTF8, response.getHeaders().getContentType());
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertTrue(response.getBody().contains(HttpStatus.FORBIDDEN.getReasonPhrase()));
    }

    @Test
    public void saveMovieAndLogin401() throws Exception {
        ResponseEntity<String> response = restTemplate
                .postForEntity("/movie-db-proxy-service/save", movie, String.class);
        printJSON(response);

        assertEquals(MediaType.APPLICATION_JSON_UTF8, response.getHeaders().getContentType());
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertTrue(response.getBody().contains(HttpStatus.UNAUTHORIZED.getReasonPhrase()));
    }

    private static void printJSON(Object object) {
        String result;
        try {
            result = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
            System.out.println(result);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
