package com.springmiddleware.services;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.springmiddleware.entities.Data;

/**
 * Defines all possible operations for a Crime object
 * 
 *@author MLondei
 * */
@Service
public interface DataService {
	
	/**
	 * Get all Data entities
	 * @return the list of all Data
	 * @throws IOException
	 */
	public List<Data> getAllData()  throws IOException;
	
	/**
	 * Get a Data by Id
	 * @param id the id of the Data entity 
	 * @return the Data, if present
	 */
	public Data getDataById(Long id);
	
	/**
	 * Add a new Data entity
	 * @param id the id of the new Data entity
	 * @param data the Data to add
	 * @return the added Data entity
	 */
	public Data addData(Long id, Data data);
	
	/** 
	 * Delete a Data entity, if present
	 * @param id the id of the Data entity to be deleted
	 * @return the deleted Data entity
	 */
	public Data deleteData(Long id);
	
	/**
	 * Update a Data entity, if present. The IDs of the Data entities must be the same
	 * @param oldData the old Data entity
	 * @param newData the updated Data entity
	 * @return the updated Data
	 */
	public Data updateData(Data oldData, Data newData);
	
	
}
