package com.springmiddleware.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.springmiddleware.entities.Data;
import com.springmiddleware.services.DataService;
import com.springmiddleware.utilities.UtilityMethods;


/**
 * Service class that performs operations with CSV file
 * @author MLondei
 * */
@Service
public class DataServiceImpl implements DataService {


	private List<Data> list = new ArrayList<Data>();



	/**
	 * Constructor - initializes the list reading the CSV file
	 */
	public DataServiceImpl() {
		list = UtilityMethods.readFromCSV();
	};


	/* (non-Javadoc)
	 * @see com.springmiddleware.services.CrimeService#getAllCrimes()
	 */
	@Override
	public ArrayList<Data> getAllData() throws IOException {
		return (ArrayList<Data>) (list = UtilityMethods.readFromCSV());
	}


	
	/**
	 * Refreshes list by reading the CSV file
	 */
	public void refreshList() {
		list = UtilityMethods.readFromCSV();
	}

	/* (non-Javadoc)
	 * @see com.springmiddleware.services.CrimeService#getCrimeById(java.lang.Long)
	 */
	@Override
	public Data getDataById(Long id) {
		this.refreshList();
		Optional<Data> oc = this.list.stream().filter(p -> p.getId() == id).findFirst();
		Data c = oc.orElse(new Data(-1));
		return c;
	}



	/* (non-Javadoc)
	 * @see com.springmiddleware.services.CrimeService#addCrime(java.lang.Long, com.springmiddleware.entities.Crime)
	 */
	@Override
	public Data addData(Long id, Data data) {
		try {
			UtilityMethods.writeToCsv(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}



	/* (non-Javadoc)
	 * @see com.springmiddleware.services.CrimeService#deleteCrime(java.lang.Long)
	 */
	@Override
	public Data deleteData(Long id) {
		Data c = getDataById(id);
		if (c != null && c.getId()!=-1) { //cancello solo se il Crime è presente nella lista
			UtilityMethods.deleteFromCsv(c);
			this.list.remove(c);		
			return this.getDataById(id);
		} else {
			return new Data(-1);
		}
	}



	/* (non-Javadoc)
	 * @see com.springmiddleware.services.CrimeService#updateCrime(com.springmiddleware.entities.Crime, com.springmiddleware.entities.Crime)
	 */
	@Override
	public Data updateData(Data oldData, Data newData) {
		this.refreshList();
		int index = 0;
		try {
			index = getAllData().indexOf(oldData);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		list.set(index, newData);
		try {
			UtilityMethods.updateCsv(list);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return newData;
	}

}
