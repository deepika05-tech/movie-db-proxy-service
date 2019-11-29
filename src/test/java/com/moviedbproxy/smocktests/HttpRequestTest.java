package com.moviedbproxy.smocktests;

import com.moviedbproxy.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class) //Without this, TRT wont be initialised
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT, classes=Application.class)
public class HttpRequestTest {
	
	@LocalServerPort
	private int port;
	
	@Autowired
	TestRestTemplate restTemplate;
	
	@Test
	public void testDefaultMessage()
	{
		System.out.println("Hitting random port -"+port);
		assertThat(restTemplate.getForObject("http://localhost:"+port+"/movie-db-proxy-service/fetchAll", String.class))
		.contains("\"error\":\"Unauthorized\""); // as we are not providing credential for user and admin
	}
	
}
