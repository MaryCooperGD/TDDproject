package com.springmiddleware.utilities;


import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.springmiddleware.entities.Data;

/**
 * Class to override generateHeader method for writing to CSV
 * @author MLondei
 *
 */
public class MyMappingStrategy extends HeaderColumnNameMappingStrategy<Data>{
	
	/**
	 * Constructor (uses superclass' constructor)
	 */
	public MyMappingStrategy() {
		super();
	}
	
	/* (non-Javadoc)
	 * @see com.opencsv.bean.AbstractMappingStrategy#generateHeader(java.lang.Object)
	 */
	@Override
	public String[] generateHeader(Data c) {
		return new String[0];
	}

}
