package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.springmiddlware.main.SpringMiddlewareApplication;

import static org.junit.Assert.*;

import java.io.IOException;



@RunWith(SpringRunner.class)
@ContextConfiguration(classes = SpringMiddlewareApplication.class)
@ComponentScan("com.springmiddleware")
@SpringBootTest(classes=SpringMiddlewareApplication.class)
//@WebMvcTest
public class SpringMiddlewareApplicationTests {




	@Test
	public void contextLoads() throws Exception {
		try {
			SpringMiddlewareApplication.main(new String[] {});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}



	@Test
	public void canRunUnitTests() {
		assertTrue(true);
	}


}
