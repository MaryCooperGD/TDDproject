package com.example.demo;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.springmiddleware.entities.Data;
import com.springmiddleware.utilities.MyMappingStrategy;

@SpringBootTest
public class TestMappingStrategy {
	
	@Test
	public void testSuperConstructor() {
		MyMappingStrategy ms = new MyMappingStrategy();
		assertTrue(ms!=null);
	}
	
	@Test
	public void testGenerateHeader() {
		MyMappingStrategy ms = new MyMappingStrategy();
		Data c = new Data();
		Object o = ms.generateHeader(c);
		assertTrue(o instanceof String[]);
	}

}
