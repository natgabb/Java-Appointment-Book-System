package dw314.lib;

import java.io.Serializable;

/**
 * Defines a Name object.
 * 
 * @author Caroline Castonguay-Boisvert
 * @author Kim Parisé
 * @author Natacha Gabbamonte
 * 
 * @since 1.5 12/10/2011
 */

public class Name implements Serializable{
	private String firstName = "";
	private String lastName = "";

	private static final long  serialVersionUID = 4203146887L;
	
	/**
	 * No parameter constructor.
	 */
	public Name() {
	}

	/**
	 * A two parameter constructor that accepts a first name and a last name.
	 * 
	 * @param firstName
	 *            the first name
	 * @param lastName
	 *            the last name
	 * throws IllegalArgumentException if the first or last name is invalid.           
	 */
	public Name(String firstName, String lastName) {
		setFirstName(firstName);
		setLastName(lastName);
	}

	/**
	 * Returns the first name.
	 * 
	 * @return the first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Returns the object's full name.
	 * 
	 * @return The full name
	 */

	public String getFullName() {
		return (firstName + " " + lastName);
	}

	/**
	 * Returns the last name.
	 * 
	 * @return the last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the first name.
	 * 
	 * @param firstName
	 *            the first name
	 * throws IllegalArgumentException if the first name is invalid.	 
	 */
	public void setFirstName(String firstName) {
		if (firstName == null)
			throw new IllegalArgumentException("Invalid first name");

		this.firstName = firstName;
	}

	/**
	 * Sets the last name.
	 * 
	 * @param lastName
	 *            the last name
	 * throws IllegalArgumentException if the last name is invalid.
	 */
	public void setLastName(String lastName) {
		if (lastName == null)
			throw new IllegalArgumentException("Invalid last name");
		this.lastName = lastName;
	}

	/**
	 * Returns a string of both the first and last name.
	 * 
	 * @return the name
	 */
	@Override
	public String toString() {
		return (firstName + "*" + lastName);
	}
}
