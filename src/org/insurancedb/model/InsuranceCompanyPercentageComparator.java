package org.insurancedb.model;

import java.util.*;

/**
 * InsuranceCompanyNameComparator implements the Comparator interface
 * and compares to InsuranceCompany objects according to their broker
 * percentage.
 * 
 * @see Comparator
 */
public class InsuranceCompanyPercentageComparator implements Comparator<InsuranceCompany> {

	/**
	 * Compares two InsuranceCompany objects according to their broker
	 * percentage.
	 *
	 * @param ic1 First InsuranceCompany
	 * @param ic2 Second InsuranceCompany
	 * @return returns -1 if the first percentage is higher, else -1. 0 if they are equal.
	 */
	public int compare(InsuranceCompany ic1, InsuranceCompany ic2){
		float percentage1 = ic1.getPercentage();
		float percentage2 = ic2.getPercentage();

		if(percentage1 > percentage2){
			return 1;
		} else if(percentage2 > percentage1){
			return -1;
		} else {
			return 0;
		}
	}

}