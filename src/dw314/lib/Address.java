package dw314.lib;

import java.io.Serializable;

/**
 * Defines an Address type.
 * 
 * @author Caroline Castonguay-Boisvert
 * @author Kim Parisé
 * @author Natacha Gabbamonte
 * 
 * @since 1.5 12/10/2011
 */

public class Address implements Serializable {

	private String city;
	private String civicNumber;
	private String province;
	private String postalCode;
	private String streetName;

	private static final long serialVersionUID = 4203146887L;

	/**
	 * Initializes an Address object.
	 * 
	 * @param addressData
	 *            Accepts a variable number of String values that will be used
	 *            to initialize the address object. The values must be passed as
	 *            - civic number, street name, city, province, postal code.
	 * @throws IllegalArgumentException
	 *             if any of the address data is null.
	 */

	public Address(String... addressData) {

		if (addressData.length > 0)
			setCivicNumber(addressData[0]);
		else
			civicNumber = "";

		if (addressData.length > 1)
			setStreetName(addressData[1]);
		else
			streetName = "";

		if (addressData.length > 2)
			setCity(addressData[2]);
		else
			city = "";

		if (addressData.length > 3)
			setProvince(addressData[3]);
		else
			province = "";

		if (addressData.length > 4)
			setPostalCode(addressData[4]);
		else
			postalCode = "";

	}

	/**
	 * This returns a string address of the Address object.
	 * 
	 * @return the address
	 */
	public String getAddress() {
		return (civicNumber
				+ (streetName.equals("") ? "" : (" " + streetName + "\n"))
				+ city + (province.equals("") ? "" : (", " + province)) + (postalCode
				.equals("") ? "" : ("\n" + postalCode)));
	}

	/**
	 * Returns the city.
	 * 
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Returns the civic number.
	 * 
	 * @return the civic number
	 */
	public String getCivicNumber() {
		return civicNumber;
	}

	/**
	 * Returns the province.
	 * 
	 * @return the province
	 */
	public String getProvince() {
		return province;
	}

	/**
	 * Returns the postal code.
	 * 
	 * @return the postal code
	 */
	public String getPostalCode() {
		return postalCode;
	}

	/**
	 * Returns the street name.
	 * 
	 * @return the street name
	 */
	public String getStreetName() {
		return streetName;
	}

	/**
	 * Sets the city.
	 * 
	 * @param city
	 *            the city
	 * @throws IllegalArgumentException
	 *             if the city is null.
	 */
	public void setCity(String city) {
		if (city == null)
			throw new IllegalArgumentException("Invalid city.");
		else
			this.city = city;
	}

	/**
	 * Sets the civic number.
	 * 
	 * @param civicNumber
	 *            the civic number
	 * @throws IllegalArgumentException
	 *             if the civic number is null.
	 */
	public void setCivicNumber(String civicNumber) {
		if (civicNumber == null)
			throw new IllegalArgumentException("Invalid civic number.");
		else
			this.civicNumber = civicNumber;
	}

	/**
	 * Sets the province.
	 * 
	 * @param province
	 *            the province
	 * @throws IllegalArgumentException
	 *             if the province is null.
	 */
	public void setProvince(String province) {
		if (province == null)
			throw new IllegalArgumentException("Invalid province.");
		else
			this.province = province;
	}

	/**
	 * Sets the postal code.
	 * 
	 * @param postalCode
	 *            the postal code
	 * @throws IllegalArgumentException
	 *             if the postal code is null.
	 */
	public void setPostalCode(String postalCode) {
		if (postalCode == null)
			throw new IllegalArgumentException("Invalid postal code.");
		else
			this.postalCode = postalCode;
	}

	/**
	 * Sets the street name.
	 * 
	 * @param streetName
	 *            the street name
	 * @throws IllegalArgumentException
	 *             if the street name is null.
	 */
	public void setStreetName(String streetName) {
		if (streetName == null)
			throw new IllegalArgumentException("Invalid street name.");
		else
			this.streetName = streetName;
	}

	/**
	 * Returns a string of the current state of the object.
	 * 
	 * @return a string representing the current state of the object.
	 */
	@Override
	public String toString() {
		return (civicNumber + "*" + streetName + "*" + city + "*" + province
				+ "*" + postalCode);
	}

}
