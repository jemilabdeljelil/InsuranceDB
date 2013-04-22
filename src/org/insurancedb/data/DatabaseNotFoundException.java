package org.insurancedb.data;

/**
 * DatabaseNotFoundException is a custom exception which should be thrown
 * if the defined database is not available.
 * 
 * @see Exception
 */
public class DatabaseNotFoundException extends Exception {

	/**
	 * Default DatabaseNotFoundException constructor
	 */
	public DatabaseNotFoundException(){
		this("Database Not Found\nPlease check your database location and restart the application!");
	}

	/**
	 * DatabaseNotFoundException constructor with custom String
	 *
	 * @param s String custom exeption message
	 */
	public DatabaseNotFoundException(String s){
		super(s);
	}

}