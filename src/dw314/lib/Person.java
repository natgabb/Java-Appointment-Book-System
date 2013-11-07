package dw314.lib;

import java.io.Serializable;

/**
 * Defines a Person object.
 * 
 * @author Caroline Castonguay-Boisvert
 * @author Kim Parisé
 * @author Natacha Gabbamonte
 * 
 * @since 1.5 12/10/2011
 */

public class Person implements Serializable{

	private Name name;
	private Address address;
	private String telephoneNumber;

	private static final long  serialVersionUID = 4203146887L;
	
	/**
	 * This constructor receives a first and last name.
	 * 
	 * @param firstName
	 *            the first name
	 * @param lastName
	 *            the last name
	 * @throws IllegalArgumentException
	 *             If the first name of the last name are invalid
	 */
	public Person(String firstName, String lastName) {
		this(firstName, lastName, "", new Address());
	}

	/**
	 * This constructor receives a first name, a last name and a phone number.
	 * 
	 * @param firstName
	 *            the first name
	 * @param lastName
	 *            the last name
	 * @param telephoneNumber
	 *            the phone number
	 * @throws IllegalArgumentException
	 *             If the first name of the last name are invalid
	 */

	public Person(String firstName, String lastName, String telephoneNumber) {
		this(firstName, lastName, telephoneNumber, new Address());
	}

	/**
	 * This constructor receives a first and last name and an Address object.
	 * 
	 * @param firstName
	 *            the first name
	 * @param lastName
	 *            the last name
	 * @param address
	 *            the Address object
	 * @throws IllegalArgumentException
	 *             If the first name of the last name are invalid
	 */
	public Person(String firstName, String lastName, Address address) {
		this(firstName, lastName, "", address);
	}

	/**
	 * This constructor receives a first name, a last name, a phone number and
	 * an Address object.
	 * 
	 * @param firstName
	 *            the first name
	 * @param lastName
	 *            the last name
	 * @param telephoneNumber
	 *            the phone number
	 * @param address
	 *            the Address object
	 * 
	 * @throws IllegalArgumentException
	 *             If the first name of the last name are invalid
	 */
	public Person(String firstName, String lastName, String telephoneNumber,
			Address address) {

		name = new Name();
		setName(firstName, lastName);
		this.address = new Address(address.getCivicNumber(),
				address.getStreetName(), address.getCity(),
				address.getProvince(), address.getPostalCode());
		this.telephoneNumber = telephoneNumber;
	}

	/**
	 * Returns a copy of the person's address.
	 * 
	 * @return the person's address
	 */
	public Address getAddress() {
		Address address = new Address(this.address.getCivicNumber(),
				this.address.getStreetName(), this.address.getCity(),
				this.address.getProvince(), this.address.getPostalCode());
		return address;
	}

	/**
	 * Returns a copy of the person's name.
	 * 
	 * @return the person's name
	 */
	public Name getName() {
		Name name = new Name(this.name.getFirstName(), this.name.getLastName());
		return name;
	}

	/**
	 * Returns the telephone number.
	 * 
	 * @return the telephone number
	 */
	public String getTelephoneNumber() {
		return telephoneNumber;
	}

	/**
	 * Sets the person's address.
	 * 
	 * @param address
	 *            the address object
	 */
	public void setAddress(Address address) {

		this.address.setCity(address.getCity());
		this.address.setCivicNumber(address.getCivicNumber());
		this.address.setPostalCode(address.getPostalCode());
		this.address.setProvince(address.getProvince());
		this.address.setStreetName(address.getStreetName());
	}

	/**
	 * Sets the person's name.
	 * 
	 * @param firstName
	 *            the first name
	 * @param lastName
	 *            the last name
	 * @throws IllegalArgumentException
	 *             if the first or last name is invalid
	 */

	public void setName(String firstName, String lastName) {
		if (firstName.equals(""))
			throw new IllegalArgumentException("Invalid first name");
		if (lastName.equals(""))
			throw new IllegalArgumentException("Invalid last name");
		name.setFirstName(firstName);
		name.setLastName(lastName);
	}

	/**
	 * Sets the telephone number.
	 * 
	 * @param telephoneNumber
	 *            the telephone number.
	 */

	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}

	/**
	 * A string with both the person's name and address.
	 * 
	 * @return a String containing both the person's name and address
	 */
	@Override
	public String toString() {
		return (name.toString() + "*" + (address == null ? "" : address
				.toString()));
	}

}
