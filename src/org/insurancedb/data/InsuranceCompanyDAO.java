package org.insurancedb.data;

import java.util.*;

/**
 * InsuranceCompanyDAO represents the applications
 * data access object. It implements all methods of the
 * DAO interface.
 * <p>
 * It delegates it's responsibilites to a specific DAO implementation.
 * 
 * @see DAO
 */
public class InsuranceCompanyDAO implements DAO {

	// Text database DAO implementation
	private InsuranceCompanyTextDAO dao;
	// InsuranceCompanySqlDAO or InsuranceCompanyXmlDAO would also be possible

	/**
	 * The constructor sets the classes dao
	 * to an instance of the implementation dao.
	 */
	public InsuranceCompanyDAO() {
		dao = InsuranceCompanyTextDAO.getInstance();
	}

	/**
	 * Delegates database initalization to dao
	 */
	public void initialize() throws DatabaseNotFoundException {
		dao.initialize();
	}

	/**
	 * Delegates readAllRecords to dao and returns a List of String arrays
	 * with database records
	 *
	 * @return List of String arrays with all database records
	 */
	public List<String[]> readAllRecords() {
		return dao.readAllRecords();
	}

	/**
	 * Delegates readRecord to dao and returns a String array containing a database
	 * record
	 *
	 * @param recNo record number to read from database
	 * @return String array containing database record
	 */
	public String[] readRecord(int recNo) throws RecordNotFoundException {
		return dao.readRecord(recNo);
	};

	/**
	 * Delegates addRecord to dao and returns an int which
	 * can be used as ID for the record
	 *
	 * @param data String array containing record data
	 * @return int which represents the id of the record
	 */
	public int addRecord(String[] data) throws DuplicateIndexException {
		return dao.addRecord(data);
	};

	/**
	 * Delegates deleteRecord to dao
	 *
	 * @param recNo record number to delete
	 */
	public void deleteRecord(int recNo) throws RecordNotFoundException  {
		dao.deleteRecord(recNo);
	};

	/**
	 * Delegates updateRecord to dao
	 *
	 * @param recNo record number to update
	 * @param data new record data
	 */
	public void updateRecord(int recNo, String[] data) throws RecordNotFoundException {
		dao.updateRecord(recNo, data);
	};

	/**
	 * Delegates findRecords to dao and returns an int array
	 * containing all found record identifiers
	 *
	 * @param criteria String array with search criteria
	 * @param bool int either 0(AND) or 1(OR) as search argument
	 */
	public int[] findRecords(String[] criteria, int bool) {
		return dao.findRecords(criteria, bool);
	};
	
}