package org.insurancedb.data;

/**
 * RecordNotFoundException is a custom exception which should be thrown
 * if a record does not exist in the database.
 * 
 * @see Exception
 */
public class RecordNotFoundException extends Exception {

	/**
	 * Default RecordNotFoundException constructor
	 */
	public RecordNotFoundException(){
		this("No Record found");
	}

	/**
	 * RecordNotFoundException constructor with custom String
	 *
	 * @param s String custom exeption message
	 */
	public RecordNotFoundException(String s){
		super(s);
	}

}