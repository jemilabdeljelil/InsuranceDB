package org.insurancedb.data;

// This interface should not be modified. If you wish to extend the list
// of behaviour defined for the data access protocol then you should extend the
// interface appropriately.

public interface DAO {

	public static final int AND = 0; // for use with findRecords method declared below

	public static final int OR = 1;  // for use with findRecords method declared below


	// Returns record data from the database specified by the
	// record identifier supplied. Returns an array where each
	// element is a record field value. If the specified record is not
	// located in the database, then a custom RecordNotFoundException
	// is raised. It is expected that this exception will be
	// handled in program logic, ultimately resulting in a report to
	// the user without exiting the application.

	public String[] readRecord(int recNo) throws RecordNotFoundException;

	// Creates a new record in the database (possibly re-using a
	// deleted record identifier). Inserts the given data, and returns
	// the record identifier of the new record. When generating the new
	// record id, a check is made that it is unique within the database,
	// and if not a custom DuplicateIndexException is generated and
	// thrown. It is expected that this exception must be handled in
	// program logic, ultimately resulting in a report to the user
	// without exiting the application.

	public int addRecord(String[] data) throws DuplicateIndexException;

	// Deletes a record, making the record identifier and database file
	// position available for re-use. Will throw a custom
	// RecordNotFoundException if the supplied record identifier does not
	// correctly map to a unique record index. It is expected that this
	// exception will be handled in program logic, ultimately resulting
	// in a report to the user without exiting the application.

	public void deleteRecord(int recNo) throws RecordNotFoundException;


	// Modifies the fields of a record. The new value for field n
	// appears in data[n]. Will throw a custom RecordNotFoundException
	// if the supplied record identifier does not correctly map to
	// a unique record index. It is expected that this exception will be
	// handled in program logic, ultimately resulting in a report to
	// the user without exiting the application.

	public void updateRecord(int recNo, String[] data) throws RecordNotFoundException;


	// Returns an array of record identifiers that map to records that
	// match the specified search criteria. The array argument should
	// contain a set of string search patterns. The integer argument
	// should be set to either AND or OR as defined by the integer constants
	// defined above in this interface. So, if a logical AND is specified then
	// the data records that match 'all' string patterns in the array should
	// be returned. If a logical OR is specified then the data records that
	// match on any one of the string patterns in the array should be returned.

	public int[] findRecords(String[] criteria, int bool);

}
