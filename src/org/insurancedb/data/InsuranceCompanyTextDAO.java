package org.insurancedb.data;

import java.util.*;
import java.io.*;

/**
 * InsuranceCompanyTextDAO is an implementation of the data access object.
 * It uses a text file database to manage records and
 * implements the Singleton pattern.
 * 
 * @see InsuranceCompanyDAO
 */
public class InsuranceCompanyTextDAO {

	// Search query constants
	public static final int AND = 0;
	public static final int OR 	= 1;

	// Database folder and file location constants
	private static final String DS 			  = File.separator;
	private static final String DATA_DIR 	  = System.getProperty("user.dir") + DS + "data";
	private static final String DATA_TEMP_DIR = DATA_DIR + DS + "tmp"; 
	private static final String DB_FILE 	  = "insurance.db";
	private static final String DB_TEMP_FILE  = "insuranceTemp.db";

	// Singleton instance
	private static InsuranceCompanyTextDAO instance;

	private Scanner s;
	private PrintWriter pw;
	private File file;
	private File tempFile;
	private File tempFileDir;

	/**
	 * Private empty construtor
	 */
	private InsuranceCompanyTextDAO() {};

	/**
	 * getInstance() returns the created instance of the class
	 *
	 * @return returns an InsuranceCompanyTextDAO object
	 */
	public static InsuranceCompanyTextDAO getInstance() {
		if(instance == null){
			return new InsuranceCompanyTextDAO();
		} else {
			return instance;
		}
	}

	/**
	 * Sets up all database files. Throws an exception if the
	 * database is not available.
	 */
	public void initialize() throws DatabaseNotFoundException {
		file = new File(DATA_DIR + DS + DB_FILE); 
		tempFile = new File(DATA_TEMP_DIR + DS + DB_TEMP_FILE);
		tempFileDir = new File(DATA_TEMP_DIR);
		if(!file.exists()){
			throw new DatabaseNotFoundException();
		} else if (!tempFileDir.exists()) {
			// If the temporary folder doesn't exist, simply create it
			tempFileDir.mkdir();
		}
	}

	/**
	 * Returns a List of String arrays containing all database records
	 *
	 * @return String array containing database record
	 */
	public List<String[]> readAllRecords() {

		// Set up the scanner
		try {
			s = new Scanner(file);
			s.useDelimiter("\n");
		} catch(Exception e) {
			System.out.println(e);
		}

		List<String[]> list = new ArrayList<String[]>();

		String[] bits = null;
		while(s.hasNext()){
			String line = s.next().trim();
			if(!line.equals("empty")){		// empty is the placeholder if a record was deleted
				bits = line.split(":");
				list.add(bits);
			} else {
				list.add(null);				// add null to the list to ensure proper ID's
			}
		}
		s.close(); // Close the scanner
		return list;
	}

	/**
	 * Reads a single record from the database
	 *
	 * @param recNo record number to read
	 * @return int which represents the id of the record
	 */
	public String[] readRecord(int recNo) throws RecordNotFoundException {
		int i = 0;

		// Set up the scanner
		try {
			s = new Scanner(file);
			s.useDelimiter("\n");
		} catch(Exception e) {
			System.out.println(e);
		}

		String[] bits = null;
		while(s.hasNext()){
			String line = s.next().trim();
			if(++i == recNo){
				bits = line.split(":");
				if(!bits[0].equals("empty")){
					s.close(); // Close the scanner
					return bits;
				} else {
					s.close(); // Close the scanner
					throw new RecordNotFoundException();
				}
			} 
		}
		s.close(); // Close the scanner
		throw new RecordNotFoundException(); // If nothing has been returned, throw an exception
	}

	/**
	 * Adds a record to the database
	 *
	 * @param data String array containing record data
	 * @return int which represents the id of the record
	 */
	public int addRecord(String[] data) throws DuplicateIndexException {
		int i = 0;
		int id = 0;
		boolean added = false;

		// Set up the scanner
		try {
			s = new Scanner(file);
			s.useDelimiter("\n");
		} catch(Exception e) {
			System.out.println(e);
		}

		// Create the temp file
		try {
			tempFile.createNewFile();
		} catch (IOException e) {
			System.out.println(e);
		}

		try {
			pw = new PrintWriter(new FileWriter(tempFile));

			while(s.hasNext()){
				i++;
				String line = s.next().trim();
				if(line.equals(data[0]+":"+data[1]+":"+data[2]+":"+data[3]+":"+data[4]+":"+data[5])){
					id = -1;
					throw new DuplicateIndexException(); // If the exact same data already exists
				} else if (line.equals("empty") && !added) {
					pw.println(data[0]+":"+data[1]+":"+data[2]+":"+data[3]+":"+data[4]+":"+data[5]);
					added = true;
					id = i;
				} else {
					pw.println(line);
				}
			} 
			
			if(!added){
				pw.println(data[0]+":"+data[1]+":"+data[2]+":"+data[3]+":"+data[4]+":"+data[5]);
				id = i + 1; // Last line + 1, otherwise the last entry in the map gets overwritten	
			}

			// Close PrintWriter and Scanner
			pw.close();
			s.close();	

			// Delete old File and rename/move temp fileq
			if(file.delete() && tempFile.renameTo(file)){
				System.out.println("DB was successully updated!");
			} else {
				System.out.println("Error: Temporary DB file could not be deleted/moved");
			}

		} catch (IOException e) {
			System.out.println(e);
		} finally {
			// Close PrintWriter and Scanner
			pw.close();
			s.close();
		}

		return id;

	}

	/**
	 * Deletes a record from the database
	 *
	 * @param recNo record number to delete
	 */
	public void deleteRecord(int recNo) throws RecordNotFoundException {
		int i = 0;
		boolean found = false;

		// Set up the scanner
		try {
			s = new Scanner(file);
			s.useDelimiter("\n");
		} catch(Exception e) {
			System.out.println(e);
		}

		// Create the temp file
		try {
			tempFile.createNewFile();
		} catch (IOException e) {
			System.out.println(e);
		}

		try {
			pw = new PrintWriter(new FileWriter(tempFile));

			while(s.hasNext()){
				String line = s.next().trim();
				if(++i == recNo){
					found = true;
					if(line.equals("empty")){
						throw new RecordNotFoundException();
					} else {
						pw.println("empty");
						System.out.println("Record " + i + " deleted!");
					}
				} else {
					pw.println(line);
				}
			} 

			// Close PrintWriter and Scanner
			pw.close();
			s.close();	

			if(!found){
				throw new RecordNotFoundException();
			} else {
				// Delete old File and rename/move temp file
				if(file.delete() && tempFile.renameTo(file)){
					System.out.println("DB was successully updated!");
				} else {
					System.out.println("Error: Temporary DB file could not be deleted/moved");
				}
			}
		} catch (IOException e) {
			System.out.println(e);
		} finally {
			// Close PrintWriter and Scanner
			pw.close();
			s.close();
		}
	}

	/**
	 * Updates a specific record in the database
	 *
	 * @param recNo record number to update
	 * @param data new record data
	 */
	public void updateRecord(int recNo, String[] data) throws RecordNotFoundException {

		int i = 0;
		boolean edited = false;

		// Set up the scanner
		try {
			s = new Scanner(file);
			s.useDelimiter("\n");
		} catch(Exception e) {
			System.out.println(e);
		}

		// Create the temp file
		try {
			tempFile.createNewFile();
		} catch (IOException e) {
			System.out.println(e);
		}

		try {
			pw = new PrintWriter(new FileWriter(tempFile));

			while(s.hasNext()){
				i++;
				String line = s.next().trim();
				if (i == recNo) {	// If the line equals the record number
					pw.println(data[0]+":"+data[1]+":"+data[2]+":"+data[3]+":"+data[4]+":"+data[5]);
					edited = true;
				} else {
					pw.println(line);
				}
			}

			if(!edited){
				throw new RecordNotFoundException();
			}

			// Close PrintWriter and Scanner
			pw.close();
			s.close();	

			// Delete old File and rename/move temp fileq
			if(file.delete() && tempFile.renameTo(file)){
				System.out.println("Entry was successully updated!");
			} else {
				System.out.println("Error: Temporary DB file could not be deleted/moved");
			}

		} catch (IOException e) {
			System.out.println(e);
		} finally {
			// Close PrintWriter and Scanner
			pw.close();
			s.close();
		}

	}

	/**
	 * Finds records accord to given criteria
	 *
	 * @param criteria String array with search criteria
	 * @param bool int either 0(AND) or 1(OR) as search argument
	 * @return returns an array of int with all found id's
	 */
	public int[] findRecords(String[] criteria, int bool) {

		int i = 0;
		ArrayList<Integer> ids = new ArrayList<Integer>();
		String[] bits = null;
		String insuranceTypes = "";
		boolean contains = true;

		// Set up the scanner
		try {
			s = new Scanner(file);
			s.useDelimiter("\n");
		} catch(Exception e) {
			System.out.println(e);
		}

		while(s.hasNext()){
			i++;
			String line = s.next().trim();
			bits = line.split(":");

			// Check if line is empty
			if(bits.length > 1){
				if(bool == AND){
					insuranceTypes = bits[3].toLowerCase();
					for(String s : criteria){
						contains = true;
						if(!insuranceTypes.contains(s.toLowerCase())){
							contains = false;
							break; // If even one criteria is not the insurance types, break and return false
						}
					}
					if(contains){
						ids.add(i); // If the insurance types contain the criteria, add the ID to the list
					}
				} else {
					// If bool is OR, add all ID's to the list if they contain the criteria
					insuranceTypes = bits[3].toLowerCase();
					for(String s : criteria){
						if(insuranceTypes.contains(s.toLowerCase())){
							if(!ids.contains(i)){
								ids.add(i);
							}
						}
					}
				}
			}

		}

		int[] idArray = new int[ids.size()];

		for(int k = 0; k < idArray.length; k++){
			idArray[k] = ids.get(k);
		}

		s.close(); // Close the scanner

		return idArray;

	}
	
}