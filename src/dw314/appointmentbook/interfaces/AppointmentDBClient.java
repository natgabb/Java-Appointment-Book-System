package dw314.appointmentbook.interfaces;

/**
 * This interface describes various methods required for an Appointment book
 * database system.
 * 
 * @author Kim Parisé
 * @author Natacha Gabbamonte
 * @author Caroline Castonguay-Boisvert
 * 
 * @since 1.5 10/21/2011
 */

import java.util.List;
import java.util.Calendar;
import dw314.appointmentbook.exceptions.DateNotAvailableException;
import dw314.appointmentbook.exceptions.NonExistingAppointmentException;

public interface AppointmentDBClient<T extends Comparable<T>> {

	/**
	 * Adds an appointment to the database.
	 * 
	 * @param appointment
	 *            The appointment to add to the database.
	 * @throws DateNotAvailableException
	 *             date is already booked.
	 */
	void add(T appointment) throws DateNotAvailableException;

	/**
	 * Disconnects from the database.
	 * 
	 * @throws Exception
	 *             problem disconnecting from database.
	 */

	void disconnect() throws Exception;

	/**
	 * Returns the specified appointment. A NoneExistingAppointmentException is
	 * thrown if the appointment doesn't exist.
	 * 
	 * @param date
	 *            The date of the appointment to get.
	 * @return An appointment.
	 * @throws NonExistingAppointmentException
	 *             the specified date is not scheduled.
	 */
	T getAppointment(Calendar date) throws NonExistingAppointmentException;

	/**
	 * Returns the appointments scheduled for a matching telephone number. The
	 * appointments will include all appointments matching the telephone number;
	 * hence may not necessarily belong to same contact. One will have to check
	 * the contact's name before concluding that the appointment actually
	 * belongs to the contact.
	 * 
	 * A reference to a list of size zero indicates that there are no
	 * appointments scheduled for the specified contact.
	 * 
	 * @param date
	 *            The date of the appointment to get.
	 * @return An appointment list.
	 * 
	 */
	List<T> getAppointmentsFor(String telephoneNumber);

	/**
	 * Returns the appointments scheduled for the specified day in a given year.
	 * If there are no daily appointments scheduled. A reference to a list of
	 * size zero if there are no daily appointments scheduled.
	 * 
	 * @param date
	 *            Date of requested daily schedule.
	 * @return A list of daily appointments.
	 */
	List<T> getDailyAppointments(Calendar date);

	/**
	 * Determines is a specified date is available for appointments.
	 * 
	 * @param date
	 *            The date to verify for availability.
	 * @return true if the specified date is available for appointments; false
	 *         if it isn't available for appointments.
	 * 
	 */
	boolean isAvailable(Calendar date);

	/**
	 * Removes the appointment that is scheduled on the given date.
	 * 
	 * @param appointment
	 *            The appointment to be removed.
	 * @throws NonExistingAppointmentException
	 *             The appointment to be removed is not in database.
	 * 
	 */
	void remove(T appointment) throws NonExistingAppointmentException;

	/**
	 * Modifies the details of an appointment scheduled on a specified date. All
	 * attributes can be changed with the exception of the key that is used to
	 * uniquely identity the appointment.
	 * 
	 * @param modifiedAppointment
	 *            The appointment to be update.
	 * @throws NonExistingAppointmentException
	 *             The appointment is not in database.
	 */
	void update(T modifiedAppointment) throws NonExistingAppointmentException;

}
