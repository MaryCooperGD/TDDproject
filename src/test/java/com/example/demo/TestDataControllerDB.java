package com.example.demo;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.util.List;

import org.junit.Before;
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
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springmiddleware.controllers.DataControllerDB;
import com.springmiddleware.entities.Data;
import com.springmiddleware.service.impl.DataServiceDBImpl;



@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { DataServiceDBImpl.class, DataControllerDB.class, Data.class })
@ComponentScan("com.springmiddleware")
@EntityScan(basePackages = { "com.springmiddleware.entities" })
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@EnableJpaRepositories("com.springmiddleware")
@EnableAutoConfiguration
public class TestDataControllerDB {



	@Autowired
	private DataControllerDB controller;



	private MockMvc mvc;



	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));



	@Autowired
	private ObjectMapper objectMapper;



	@Before
	public void initialize() {
		mvc = MockMvcBuilders.standaloneSetup(controller).build();
	}



	@Test
	public void getAllCrimes() {
		this.objectMapper = new ObjectMapper();
		try {
			ResultActions resultActions = this.mvc
					.perform(MockMvcRequestBuilders.get("/dbcrimes").accept(MediaType.APPLICATION_JSON));
			this.mvc.perform(get("/dbcrimes")).andExpect(status().isOk());
			MvcResult result = resultActions.andReturn();
			List<Object> list = objectMapper.readValue(result.getResponse().getContentAsString(),
					new TypeReference<List<Data>>() {
					});
			for (Object c : list) {
				assertTrue(c.getClass().equals(Data.class));
			}
			//assertTrue(list.size() == 298);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void getCrime() {
		this.objectMapper = new ObjectMapper();
		try {
			ResultActions resultActions = this.mvc
					.perform(MockMvcRequestBuilders.get("/getdbcrime/23").accept(MediaType.APPLICATION_JSON));
			MvcResult result = resultActions.andReturn();
			Data c = objectMapper.readValue(result.getResponse().getContentAsString(), Data.class);
			assertTrue(c.getMedianAge() == 6);
			resultActions = this.mvc
					.perform(MockMvcRequestBuilders.get("/getdbcrime/2").accept(MediaType.APPLICATION_JSON));
			result = resultActions.andReturn();
			assertTrue(result.getResponse().getContentAsString().equals("Il crime non è presente"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	@Test
	public void add() {
		this.objectMapper = new ObjectMapper();
		try {
			Data c = new Data(418);
			String body = objectMapper.valueToTree(c).toString();
			ResultActions resultActions = this.mvc.perform(
					MockMvcRequestBuilders.post("/dbadd").contentType(MediaType.APPLICATION_JSON).content(body))
					.andExpect(status().isOk());
			MvcResult result = resultActions.andReturn();
			Object o = objectMapper.readValue(result.getResponse().getContentAsString(), Data.class);
			assertTrue(o instanceof Data);
			assertTrue(((Data)o).getId() == 418);
			
			//check sulla lista
			ResultActions resultAction2 = this.mvc
					.perform(MockMvcRequestBuilders.get("/dbcrimes").accept(MediaType.APPLICATION_JSON));
			MvcResult result2 = resultAction2.andReturn();
			List<Object> list = objectMapper.readValue(result2.getResponse().getContentAsString(),
					new TypeReference<List<Data>>(){});
			//assertTrue(list.size() == 298);// se ne aggiungo uno la lista cresce /rimane uguale
			assertTrue(list.contains(c));//entità presente nella lista
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	@Test
	public void delete() {
		this.objectMapper = new ObjectMapper();
		try {
			ResultActions resultActions = this.mvc.perform(
					MockMvcRequestBuilders.delete("/dbdelete/20"))
					.andExpect(status().isOk());
			MvcResult result = resultActions.andReturn();
			Object o = objectMapper.readValue(result.getResponse().getContentAsString(), Data.class);
			assertTrue(o instanceof Data);
			assertTrue(((Data)o).getId() == -1);
			
		
			//check sulla lista
			ResultActions resultAction2 = this.mvc
					.perform(MockMvcRequestBuilders.get("/dbcrimes").accept(MediaType.APPLICATION_JSON));
			MvcResult result2 = resultAction2.andReturn();
			List<Object> list = objectMapper.readValue(result2.getResponse().getContentAsString(),
					new TypeReference<List<Data>>(){});
			//assertTrue(list.size() == 295);// se ne aggiungo uno la lista cresce /rimane uguale
			assertFalse(list.contains((Data)o));//l'entità non è più presente nella lista
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	@Test
	public void update() {
		this.objectMapper = new ObjectMapper();
		try {
			ResultActions resultAction2 = this.mvc
					.perform(MockMvcRequestBuilders.get("/dbcrimes").accept(MediaType.APPLICATION_JSON));
			MvcResult result2 = resultAction2.andReturn();
			List<Object> list = objectMapper.readValue(result2.getResponse().getContentAsString(),
					new TypeReference<List<Data>>(){});
			
			Data toUpdate = new Data(23L,6,6,6,6,6,6,6);
			Data c = (Data)list.get(list.indexOf(toUpdate)); //should be ID 23
			c.setAvgHouseholdSize(6);
			c.setMedianAge(6);
			c.setTotFemales(6);
			c.setTotHouseholds(6);
			c.setTotMales(6);
			c.setTotPopulation(6);
			c.setZipCode(6);
			String body = objectMapper.valueToTree(c).toString();
			ResultActions resultActions = this.mvc
					.perform(MockMvcRequestBuilders.put("/dbupdate/23").contentType(APPLICATION_JSON_UTF8).content(body));
			MvcResult result = resultActions.andReturn();
			String contentString = result.getResponse().getContentAsString();
			Data crime = objectMapper.readValue(contentString, Data.class);
			assertTrue(crime.getTotFemales() == 6);
			
			//Caso in cui il crime non è presente
			Data nc = new Data(17L,6,6,6,6,6,6,6);
			body = objectMapper.valueToTree(nc).toString();
			resultActions = this.mvc
					.perform(MockMvcRequestBuilders.put("/dbupdate/17").contentType(APPLICATION_JSON_UTF8).content(body));
			 	result = resultActions.andReturn();
				contentString = result.getResponse().getContentAsString();
				assertTrue(contentString.equals("L'Id specificato non corrisponde a nessuna entry esistente"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
