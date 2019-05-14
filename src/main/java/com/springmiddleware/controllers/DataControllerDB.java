package com.springmiddleware.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.springmiddleware.entities.Data;
import com.springmiddleware.service.impl.DataServiceDBImpl;
import com.springmiddleware.utilities.Sender;



/**
 * CLass for a RestController connected to a Postgres DB
 * 
 * @author MLondei
 */
@EnableAutoConfiguration
@EnableJpaRepositories("com.springmiddleware")
@RestController
public class DataControllerDB {



	@Autowired
	Sender sender;



	@Autowired
	private DataServiceDBImpl dbservice = new DataServiceDBImpl();



	/**
	 * Endpoint for getting all crimes from DB
	 * 
	 * @return the list of crimes
	 */
	@GetMapping("/dbcrimes")
	public List<Data> allData() {
		sender.sendInfo("Total number of Data is " + dbservice.getAllData().size());
		return dbservice.getAllData();
	}



	/**
	 * Endpoint that returns a Data entity
	 * 
	 * @param id the id of the Data entity
	 * @return a String representing the Data entity
	 */
	@GetMapping("/getdbcrime/{id}")
	public String getData(@PathVariable Long id) {
		Data c = this.dbservice.getDataById(id);
		if (c.getId() == -1) {
			sender.sendError("The specified Data entity is not present");
			return "Il crime non è presente";
		} else {
			sender.sendInfo(c);
			return new Gson().toJson(c); // change in json
		}
	}



	/**
	 * Endpoint for adding a new Data entity to DB
	 * 
	 * @param data the Data entity to add
	 * @return a String representing the Data added
	 */
	@PostMapping("/dbadd")
	public String addData(@RequestBody Data data) {
		sender.sendInfo("Saved Data entity " + data.getId());
		return new Gson().toJson(dbservice.addData(data.getId(), data));
	}



	/**
	 * Endpoint for deleting a Data entity. If the entity specified is not present, no
	 * Data is deleted
	 * 
	 * @param id the id of the Data entity to delete
	 * @return the Data deleted or a Data with -1 values if not present
	 */
	@RequestMapping(value = "/dbdelete/{id}", method = RequestMethod.DELETE)
	public Data deleteData(@PathVariable Long id) {
		sender.sendInfo("Crime deleted");
		return (this.dbservice.deleteData(id));
	}



	/**
	 * Endpoint for updating a Data entity. If the Data entity specified is not present, no Data
	 * is updated
	 * 
	 * @param id the id of the Data to be updated
	 * @param data the Data entity with new values
	 * @return a string representing the updated Data or an error string if not
	 *         present
	 */
	@RequestMapping(value = "/dbupdate/{id}", method = RequestMethod.PUT)
	public String updateData(@PathVariable Long id, @RequestBody Data data) {
		Data oldCrime = this.dbservice.getDataById(id);
		if (oldCrime.getId() != -1) {
			sender.sendInfo("Updated Data with id " + oldCrime.getId());
			return new Gson().toJson(this.dbservice.updateData(oldCrime, data));
		} else {
			sender.sendError("The specified Data does not exist");
			return "L'Id specificato non corrisponde a nessuna entry esistente";
		}
	}



}
