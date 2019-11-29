package com.moviedbproxy.smocktests;

import com.moviedbproxy.Application;
import com.moviedbproxy.controller.MovieController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class) //integrate Junit features with spring boot test
@SpringBootTest(classes=Application.class)
public class ApplicationTest {

    @Autowired
    private MovieController movieController;

    @Test
    public void contextLoads() throws Exception
    {
        assertThat(movieController).isNotNull();
    }

}

