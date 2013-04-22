package org.insurancedb.data;

/**
 * DuplicateIndexFound is a custom exception which should be thrown
 * if a record already exists in the database
 * 
 * @see Exception
 */
public class DuplicateIndexException extends Exception {

	/**
	 * Default DuplicateIndexException constructor
	 */
	public DuplicateIndexException(){
		this("The same record already exists!\nEdit/Delete the new record first, before adding a new one.");
	}

	/**
	 * DuplicateIndexException constructor with custom String
	 *
	 * @param s String custom exeption message
	 */
	public DuplicateIndexException(String s){
		super(s);
	}

}