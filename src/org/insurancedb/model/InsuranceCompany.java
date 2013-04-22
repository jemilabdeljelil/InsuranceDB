package org.insurancedb.model;

/**
 * InsuranceCompany holds all data for a single InsuranceCompany.
 * The class is immutable and can only be instantiated via the static
 * method createInsuranceCompany.
 * <p>
 * Implements Comparable<InsuranceCompany> to enable natural sorting.
 *
 * @see Comparable
 */
public class InsuranceCompany implements Comparable<InsuranceCompany>{

	private String 	companyName,
					url,
					insuranceTypes,
					generalDescription,
					telephone;
	private float	percentage;
	private int		id;

	/**
	 * Private constructor, can only be invoked from static method createInsuranceCompany
	 *
	 * @param	id	unique InsuranceCompany identifier
	 * @param	cn	company name
	 * @param	tel	telephone number
	 * @param	url	website
	 * @param	it	insurance types
	 * @param	per	broker percentage
	 * @param	gd	general description
	 */
	private InsuranceCompany(int id, String cn, String tel, String url, String it, Float per, String gd){
		this.id = id;
		this.companyName = cn;
		this.url = url;
		this.insuranceTypes = it;
		this.generalDescription = gd;
		this.telephone = tel;
		this.percentage = per;
	};

	/**
	 * A public static method which creates a new InsuranceCompany
	 *
	 * @param	id	unique InsuranceCompany identifier
	 * @param	cn	company name
	 * @param	tel	telephone number
	 * @param	url	website
	 * @param	it	insurance types
	 * @param	per	broker percentage
	 * @param	gd	general description
	 */
	public static InsuranceCompany createInsuranceCompany(int id, String cn, String tel, String url, String it, Float per, String gd){
		return new InsuranceCompany(id, cn, tel, url, it, per, gd);
	};

	/**
	 * Gets the company's ID
	 *
	 * @return unique InsuranceCompany identifier
	 */
	public int getId(){
		return this.id;
	}

	/**
	 * Gets the company's name
	 *
	 * @return company name String
	 */
	public String getCompanyName(){
		return this.companyName;
	}

	/**
	 * Gets the company's website
	 *
	 * @return website String
	 */
	public String getUrl(){
		return this.url;
	}

	/**
	 * Gets the company's insurance types
	 *
	 * @return insurance types String
	 */
	public String getInsuranceTypes(){
		return this.insuranceTypes;
	}

	/**
	 * Gets the company's general description
	 *
	 * @return general description String
	 */
	public String getGeneralDescription(){
		return this.generalDescription;
	}

	/**
	 * Gets the company's telephone number
	 *
	 * @return telephone number String
	 */
	public String getTelephone(){
		return this.telephone;
	}

	/**
	 * Gets the company's broker percentage
	 *
	 * @return broker percentage float
	 */
	public float getPercentage(){
		return this.percentage;
	}

	/**
	 * Returns a String with all
	 * InsuranceCompany data information.
	 *
	 * @return String with InsuranceCompany data
	 */
	public String toString(){
		return 	"Company: \t\t" + this.companyName + "\n" +
				"Telephone: \t\t" + this.telephone + "\n" +
				"Web Adress: \t\t" + this.url + "\n" +
				"Types of insurance: \t" + this.insuranceTypes + "\n" +
				"Broker percentage: \t" + this.percentage + "\n" +
				"General Description: \t" + this.generalDescription + "\n";
	};

	/**
	 * Test equalitiy of two InsuranceCompany objects
	 *
	 * @return returns true if the InsuranceCompany objects have the same ID, else false.
	 */
	public boolean equals(Object o){
		if(o == this){
			return true;
		}
		if((o == null) || !(o instanceof InsuranceCompany)){
			return false;
		}
		InsuranceCompany insuranceCompany = (InsuranceCompany)o;
		if(this.getId() == insuranceCompany.getId()){
			return true;
		} else {
			return false;
		}
	};
	
	/**
	 * Implements the natural sorting of InsuranceCompany
	 * according to their unique identifier ID
	 *
	 * @return returns 1 if the first company has a greater ID, -1 if lower and 0 if they have the same ID
	 */
	public int compareTo(InsuranceCompany ic){
		if(this.getId() > ic.getId()){
			return 1;
		} else if(ic.getId() > this.getId()){
			return -1;
		} else {
			return 0;
		}
	}

}