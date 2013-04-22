package org.insurancedb.model;

import java.util.*;

/**
 * InsuranceCompanyNameComparator implements the Comparator interface
 * and compares to InsuranceCompany objects according to their company
 * name. It implements the compareTo algorithm of String to provide
 * lexicographical ordering.
 * 
 * @see Comparator
 */
public class InsuranceCompanyNameComparator implements Comparator<InsuranceCompany> {

	/**
	 * Compares two InsuranceCompany objects according to their company
	 * name.
	 *
	 * @param ic1 First InsuranceCompany
	 * @param ic2 Second InsuranceCompany
	 * @return returns -1 if the first InsuranceCompany preceeds the other, else -1. 0 if they are equal.
	 */
	public int compare(InsuranceCompany ic1, InsuranceCompany ic2){
		String name1 = ic1.getCompanyName();
		String name2 = ic2.getCompanyName();

		if(name1.compareTo(name2) > 0){
			return -1;
		} else if(name2.compareTo(name1) > 0){
			return 1;
		} else {
			return 0;
		}
	}

}