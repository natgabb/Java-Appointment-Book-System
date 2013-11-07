package group8.appointmentbook.business;

import dw314.lib.*;

import java.io.Serializable;
import java.util.Calendar;

/**
 * This class defines the Appointment object. It can be used to keep track of
 * the date, contact information and reason for the appointment.
 * 
 * @author Caroline Castonguay-Boisvert
 * @author Kim Parisé
 * @author Natacha Gabbamonte
 * 
 * @since 1.5 01/11/2011
 */
public class Appointment implements Comparable<Appointment>, Serializable {

	private Calendar date;
	private Person contact;
	private String reason;

	private static final long serialVersionUID = 4203146887L;

	/**
	 * This constructor creates an appointment with a date and a person.
	 * 
	 * @param year
	 *            the year of the appointment
	 * @param month
	 *            the month of the appointment
	 * @param day
	 *            the day of the appointment
	 * @param hours
	 *            the hour of the appointment
	 * @param minutes
	 *            the minutes of the appointment
	 * @param contact
	 *            the person to contact for the appointment
	 */
	public Appointment(int year, int month, int day, int hours, int minutes,
			Person contact) {

		this(year, month, day, hours, minutes, contact, "");
	}

	/**
	 * This constructor creates an appointment with a date, a person and a
	 * reason.
	 * 
	 * @param year
	 *            the year of the appointment
	 * @param month
	 *            the month of the appointment
	 * @param day
	 *            the day of the appointment
	 * @param hours
	 *            the hour of the appointment
	 * @param minutes
	 *            the minutes of the appointment
	 * @param contact
	 *            the person to contact for the appointment
	 * @param reason
	 *            the reason for the appointment
	 * @throws IllegalArgumentException
	 *             if the date sent in is invalid.
	 */
	public Appointment(int year, int month, int day, int hours, int minutes,
			Person contact, String reason) {

		date = Calendar.getInstance();
		date.setLenient(false);
		date.set(year, month, day, hours, minutes, 0);
		date.set(Calendar.MILLISECOND, 0);

		// If the date is invalid, getTime() will throw an
		// IllegalArgumentException
		date.getTime();

		setContact(contact);
		setReason(reason);
	}

	/**
	 * This getter returns a copy of the contact.
	 * 
	 * @return a copy of the contact
	 */
	public Person getContact() {
		Name copyName = contact.getName();
		Person copyContact = new Person(copyName.getFirstName(),
				copyName.getLastName(), contact.getTelephoneNumber(),
				contact.getAddress());
		return copyContact;
	}

	/**
	 * This getter returns a copy of the date.
	 * 
	 * @return a copy of the date
	 */
	public Calendar getDate() {

		return (Calendar) date.clone();
	}

	/**
	 * This getter returns the reason for the appointment.
	 * 
	 * @return the reason for the appointment
	 */
	public String getReason() {
		return reason;
	}

	/**
	 * This setter sets the contact information.
	 * 
	 * @param contact
	 *            the contact information for the appointment
	 * @throws IllegalArgumentException
	 *             if the contact is null.
	 */
	public void setContact(Person contact) {
		if (contact == null)
			throw new IllegalArgumentException("The contact should not be null");
		this.contact = contact;
	}

	/**
	 * This setter sets the reason for the appointment. If the reason sent in is
	 * null, it is going to be changed to "".
	 * 
	 * @param reason
	 *            the reason for the appointment
	 */
	public void setReason(String reason) {
		if (reason == null)
			reason = "";
		this.reason = reason;
	}

	/**
	 * This method compares the Appointment object and another Object.
	 * 
	 * @param app
	 *            the Appointment to be compared
	 * 
	 * @return returns 0 if both objects are the same, a positive number if the
	 *         calling Appointment object has a later date, and a negative
	 *         number if the calling Appointment object has a more recent date.
	 * 
	 * @throws ClassCastException
	 *             if the object being compared to is not an Appointment.
	 * @throws NullPointerException
	 *             if the reference being sent in is referencing null
	 */
	@Override
	public int compareTo(Appointment app) {

		if (this == app)
			return 0;

		return (date.compareTo(app.getDate()));
	}

	/**
	 * This compares an Object sent in to an instance of the Appointment class.
	 * 
	 * @param obj
	 *            the Object to be compared
	 * 
	 * @return whether the Object compared is equal or not
	 */
	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;
		if (obj == null || obj.getClass() != this.getClass())
			return false;

		Appointment appointment = (Appointment) obj;

		return date.equals(appointment.date);
	}

	/**
	 * This method returns a String representation of the appointment.
	 * 
	 * @return a String representation of the appointment
	 */
	@Override
	public String toString() {

		String del = "*";

		Name name = contact.getName();
		String s = date.get(Calendar.YEAR) + del + date.get(Calendar.MONTH)
				+ del + date.get(Calendar.DAY_OF_MONTH) + del
				+ date.get(Calendar.HOUR_OF_DAY) + del
				+ date.get(Calendar.MINUTE) + del + name.toString() + del
				+ contact.getTelephoneNumber() + del + reason;
		return s;
	}
}