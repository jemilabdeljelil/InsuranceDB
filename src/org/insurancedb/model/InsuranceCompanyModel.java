package org.insurancedb.model;

import org.insurancedb.data.*;
import org.insurancedb.view.gui.InsuranceCompanyEditPanelObserver;
import org.insurancedb.view.gui.InsuranceCompanyTableObserver;
import org.insurancedb.view.gui.InsuranceCompanyErrorObserver;
import java.util.*;

/**
 * InsuranceCompanyModel represents the main business logic and data.
 * It implements the InsuranceCompanyModelInterface. It uses the Strategy Pattern
 * to hold a sorting strategy and Observer Pattern to notify registered Observers (View).
 * 
 * @see InsuranceCompanyModelInterface
 * @see InsuranceCompany
 */
public class InsuranceCompanyModel implements InsuranceCompanyModelInterface {
	
	private InsuranceCompanyDAO dao;
	private InsuranceCompany currentInsuranceCompany; // InsuranceCompany which is selected
	private Comparator<InsuranceCompany> sortingStrategy;
	private Map<Integer, InsuranceCompany> insuranceCompanies;
	private Map<Integer, InsuranceCompany> searchMap;

	private ArrayList<Object> insuranceCompanyEditPanelObservers = new ArrayList<Object>();
	private ArrayList<Object> insuranceCompanyTableObservers = new ArrayList<Object>();
	private ArrayList<Object> insuranceCompanyErrorObservers = new ArrayList<Object>();

	/**
	 * The constructor sets the data access object
	 * which is retreived as argument and calls the
	 * initialize method.
	 *
	 * @param dao retrieves a data access object
	 */
	public InsuranceCompanyModel(InsuranceCompanyDAO dao){
		this.dao = dao;
		initialize();
	}

	/**
	 * The initialize method uses the data access object to
	 * retreive all InsuranceCompany records of the database.
	 * It then creates InsuranceCompany objects and stores them
	 * in a map with a unique identifier.
	 */
	private void initialize(){
		TreeMap<Integer, InsuranceCompany> tempInsuranceCompanies = new TreeMap<Integer, InsuranceCompany>();
		int i = 0;
		for(String[] bits : dao.readAllRecords()){
			// Read string arrays, create insurance companies and add them to the treemap
			if(bits != null){
				InsuranceCompany ic = InsuranceCompany.createInsuranceCompany(++i,bits[0],bits[1],bits[2],bits[3],Float.parseFloat(bits[4]),bits[5]);
				tempInsuranceCompanies.put(i, ic);
			} else {
				i++;
			}
		}
		insuranceCompanies = tempInsuranceCompanies;
	}

	/**
	 * Sets the currentInsuranceCompany to the InsuranceCompany
	 * with the given ID. Then notifies all EditPanel observers
	 * to update.
	 *
	 * @param i unique InsuranceCompany identifier
	 */
	public void setCurrentInsuranceCompany(int i){
		currentInsuranceCompany = insuranceCompanies.get(i);
		notifyInsuranceCompanyEditPanelObservers();
	}

	/**
	 * Gets the currentInsuranceCompany
	 *
	 * @return currentInsuranceCompany
	 */
	public InsuranceCompany getCurrentInsuranceCompany(){
		return currentInsuranceCompany;
	}

	/**
	 * Calls the data access object to retreive the raw data of
	 * a single record. It then returns a String array with the data.
	 *
	 * @param i unique InsuranceCompany identifier
	 * @return returns a String array containing InsuranceCompany data
	 */
	public String[] readInsuranceCompany(int i){
		try {
			return dao.readRecord(i);
		} catch (RecordNotFoundException e) {
			notifyInsuranceCompanyErrorObservers(e);
		}
		return null;
	}

	/**
	 * Gets the InsuranceCompany object of the insuranceCompanies map
	 * accord to the given parameter i.
	 *
	 * @param i unique InsuranceCompany identifier
	 * @return returns an InsuranceCompany object
	 */
	public InsuranceCompany getInsuranceCompany(int i){
		return insuranceCompanies.get(i);
	}

	/**
	 * Adds an InsuranceCompany record to the database and to
	 * the InsuranceCompanies map. After adding a new InsuranceCompany
	 * it automatically becomes currentInsuranceCompany and the method
	 * notifies all Table observers.
	 *
	 * @param data String array containing InsuranceCompany raw data
	 */
	public void addInsuranceCompany(String[] data){
		try {
			// Add it to db
			int i = dao.addRecord(data);
			// Add it to map with the appropriate id
			if(i != -1){
				InsuranceCompany ic = InsuranceCompany.createInsuranceCompany(i,data[0],data[1],data[2],data[3],Float.parseFloat(data[4]),data[5]);
				insuranceCompanies.put(i, ic);
				currentInsuranceCompany = ic;
				notifyInsuranceCompanyTableObservers();
			}
		} catch (DuplicateIndexException e) {
			notifyInsuranceCompanyErrorObservers(e);
		}
	}

	/**
	 * Updates a record accord to the given arguments i and data.
	 * It first updates the record in the database and, if successful, then reads
	 * the updated record from the database in, to create a new InsuranceCompany object.
	 * Finally all Table observers are notified.
	 *
	 * @param i unique InsuranceCompany identifier
	 * @param data String array containing InsuranceCompany raw data
	 */
	public void updateInsuranceCompany(int i, String[] data){
		try {
			// Update record
			dao.updateRecord(i, data);
			// Read record, create new InsuranceCompany instance and overwrite old InsuranceCompany in map
			String[] bits = readInsuranceCompany(i);
			InsuranceCompany insuranceCompany = InsuranceCompany.createInsuranceCompany(i,bits[0],bits[1],bits[2],bits[3],Float.parseFloat(bits[4]),bits[5]);
			insuranceCompanies.put(i, insuranceCompany);
			// Notify all table observers
			notifyInsuranceCompanyTableObservers();
		} catch (RecordNotFoundException e) {
			notifyInsuranceCompanyErrorObservers(e);
		}
	}

	/**
	 * Deletes an InsuranceCompany from the database and from the map.
	 * Notifies all Table observers.
	 *
	 * @param i unique InsuranceCompany identifier
	 */
	public void deleteInsuranceCompany(int i){
		try {
			// Remove it from db
			dao.deleteRecord(i);
			// Remove it from map
			insuranceCompanies.remove(i);
			notifyInsuranceCompanyTableObservers();
		} catch (RecordNotFoundException e) {
			notifyInsuranceCompanyErrorObservers(e);
		}
	}

	/**
	 * Retreives a search query String. It parses the String,
	 * calls the data access object and passes the parsed search
	 * critera and boolean search argument (1 for OR, 0 for AND).
	 * It then populates the models searchMap.
	 *
	 * @param query search query String
	 */
	public void searchInsuranceCompanies(String query){

		// Parse the criteria and store them in ArrayList
		int bool = 1;
		query = query.trim();
		query = query.toLowerCase();
		String[] criteria = query.split(" ");
		ArrayList<String> slicedCriteria = new ArrayList<String>();
		for(String s : criteria){
			if(s.equals("and")){
				bool = 0;
			} else if(s.equals("or")){
				bool = 1;
			} else {
				slicedCriteria.add(s);
			}
		}

		// Create a String array from the ArrayList slicedCriteria
		String[] newCriteria = new String[slicedCriteria.size()];
		newCriteria = slicedCriteria.toArray(newCriteria);

		// Call the data access obect search method
		int[] ids = dao.findRecords(newCriteria, bool);

		// Populate search list
		searchMap = new TreeMap<Integer, InsuranceCompany>();
		for(int i : ids){
			searchMap.put(i, getInsuranceCompany(i));
		}

	}

	/**
	 * Gets the models main insuranceCompanies map.
	 *
	 * @return insuranceCompanies map
	 */
	public Map<Integer, InsuranceCompany> getInsuranceCompanies(){
		return insuranceCompanies;
	}

	/**
	 * Gets the models search map.
	 *
	 * @return searchMap map
	 */
	public Map<Integer, InsuranceCompany> getSearchMap(){
		return searchMap;
	}

	/**
	 * Sets the sorting strategy according to a given 
	 * InsuranceCompany Comparator.
	 *
	 * @param comparator Comparator<InsuranceCompany>
	 */
	public void setSortingStrategy(Comparator<InsuranceCompany> comparator){
		sortingStrategy = comparator;
	}

	/**
	 * Gets the models sorting strategy.
	 *
	 * @return sortingStrategy
	 */
	public Comparator<InsuranceCompany> getSortingStrategy(){
		return sortingStrategy;
	}

	/**
	 * Register a new EditPanel observer
	 *
	 * @param o InsuranceCompanyEditPanelObserver
	 */
	public void registerObserver(InsuranceCompanyEditPanelObserver o){
		insuranceCompanyEditPanelObservers.add(o);
	}

	/**
	 * Remove an EditPanel observer
	 *
	 * @param o InsuranceCompanyEditPanelObserver
	 */
	public void removeObserver(InsuranceCompanyEditPanelObserver o){
		int i = insuranceCompanyEditPanelObservers.indexOf(o);
		if(i >= 0){
			insuranceCompanyEditPanelObservers.remove(i);
		}
	}

	/**
	 * Notify all Edit Panel observers
	 */
	public void notifyInsuranceCompanyEditPanelObservers(){
		for(int i = 0; i < insuranceCompanyEditPanelObservers.size(); i++){
			InsuranceCompanyEditPanelObserver observer = (InsuranceCompanyEditPanelObserver)insuranceCompanyEditPanelObservers.get(i);
			observer.updateEditPanel();
		}
	}

	/**
	 * Register a new Table observer
	 *
	 * @param o InsuranceCompanyTableObserver
	 */
	public void registerObserver(InsuranceCompanyTableObserver o){
		insuranceCompanyTableObservers.add(o);
	}

	/**
	 * Remove a Table observer
	 *
	 * @param o InsuranceCompanyTableObserver
	 */
	public void removeObserver(InsuranceCompanyTableObserver o){
		int i = insuranceCompanyTableObservers.indexOf(o);
		if(i >= 0){
			insuranceCompanyTableObservers.remove(i);
		}
	}

	/**
	 * Notify all Table observers
	 */
	public void notifyInsuranceCompanyTableObservers(){
		for(int i = 0; i < insuranceCompanyTableObservers.size(); i++){
			InsuranceCompanyTableObserver observer = (InsuranceCompanyTableObserver)insuranceCompanyTableObservers.get(i);
			observer.updateTable();
		}
	}

	/**
	 * Register a new Error observer
	 *
	 * @param o InsuranceCompanyErrorObserver
	 */
	public void registerObserver(InsuranceCompanyErrorObserver o){
		insuranceCompanyErrorObservers.add(o);
	}

	/**
	 * Remove an Error observer
	 *
	 * @param o InsuranceCompanyErrorObserver
	 */
	public void removeObserver(InsuranceCompanyErrorObserver o){
		int i = insuranceCompanyErrorObservers.indexOf(o);
		if(i >= 0){
			insuranceCompanyErrorObservers.remove(i);
		}
	}

	/**
	 * Notify all Error observers
	 */
	public void notifyInsuranceCompanyErrorObservers(Throwable t){
		for(int i = 0; i < insuranceCompanyErrorObservers.size(); i++){
			InsuranceCompanyErrorObserver observer = (InsuranceCompanyErrorObserver)insuranceCompanyErrorObservers.get(i);
			observer.showErrorMsg(t);
		}
	}

}