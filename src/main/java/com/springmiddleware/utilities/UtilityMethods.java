package com.springmiddleware.utilities;


import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.springmiddleware.entities.Data;



/**
 * 
 * Utility class
 * @author MLondei
 *
 */
public final class UtilityMethods {



	private static String file = "PopulationFile.csv";



	/**
	 * Constructor
	 */
	private UtilityMethods() {
	}



	/**
	 * Read CSV file and returns the Data in the file
	 * @return the list of Data read from CSV
	 */
	public static ArrayList<Data> readFromCSV() {
		ArrayList<Data> crimes = new ArrayList<>();
		FileReader filereader;
		try {
			filereader = new FileReader(file);

			CSVReader csvReader = new CSVReader(filereader);

			String[] nextRecord;
			//csvReader.readNext();//skip header
			while ((nextRecord = csvReader.readNext()) != null) {
				crimes.add(new Data(Long.parseLong(nextRecord[0]),
						Integer.parseInt(nextRecord[1]), Integer.parseInt(nextRecord[2]),
						Double.parseDouble(nextRecord[3]), Integer.parseInt(nextRecord[4]),
						Integer.parseInt(nextRecord[5]), Integer.parseInt(nextRecord[6]),
						Double.parseDouble(nextRecord[7])));
			}
			csvReader.close();
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}
		return crimes;
	}



	/**
	 * 
	 * Write a new Data to the CSV file
	 * @param c the Data to write to CSV
	 * @throws IOException
	 */
	public static void writeToCsv(Data c) throws IOException {

		Writer writer = new FileWriter(file, true);
		ColumnPositionMappingStrategy<Data> mappingStrategy = new ColumnPositionMappingStrategy<Data>();
		mappingStrategy.setType(Data.class);
		String[] columns = new String[] { "id", "zipCode", "totPopulation", "medianAge", "totMales", "totFemales",
				"totHouseholds", "avgHouseholdSize" };
		mappingStrategy.setColumnMapping(columns);
		StatefulBeanToCsvBuilder<Data> builder = new StatefulBeanToCsvBuilder<Data>(writer);
		StatefulBeanToCsv<Data> beanWriter = builder.withMappingStrategy(mappingStrategy).build();
		try {
			beanWriter.write(c);
		} catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
			e.printStackTrace();
		}
		writer.close();
	}


	/**
	 * Update CSV file when changing a Data entity
	 * @param list the list of Data to write to csv with the Data updated
	 * @throws IOException
	 */
	public static void updateCsv(List<Data> list) throws IOException {
		Writer writer = new FileWriter(file);
		ColumnPositionMappingStrategy<Data> mappingStrategy = new ColumnPositionMappingStrategy<Data>();
		mappingStrategy.setType(Data.class);
		String[] columns = new String[] { "id", "zipCode", "totPopulation", "medianAge", "totMales", "totFemales",
				"totHouseholds", "avgHouseholdSize" };
		mappingStrategy.setColumnMapping(columns);
		StatefulBeanToCsvBuilder<Data> builder = new StatefulBeanToCsvBuilder<Data>(writer);
		StatefulBeanToCsv<Data> beanWriter = builder.withMappingStrategy(mappingStrategy).build();
		
		for (Data c : list) {
			try {
				beanWriter.write(c);
			} catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
				e.printStackTrace();
			}
		}
		writer.close();
	}

	/**
	 * Delete a Data entity from file
	 * @param c the Data to be deleted
	 */
	public static void deleteFromCsv(Data c) {

		try {
			List<String[]> myEntries = new ArrayList<>();
			CSVReader reader = new CSVReader(new FileReader(file));
			myEntries = reader.readAll();
			List<String[]> toRemove = new ArrayList<>();
			
			for (String[] s :myEntries) {
				if ((Long.parseLong(s[0])) == c.getId()) {
					toRemove.add(s);
				}
			}
			myEntries.removeAll(toRemove);
			CSVWriter writer = new CSVWriter(new FileWriter(file));
			for (String[] row : myEntries) {
				writer.writeNext(row);
			}
			reader.close();
			writer.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}
	
	
	
	/**
	 * Write a list of Crimes to the CSV file
	 * @param crimes list of crimes to write to CSV file
	 * @param repository the repository object that saves the crimes
	 */
	/*@Transactional
	public static void writeToDb(List<Crime> crimes, CrimeRepository repository) {	
		repository.saveAll(crimes);
	}*/
}
