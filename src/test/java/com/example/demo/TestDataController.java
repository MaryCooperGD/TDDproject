package com.example.demo;


import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
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
import com.springmiddleware.controllers.DataController;
import com.springmiddleware.entities.Data;
import com.springmiddleware.service.impl.DataServiceImpl;



/**
 * Test for CrimeController class - Testing endpoints
 * 
 * @author MLondei
 */
/*
 * @RunWith(SpringRunner.class)
 * 
 * @ContextConfiguration(classes = SpringMiddlewareApplication.class)
 * 
 * @ComponentScan("com.springmiddleware")
 * 
 * @SpringBootTest
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { DataServiceImpl.class, DataController.class, Data.class })
@ComponentScan("com.springmiddleware")
@EntityScan(basePackages = { "com.springmiddleware.entities" })
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@EnableJpaRepositories("com.springmiddleware")
public class TestDataController {



	private MockMvc mvc = MockMvcBuilders.standaloneSetup(new DataController()).build();



	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));



	private ObjectMapper objectMapper;



	// BASE TESTS FOR ENDPOINT


	public void getAllCrimes1() {
		try {
			this.mvc.perform(get("/crimes")).andExpect(status().isOk())
					.andExpect(content().string(containsString("List of all crimes")));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}



	public void getOneCrime1() {
		// his.service.addCrime(5L, new Crime(5));
		try {
			this.mvc.perform(get("/getcrime/5")).andExpect(status().isOk())
					.andExpect(content().string(containsString("Get one crime")));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}



	public void postCrime1() {
		try {
			this.mvc.perform(get("/addcrime")).andExpect(status().isOk())
					.andExpect(content().string(containsString("Post one crime")));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}



	public void deleteCrime1() {
		// this.service.addCrime(5L, new Crime(5));
		try {
			this.mvc.perform(get("/deletecrime/5")).andExpect(status().isOk())
					.andExpect(content().string(containsString("Delete a crime")));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}



	public void updateCrime1() {
		// this.service.addCrime(5L, new Crime(5));
		try {
			this.mvc.perform(get("/updatecrime/5")).andExpect(status().isOk())
					.andExpect(content().string(containsString("Update a crime")));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}



	// ADVANCED TESTS FOR ENDPOINTS

	public void getAllCrimes2() {
		try {
			/*ResultActions resultActions = this.mvc
					.perform(MockMvcRequestBuilders.get("/crimes").accept(MediaType.APPLICATION_JSON));*/
			this.mvc.perform(get("/crimes")).andExpect(status().isOk())
					.andExpect(jsonPath("$[0].zipCode", is(0)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	public void getOneCrime2() {
		try {
			/*ResultActions resultActions = this.mvc
					.perform(MockMvcRequestBuilders.get("/getcrime/5").accept(MediaType.APPLICATION_JSON));*/
			this.mvc.perform(get("/getcrime/5")).andExpect(status().isOk()).andExpect(jsonPath("$.zipCode", is(5))); // jsonPath notation
		} catch (Exception e) {
			e.printStackTrace();
		}
	}




	public void postCrime2() {
		this.objectMapper = new ObjectMapper();
		try {
			ResultActions resultActions = this.mvc
					.perform(MockMvcRequestBuilders.get("/addcrime"));

			MvcResult result = resultActions.andReturn();
			String contentString = result.getResponse().getContentAsString();
			Data crime = objectMapper.readValue(contentString, Data.class);
			assertTrue(crime.getId() == 5L);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	
	public void deleteCrime2() {
		try {
			ResultActions resultActions = this.mvc.perform(MockMvcRequestBuilders.get("/deletecrime/5"));
			MvcResult result = resultActions.andReturn();
			String contentString = result.getResponse().getContentAsString();
			assertTrue(contentString.equals("true"));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}



	
	public void updateCrime2() {
		this.objectMapper = new ObjectMapper();
		try {
			ResultActions resultActions = this.mvc
					.perform(MockMvcRequestBuilders.get("/updatecrime/5"));
			MvcResult result = resultActions.andReturn();
			String contentString = result.getResponse().getContentAsString();
			Data crime = objectMapper.readValue(contentString, Data.class);
			assertTrue(crime.getTotFemales() == 10);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}



	// ADVANCED TESTS - CSV FILE READING
	
	@Test
	public void getAllCrimes() {
		this.objectMapper = new ObjectMapper();
		try {
			ResultActions resultActions = this.mvc
					.perform(MockMvcRequestBuilders.get("/crimes").accept(MediaType.APPLICATION_JSON));
			this.mvc.perform(get("/crimes")).andExpect(status().isOk());
			MvcResult result = resultActions.andReturn();
			List<Object> list = objectMapper.readValue(result.getResponse().getContentAsString(),
					new TypeReference<List<Data>>() {
					});
			for (Object c : list) {
				assertTrue(c.getClass().equals(Data.class));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	@Test
	public void getOneCrime() {
		this.objectMapper = new ObjectMapper();
		try {
			ResultActions resultActions = this.mvc
					.perform(MockMvcRequestBuilders.get("/getcrime/506").accept(MediaType.APPLICATION_JSON));
			MvcResult result = resultActions.andReturn();
			Data c = objectMapper.readValue(result.getResponse().getContentAsString(), Data.class);
			assertTrue(c.getMedianAge() == 506);
			resultActions = this.mvc
					.perform(MockMvcRequestBuilders.get("/getcrime/2").accept(MediaType.APPLICATION_JSON));
			result = resultActions.andReturn();
			assertTrue(result.getResponse().getContentAsString().equals("Il crime non è presente"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	// do not add if already present
	@Test
	public void postCrime() {
		this.objectMapper = new ObjectMapper();
		try {
			Data c = new Data(800);
			String body = objectMapper.valueToTree(c).toString();
			ResultActions resultActions = this.mvc
					.perform(MockMvcRequestBuilders.post("/addcrime").contentType(APPLICATION_JSON_UTF8).content(body));
			MvcResult result = resultActions.andReturn();
			String contentString = result.getResponse().getContentAsString();
			Data crime = objectMapper.readValue(contentString, Data.class);
			assertTrue(crime.getId() == 800);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	@Test
	public void updateCrime() {
		this.objectMapper = new ObjectMapper();
		try {
			ResultActions resultActions;
			Data c = new Data(10); // new crime
			String body = objectMapper.valueToTree(c).toString();
			resultActions = this.mvc
					.perform(MockMvcRequestBuilders.put("/updatecrime/37").contentType(APPLICATION_JSON_UTF8)
							.content(body));
			MvcResult result = resultActions.andReturn();
			String contentString = result.getResponse().getContentAsString();
			Data crime = objectMapper.readValue(contentString, Data.class);
			assertTrue(crime.getTotFemales() == 10);
			// Caso in cui il crime non è presente
			resultActions = this.mvc
					.perform(MockMvcRequestBuilders.put("/updatecrime/17").contentType(APPLICATION_JSON_UTF8)
							.content(body));
			result = resultActions.andReturn();
			contentString = result.getResponse().getContentAsString();
			assertTrue(contentString.equals("L'Id specificato non corrisponde a nessuna entry esistente"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	@Test
	public void deleteCrime() {
		this.objectMapper = new ObjectMapper();
		try {
			ResultActions resultActions = this.mvc
					.perform(MockMvcRequestBuilders.delete("/deletecrime/301"));
			MvcResult result = resultActions.andReturn();
			String contentString = result.getResponse().getContentAsString();
			Data crime = objectMapper.readValue(contentString, Data.class);
			assertTrue(crime.getId() == -1); // se provo a recuperare il crime, mi deve dare 0 (crime inesistente)
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
