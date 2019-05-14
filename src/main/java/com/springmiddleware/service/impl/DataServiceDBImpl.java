package com.springmiddleware.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Service;

import com.springmiddleware.entities.Data;
import com.springmiddleware.repository.DataRepository;
import com.springmiddleware.services.DataService;


/**
 * Service class implementation for DB
 * @author MLondei
 * */
@EnableAutoConfiguration
@Service
public class DataServiceDBImpl implements DataService{
	
	@Autowired(required=true)
	private DataRepository repository;
	

	/**
	 * Constructor
	 */
	public DataServiceDBImpl() {
	};
	
	
	/* (non-Javadoc)
	 * @see com.springmiddleware.services.CrimeService#getAllCrimes()
	 */
	@Override
	public List<Data> getAllData(){
		return repository.findAll();
	}

	/* (non-Javadoc)
	 * @see com.springmiddleware.services.CrimeService#getCrimeById(java.lang.Long)
	 */
	@Override
	public Data getDataById(Long id) {
		return repository.findById(id).orElse(new Data(-1)); //returns an optional, so Crime or new Crime
	}

	/* (non-Javadoc)
	 * @see com.springmiddleware.services.CrimeService#addCrime(java.lang.Long, com.springmiddleware.entities.Crime)
	 */
	@Override
	public Data addData(Long id, Data data) {
		Data c = this.repository.save(data);
		return c;
	}

	/* (non-Javadoc)
	 * @see com.springmiddleware.services.CrimeService#deleteCrime(java.lang.Long)
	 */
	@Override
	public Data deleteData(Long id) {
		try {
		repository.deleteById(id);
		} catch (org.springframework.dao.EmptyResultDataAccessException e) { //gestione eccezione entità non presente
			return new Data(-1);
		}
		return repository.findById(id).orElse(new Data(-1));
	}

	/* (non-Javadoc)
	 * @see com.springmiddleware.services.CrimeService#updateCrime(com.springmiddleware.entities.Crime, com.springmiddleware.entities.Crime)
	 */
	@Override
	public Data updateData(Data oldData, Data newData) {
		if(oldData.getId() != newData.getId() || !(repository.findById(oldData.getId()).isPresent())) {
			return new Data(-1);
		}
		else{
			oldData.setZipCode(newData.getZipCode());
			oldData.setAvgHouseholdSize(newData.getAvgHouseholdSize());
			oldData.setMedianAge(newData.getMedianAge());
			oldData.setTotFemales(newData.getTotFemales());
			oldData.setTotHouseholds(newData.getTotHouseholds());
			oldData.setTotMales(newData.getTotMales());
			oldData.setTotPopulation(newData.getTotPopulation());
			repository.save(oldData);
			return oldData;
		} 
	}

}
