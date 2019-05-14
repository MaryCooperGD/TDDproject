package com.example.demo;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.springmiddleware.controllers.DataController;
import com.springmiddleware.entities.Data;
import com.springmiddleware.repository.DataRepository;
import com.springmiddleware.service.impl.DataServiceDBImpl;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { DataServiceDBImpl.class, DataController.class, Data.class })
@ComponentScan("com.springmiddleware")
@EntityScan(basePackages = {"com.springmiddleware.entities"})
//@DataJpaTest
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@EnableJpaRepositories("com.springmiddleware")
@EnableAutoConfiguration
public class TestDataServiceDB {
	
	
	@Autowired
	private DataRepository myRepository;
	
	@Autowired
	DataServiceDBImpl service = new DataServiceDBImpl();
	
	@Test
	public void getAll() {
		assertTrue(service.getAllData() instanceof List<?>);
	}
	
	@Test
	public void addOne() {
		service.addData(20L, new Data(20));
		assertTrue(myRepository.findById(20L)!=null); //sia che ne aggiungo uno nuovo che esista già l'asserzione è soddisfatta
	}
	
	@Test
	public void getOne() {
		long id = 104L;
		long id2 = 11L;
		Data a = service.getDataById(id);
		Data b = service.getDataById(id2);
		assertTrue(a.getId() == id);
		assertTrue(b.getId() == -1);
	}
	
	@Test
	public void delete() {
		service.deleteData(24L);
		assertTrue(service.getDataById(24L).getId() == -1); // se cancello il crime, mi deve tornare il crime con id -1 
	}
	
	
	
	@Test
	public void update() {
		Data c = service.getDataById(10L);
		Data c2 = service.getDataById(0L);
		Data newCrime = new Data(10L,6,6,6,6,6,6,6);
		Data updated = service.updateData(c, newCrime);
		Data updated2 = service.updateData(c2, newCrime);
		assertTrue(updated.getMedianAge() == 6);
		assertTrue(updated2.getMedianAge() == -1);
	}

}
