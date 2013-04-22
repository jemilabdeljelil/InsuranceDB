package org.insurancedb.view.gui;

import javax.swing.table.*;
import java.util.*;
import org.insurancedb.model.*;

/**
 * InsuranceCompanyTableModel is the custom AbstractTableModel
 * for the InsuranceCompanyView. It implements the possibility of sorting.
 * 
 * @see AbstractTableModel
 * @see InsuranceCompanyView
 */
class InsuranceCompanyTableModel extends AbstractTableModel {
	
	private String[] columnNames = {"ID","Company","Telephone","Website","Insurance Types","Broker Percentage"};
	private ArrayList<String[]> insuranceCompanies;
	
	/**
	 * The constructor calls the table model's
	 * update method with the given data
	 * 
	 * @param data A map of InsuranceCompany objects
	 */
	public InsuranceCompanyTableModel(Map<Integer, InsuranceCompany> data){
		update(data, null);
	}
	
	/**
	 * Gets the number of table columns
	 * 
	 * @return int table columns
	 */
	public int getColumnCount() {
		return this.columnNames.length;
	}

	/**
	 * Gets the number of table rows
	 * 
	 * @return int table rows
	 */
	public int getRowCount() {
		return this.insuranceCompanies.size();
	}

	/**
	 * Gets the column names
	 * 
	 * @return String column names
	 */
	public String getColumnName(int column) {
		return columnNames[column];
	}

	/**
	 * Gets the value at a specific location
	 * 
	 * @param int row number
	 * @param int column number
	 * @return Object table Object
	 */
	public Object getValueAt(int row, int column) {
		String[] rowData = insuranceCompanies.get(row);
		return rowData[column];
	}

	/**
	 * Update the table models data
	 * 
	 * @param data InsuranceCompany Map
	 * @param comparator InsuranceCompany Comparator
	 */
	public void update(Map<Integer, InsuranceCompany> data, Comparator<InsuranceCompany> comparator){
		insuranceCompanies = convertInsuranceCompanies(data, comparator); // Set the tables data
		Collections.reverse(insuranceCompanies); // Show highest values always on top
		this.fireTableDataChanged(); // Notify the table that the data has changed
	}

	/**
	 * Converts a Map to an ArrayList suited for the table model.
	 * That way sorting can be enabled.
	 * 
	 * @param data InsuranceCompany Map
	 * @param comparator InsuranceCompany Comparator
	 * @return returns an ArrayList which can be sorted
	 */
	private ArrayList<String[]> convertInsuranceCompanies(Map<Integer, InsuranceCompany> data, Comparator<InsuranceCompany> comparator){

		ArrayList<String[]> tempInsuranceCompanies = new ArrayList<String[]>();
		List<InsuranceCompany> list = new ArrayList<InsuranceCompany>(data.values()); // Convert the Map to an ArrayList

		if(comparator == null){
			Collections.sort(list);
		} else {
			Collections.sort(list, comparator);
		}

		// Choose the needed values and populate the temporary list
		for(InsuranceCompany company : list){

			String[] columns = {
				String.valueOf(company.getId()),
				company.getCompanyName(),
				company.getTelephone(),
				company.getUrl(),
				company.getInsuranceTypes(),
				String.valueOf(company.getPercentage())
			};

			tempInsuranceCompanies.add(columns);
		}

		return tempInsuranceCompanies;

	}

}