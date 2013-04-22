package org.insurancedb.view.gui;

/**
 * InsuranceCompanyErrorObserver manages the view's error messages
 */
public interface InsuranceCompanyErrorObserver {

	/**
	 * Shows an error message
	 *
	 * @param t Throwable object
	 */
	public void showErrorMsg(Throwable t);
	
}