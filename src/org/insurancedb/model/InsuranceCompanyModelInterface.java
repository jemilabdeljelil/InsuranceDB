package org.insurancedb.model;

import org.insurancedb.view.gui.InsuranceCompanyEditPanelObserver;
import org.insurancedb.view.gui.InsuranceCompanyTableObserver;
import org.insurancedb.view.gui.InsuranceCompanyErrorObserver;
import java.util.*;

/**
 * InsuranceCompanyModelInterface represents all methods which
 * should be implemented by an InsuranceCompanyModel class.
 *
 * @see InsuranceCompanyModel
 */
public interface InsuranceCompanyModelInterface {

	public String[] readInsuranceCompany(int i);

	public void setCurrentInsuranceCompany(int i);

	public InsuranceCompany getCurrentInsuranceCompany();

	public InsuranceCompany getInsuranceCompany(int i);

	public void addInsuranceCompany(String[] data);
	
	public void updateInsuranceCompany(int i, String[] data);

	public void deleteInsuranceCompany(int i);

	public void searchInsuranceCompanies(String query);

	public void setSortingStrategy(Comparator<InsuranceCompany> comparator);

	public Comparator<InsuranceCompany> getSortingStrategy();

	public Map<Integer, InsuranceCompany> getSearchMap();

	public Map<Integer, InsuranceCompany> getInsuranceCompanies();

	public void registerObserver(InsuranceCompanyEditPanelObserver o);

	public void removeObserver(InsuranceCompanyEditPanelObserver o);

	public void registerObserver(InsuranceCompanyTableObserver o);

	public void removeObserver(InsuranceCompanyTableObserver o);

	public void registerObserver(InsuranceCompanyErrorObserver o);

	public void removeObserver(InsuranceCompanyErrorObserver o);

}