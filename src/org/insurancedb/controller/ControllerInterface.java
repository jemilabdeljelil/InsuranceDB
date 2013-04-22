package org.insurancedb.controller;

/**
 * ControllerInterface represents the main interface,
 * which should be implemented in all Controller classes.
 */
public interface ControllerInterface {
	
	void addInsuranceCompany();

	void updateInsuranceCompany(int i, String[] data);

	void deleteInsuranceCompany(int i);

	void searchInsuranceCompanies(String query);

	void selectInsuranceCompany(int i);

}