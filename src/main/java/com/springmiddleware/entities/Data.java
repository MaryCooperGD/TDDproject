package com.springmiddleware.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.lang.NonNull;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;



/**
 * Class representing a Data entity
 * 
 * @author MLondei
 */
@Entity
@Table(name = "crimes")
public class Data {



	@CsvBindByPosition(position = 0)
	@CsvBindByName(column = "id")
	@Id
	private long id;



	@CsvBindByPosition(position = 1)
	@CsvBindByName(column = "zipCode")
	@NonNull
	private int zipCode;



	@CsvBindByPosition(position = 2)
	@CsvBindByName(column = "totPopulation")
	@NonNull
	private int totPopulation;



	@CsvBindByPosition(position = 3)
	@CsvBindByName(column = "medianAge")
	@NonNull
	private double medianAge;



	@CsvBindByPosition(position = 4)
	@CsvBindByName(column = "totMales")
	@NonNull
	private int totMales;



	@CsvBindByPosition(position = 5)
	@CsvBindByName(column = "totFemales")
	@NonNull
	private int totFemales;



	@CsvBindByPosition(position = 6)
	@CsvBindByName(column = "totHouseholds")
	@NonNull
	private int totHouseholds;



	@CsvBindByPosition(position = 7)
	@CsvBindByName(column = "avgHouseholdSize")
	@NonNull
	private double avgHouseholdSize;



	/**
	 * Constructor
	 * 
	 * @param id
	 * @param zipCode
	 * @param totPopulation
	 * @param medianAge
	 * @param totMales
	 * @param totFemales
	 * @param totHouseholds
	 * @param avgHouseholdSize
	 */
	public Data(long id, int zipCode, int totPopulation, double medianAge, int totMales, int totFemales,
			int totHouseholds, double avgHouseholdSize) {
		this.id = id;
		this.zipCode = zipCode;
		this.totPopulation = totPopulation;
		this.medianAge = medianAge;
		this.totMales = totMales;
		this.totFemales = totFemales;
		this.totHouseholds = totHouseholds;
		this.avgHouseholdSize = avgHouseholdSize;
	}



	/**
	 * Empty constructor, create a Data with all fields set to 0
	 * 
	 */
	public Data() {
		this.id = 0;
		this.zipCode = 0;
		this.totPopulation = 0;
		this.medianAge = 0;
		this.totMales = 0;
		this.totFemales = 0;
		this.totHouseholds = 0;
		this.avgHouseholdSize = 0;
	}



	/**
	 * Constructor that accept the id. All fields are set to id value
	 * 
	 * @param id
	 */
	public Data(long id) {
		this.id = id;
		this.zipCode = (int) id;
		this.totPopulation = (int) id;
		this.medianAge = (int) id;
		this.totMales = (int) id;
		this.totFemales = (int) id;
		this.totHouseholds = (int) id;
		this.avgHouseholdSize = (int) id;
	}



	/**
	 * @return id of the Data entity
	 */
	public long getId() {
		return id;
	}



	/**
	 * @return zip code
	 */
	public int getZipCode() {
		return zipCode;
	}



	/**
	 * Zipcode setter
	 * 
	 * @param zipCode
	 */
	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
	}



	/**
	 * @return total population
	 */
	public int getTotPopulation() {
		return totPopulation;
	}



	/**
	 * Tot population setter
	 * 
	 * @param totPopulation
	 */
	public void setTotPopulation(int totPopulation) {
		this.totPopulation = totPopulation;
	}



	/**
	 * @return median age
	 */
	public double getMedianAge() {
		return medianAge;
	}



	/**
	 * Median age setter
	 * 
	 * @param medianAge
	 */
	public void setMedianAge(double medianAge) {
		this.medianAge = medianAge;
	}



	/**
	 * @return total males
	 */
	public int getTotMales() {
		return totMales;
	}



	/**
	 * total males setter
	 * 
	 * @param totMales
	 */
	public void setTotMales(int totMales) {
		this.totMales = totMales;
	}



	/**
	 * @return total females
	 */
	public int getTotFemales() {
		return totFemales;
	}



	/**
	 * total females setter
	 * 
	 * @param totFemales
	 */
	public void setTotFemales(int totFemales) {
		this.totFemales = totFemales;
	}



	/**
	 * @return total households
	 */
	public int getTotHouseholds() {
		return totHouseholds;
	}



	/**
	 * total households setter
	 * 
	 * @param totHouseholds
	 */
	public void setTotHouseholds(int totHouseholds) {
		this.totHouseholds = totHouseholds;
	}



	/**
	 * @return average household size
	 */
	public double getAvgHouseholdSize() {
		return avgHouseholdSize;
	}



	/**
	 * average household setter
	 * 
	 * @param avgHouseholdSize
	 */
	public void setAvgHouseholdSize(double avgHouseholdSize) {
		this.avgHouseholdSize = avgHouseholdSize;
	}



	// Override per poter fare la indexOf.
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object c) {
		if (c == this) {
			return true;
		}

		if (!(c instanceof Data)) {
			return false;
		}

		Data crime = (Data) c;

		return Long.compare(this.id, crime.id) == 0 &&
				Integer.compare(this.zipCode, crime.zipCode) == 0 &&
				Integer.compare(this.totPopulation, totPopulation) == 0 &&
				Double.compare(this.medianAge, crime.medianAge) == 0 &&
				Integer.compare(this.totMales, crime.totMales) == 0 &&
				Integer.compare(this.totFemales, crime.totFemales) == 0 &&
				Integer.compare(this.totHouseholds, crime.totHouseholds) == 0 &&
				Double.compare(this.avgHouseholdSize, crime.avgHouseholdSize) == 0;

	}



	@Override
	public String toString() {
		return "id " + id + "\r\nzip code " + zipCode + "\r\ntot population " + totPopulation + "\r\nmedian age " + medianAge + "\r\ntot males "
				+ totMales + "\r\ntot females " + totFemales + "\r\ntothouseholds " + totHouseholds + "\r\naverage household size " + avgHouseholdSize;
	}



}
