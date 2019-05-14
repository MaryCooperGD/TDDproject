package com.springmiddleware.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.springmiddleware.entities.Data;
import com.springmiddleware.service.impl.DataServiceImpl;



/**
 * Basic RestController for reading/writing to CSV file.
 * @author MLondei
 * */
@RestController
public class DataController {



	@Autowired
	private DataServiceImpl service = new DataServiceImpl();//l'inizializzazione della lista avviene qui
	



	/**
	 * Endpoint for getting all Data
	 * @return a String representing all Data
	 */
	@GetMapping("/crimes")
	public String allData(){
		return service.addData(3L, new Data(3)).toString();
	}

 

	/**
	 * Endpoint that returns a Data entity
	 * @param id the id of the Data
	 * @return a String representing the Data
	 */
	@GetMapping("/getcrime/{id}")
	public String getData(@PathVariable Long id) {
		Data c = this.service.getDataById(id);
		if(c.getId() == -1) {
			return "Il crime non è presente";
		} else {
			return new Gson().toJson(this.service.getDataById(id)); // change in json
		}
	}



	/**
	 * Endpoint for adding a new Data entity
	 * @param data the Data to add
	 * @return a String representing the added crime
	 */
	@PostMapping("/addcrime")
	public String addData(@RequestBody Data data) {	
		return new Gson().toJson(this.service.addData(data.getId(), data));
	}


	
	/**
	 * Endpoint for deleting a Data entity. If the Data specified is not present, no Data is deleted
	 * @param id the id of the Data to delete
	 * @return the Data deleted or a Data with -1 values if not present
	 */
	@RequestMapping(value = "/deletecrime/{id}", method = RequestMethod.DELETE)
	public Data deleteData(@PathVariable Long id) {
		return this.service.deleteData(id);
	}



	/**
	 * Endpoint for updating a Data entity. If the Data specified is not present, no Data is updated
	 * @param id the id of the Data to be updated
	 * @param data the Data with new values
	 * @return a string representing the updated Data or an error string if not present
	 */
	@RequestMapping(value = "/updatecrime/{id}", method = RequestMethod.PUT)
	public String updateData(@PathVariable Long id, @RequestBody Data data) {
		Data oldCrime = this.service.getDataById(id);
		if(oldCrime.getId()!=-1) {
			return new Gson().toJson(this.service.updateData(oldCrime, data));
		} else {
			return "L'Id specificato non corrisponde a nessuna entry esistente";
		}	
	}



}
