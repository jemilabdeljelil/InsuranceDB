package org.insurancedb.controller;

import org.insurancedb.model.*;
import org.insurancedb.view.gui.*;

/**
 * InsuranceCompanyController implements the ControllerInterface
 * and is responsibile for calling the appropriate methods in the
 * InsuranceCompanyModel.
 *
 * @see InsuranceCompanyModel
 * @see ControllerInterface
 */
public class InsuranceCompanyController implements ControllerInterface {
	
	private InsuranceCompanyModelInterface model;
	private InsuranceCompanyView view;

	/**
	 * Sets up a new InsuranceCompanyController instance and
	 * sets the controllers model to the given model.
	 * Creates a new InsuranceCompanyView and initializes the view's
	 * table.
	 *
	 * @param model given InsuranceCompanyInterface
	 */
	public InsuranceCompanyController(InsuranceCompanyModelInterface model){

		this.model = model;
		view = new InsuranceCompanyView(this, model);
		view.loadTable();

	}

	/**
	 * Creates a new String array with empty data to be
	 * added in the model. Notifies the model to add a new
	 * InsuranceCompany with the given data.
	 */
	public void addInsuranceCompany(){

		String[] data = {"-","-","-","-","0.0","-"};
		model.addInsuranceCompany(data);

	}

	/**
	 * Notifies the model to search for a specific InsuranceCompany record
	 * and updates it's data
	 *
	 * @param  i  	InsuranceCompany ID
	 * @param  data	StringArray containing the new data
	 */
	public void updateInsuranceCompany(int i, String[] data){

		model.updateInsuranceCompany(i, data);

	}

	/**
	 * Notifies the model to delete an InsuranceCompany according to a given identifier
	 * @param i InsuranceCompany ID
	 */
	public void deleteInsuranceCompany(int i){

		model.deleteInsuranceCompany(i);
		
	}

	/**
	 * Notifies the model to select a specific InsuranceCompany
	 *
	 * @param i InsuranceCompany ID
	 */
	public void selectInsuranceCompany(int i){

		model.setCurrentInsuranceCompany(i);

	}

	/**
	 * Notifies the model to search for a set of InsuranceCompanies according to
	 * a given search query
	 *
	 * @param query search query given as String
	 */
	public void searchInsuranceCompanies(String query){

		model.searchInsuranceCompanies(query);

	}

}