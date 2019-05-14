package com.example.demo;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.springmiddleware.controllers.DataController;
import com.springmiddleware.entities.Data;
import com.springmiddleware.service.impl.DataServiceDBImpl;
import com.springmiddleware.service.impl.DataServiceImpl;



/**
 * Tests for CrimeService class - Testing logic
 * 
 * @author MLondei
 * 
 */

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { DataServiceDBImpl.class, DataController.class, Data.class })
@ComponentScan("com.springmiddleware")
@EntityScan(basePackages = { "com.springmiddleware.entities" })
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@EnableJpaRepositories("com.springmiddleware")
public class TestDataService {



	private DataServiceImpl service = new DataServiceImpl();



	// Basic tests - local, no serialization


	
	public void returnCrimes() throws NoSuchMethodException,
			SecurityException, IOException {
		assertTrue(this.service.getAllData() instanceof List<?>); // check if returns a list
		assertTrue(this.service.getAllData().isEmpty()); // check if there's something in the list
		assertTrue(this.service.getAllData().get(0) instanceof Data); // check if items in list are of Crime class
		assertTrue(this.service.getAllData().size() > 1); // check if there's more than 1 item
	}



	
	public void getDataById() {
		Long id = 4L;
		this.service.addData(id, new Data());
		assertTrue(this.service.getDataById(id) != null); // check if returns something
		assertTrue(this.service.getDataById(id) instanceof Data);// check if what method returns is a Crime
		assertTrue(((Data) this.service.getDataById(id)).getId() == id); // check if the Crime searched is the one returned
																			
	}



	
	public void addData() throws IOException { 
	  Long id = 5L;
	  this.service.addData(id, new Data());
	  assertTrue(this.service.getAllData().size() > 0); //check if, if I add a new Crime, list is not empty
	  assertTrue(this.service.getAllData().stream().filter(p -> p.getId() ==id).findAny().isPresent()); //check if, after adding a Crime, I can find it in the list 
	  }



	public void deleteData() throws IOException { 
		long id = 5L;
	  this.service.addData(id, new Data()); 
	  int oldSize =	this.service.getAllData().size(); 
	  this.service.deleteData(id);
	  assertTrue(this.service.getAllData().size() == (oldSize-1)); //list size is decreased by 1
	  assertFalse(this.service.getAllData().contains(this.service.getDataById(id))); //the list does not contains the deleted Crime 
	  }



	public void updateData() throws IOException { 
	long id = 9L; 
	  //Data crime = new Data(id);//oldcrime this.service.addCrime(id, crime); 
	  int oldSize = this.service.getAllData().size(); 
	  //Data newCrime = new Data(crime.getId(),crime.getZipCode(),crime.getTotPopulation(),crime.getMedianAge(),crime.getTotMales(),10,crime.getTotHouseholds(),crime.
	//  getAvgHouseholdSize()); 
	// this.service.updateCrime(id,this.service.getCrimeById(id),newCrime);
	  assertTrue(this.service.getAllData().size() == oldSize); //list size remains the same 
	  Data changedCrime = this.service.getDataById(id);
	  assertTrue(changedCrime!=null); //the changed Crime is ok
	  assertTrue(changedCrime.getTotFemales() == 10); //check on the changed field
	  }



	// Advanced tests - Local CSV file for input and output

	// Read from CSV file
	@Test
	public void returnAllCrimes() throws NoSuchMethodException, SecurityException, IOException {

		try {
			List<Data> list = this.service.getAllData();
			for (Object c : list) {
				assertTrue(c.getClass().equals(Data.class));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	@Test
	public void getCrimeById() {
		Long id = 320L; // id di esempio del file
		Data check = this.service.getDataById(id);
		assertTrue(check != null); // check if returns something
		assertTrue(check instanceof Data); // check if what method returns is a Crime
		assertTrue(check.getId() == id); // check if the Crime searched is the one returned - all field checked
		assertTrue(check.getZipCode() == 320);
		assertTrue(check.getTotPopulation() == 320);
		assertTrue(check.getMedianAge() == 320);
		assertTrue(check.getTotMales() == 320);
		assertTrue(check.getTotFemales() == 320);
		assertTrue(check.getTotHouseholds() == 320);
		assertTrue(check.getAvgHouseholdSize() == 320);
	}



	@Test
	public void addCrime() throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
		Long id = 506L;
		this.service.addData(id, new Data(id));
		Data check = this.service.getDataById(id);
		assertTrue(check.getId() != -1); // check if, after adding a Crime, I can find it in the list
		assertTrue(check.getId() == id); // check if the Crime searched is the one returned - all field checked
		assertTrue(check.getZipCode() == id);
		assertTrue(check.getTotPopulation() == id);
		assertTrue(check.getMedianAge() == id);
		assertTrue(check.getTotMales() == id);
		assertTrue(check.getTotFemales() == id);
		assertTrue(check.getTotHouseholds() == id);
		assertTrue(check.getAvgHouseholdSize() == id);
	}



	@Test
	public void deleteCrime() throws IOException {
		long id = 0L; // crime non esistente
		long id2 = this.service.getAllData().get(0).getId(); // per fare in modo che cancelli sempre un crime
																// esistente e ci sia coverage
		this.service.deleteData(id);
		this.service.deleteData(id2);
		assertFalse(this.service.getAllData().contains(this.service.getDataById(id))); // the list does not contains
																							// the deleted Crime
		assertFalse(this.service.getAllData().contains(this.service.getDataById(id2)));
	}



	@Test
	public void updateCrime() throws IOException {
		Data oldCrime = this.service.getDataById(319L);
		Data newCrime = new Data(oldCrime.getId(), 20, 10,
				10, 10, 10, 10, 30);
		this.service.updateData(oldCrime, newCrime);
		Data changedCrime = this.service.getDataById(319L);
		assertTrue(changedCrime != null); // the changed Crime is ok
		assertTrue(changedCrime.getTotFemales() == 10); // check on the changed field
	}

}
