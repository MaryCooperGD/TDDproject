package com.example.demo;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.springmiddleware.entities.Data;
import com.springmiddlware.main.SpringMiddlewareApplication;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = SpringMiddlewareApplication.class)
@ComponentScan("com.springmiddleware")
@SpringBootTest
public class TestData {
	
	private Data c = new Data(0);
	
	@SuppressWarnings("unlikely-arg-type")
	@Test
	public void testEqualsMethod() {
		Data c2 = new Data(0);
		Data c3 = new Data(0,2,3,3,4,5,6,7);
		Data c4 = new Data(0,0,10,3,4,5,6,7);
		Data c5 = new Data(0,0,0,3,4,5,6,7);
		Data c6 = new Data(0,0,0,0,4,5,6,7);
		Data c7 = new Data(0,0,0,0,0,5,6,7);
		Data c8 = new Data(0,0,0,0,0,0,6,7);
		Data c9 = new Data(0,0,0,0,0,0,0,7);
		assertTrue(c.equals(c));
		assertFalse(c.equals(new ArrayList<Data>()));
		assertTrue(c.equals(c2));
		assertFalse(c.equals(c3));
		assertFalse(c.equals(c4));
		assertFalse(c.equals(c5));
		assertFalse(c.equals(c6));
		assertFalse(c.equals(c7));
		assertFalse(c.equals(c8));
		assertFalse(c.equals(c9));
	}
	
	@Test
	public void testClass() {
		Data c = new Data(1,2,3,4,5,6,7,8);
		assertTrue(c.getId() == 1);
		assertTrue(c.getZipCode() == 2);
		assertTrue(c.getTotPopulation() == 3);
		assertTrue(c.getMedianAge()==4);
		assertTrue(c.getTotMales() ==5);
		assertTrue(c.getTotFemales() ==6);
		assertTrue(c.getTotHouseholds() == 7);
		assertTrue(c.getAvgHouseholdSize() == 8);
	}

}
