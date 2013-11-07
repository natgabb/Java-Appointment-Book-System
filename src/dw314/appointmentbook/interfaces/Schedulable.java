package dw314.appointmentbook.interfaces;

import dw314.appointmentbook.exceptions.DateNotAvailableException;
import dw314.appointmentbook.exceptions.NonExistingAppointmentException;
import java.util.Calendar;
import java.util.List;

/**
 * @author Carmen Legendre
 * @since JDK 5
 * @version October 31, 2011 ver 1.2
 * 
 */
public interface Schedulable<T extends Comparable<T>> {

	/**
	 * Cancels an entry. This implies that the entry will be removed from the
	 * database.
	 * 
	 * @param entry
	 *            the entry to cancel.
	 * 
	 * @throws NonExistingAppointmentException
	 *             if the entry does not exist.
	 */
	void cancelAppointment(T entry) throws NonExistingAppointmentException;

	/**
	 * Changes either the contact information or the reason for the scheduled
	 * entry.
	 * 
	 * @param entry
	 *            the modified entry.
	 * 
	 * @throws NonExistingAppointmentException
	 *             if the appointment does not exist.
	 */
	void changeAppointmentDetails(T entry)
			throws NonExistingAppointmentException;

	/**
	 * Remove all passed appointments from the appointment book and closes the
	 * appointment book.
	 * 
	 * @throws Exception
	 *             if there is a problem closing the appointment book.
	 */

	void closeBook() throws Exception;

	/**
	 * Returns a list of the next five available appointments, starting at a
	 * specific date.
	 * 
	 * @param startingDate
	 *            the desired date. For example, if the client would like to
	 *            schedule an appointment on November 25, 2011, this would be
	 *            considered the starting date
	 * 
	 * @return a list of available dates.
	 * 
	 */
	List<Calendar> getNextAvailableDatesStarting(Calendar startingDate);

	/**
	 * Returns the specified appointment. A NonExistingAppointmentException is
	 * thrown if the appointment doesn't exist.
	 * 
	 * @param date
	 *            the date of the appointment to get.
	 * @return an appointment.
	 * @throws NonExistingAppointmentException
	 *             if the specified date is not scheduled.
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
	 * @param telephoneNumber
	 *            the telephone number to check the appointments against.
	 * @return an appointment list.
	 * 
	 */
	List<T> getAppointmentsFor(String telephoneNumber);

	/**
	 * Returns the appointments scheduled for the specified day in a given year.
	 * Returns a reference to a list of size zero if there are no daily
	 * appointments scheduled on the specified date.
	 * 
	 * @param date
	 *            date of requested daily schedule.
	 * @return a list of daily appointments.
	 */
	List<T> getDailyAppointments(Calendar date);

	/**
	 * Determines if a specified date is available for appointments.
	 * 
	 * @param date
	 *            the date to verify for availability.
	 * @return true if the specified date is available for appointments; false
	 *         if it isn't available for appointments.
	 * 
	 */
	boolean isAvailable(Calendar date);

	/**
	 * Schedules an appointment. Appointment date must be available and must
	 * respect the daily constraints.
	 * 
	 * @param appointment
	 *            The appointment to be scheduled.
	 * 
	 * @throws DateNotAvailableException
	 *             if the requested date is already taken or if the requested
	 *             date does not respect the specified daily constraints.
	 */
	void scheduleAppointment(T appointment) throws DateNotAvailableException;

	/**
	 * Reschedules an appointment. New appointment date must be available and
	 * must respect the daily constraints.
	 * 
	 * @param currentDate
	 *            the date of the current appointment.
	 * @param newDate
	 *            the date of the rescheduled appointment.
	 * 
	 * @throws DateNotAvailableException
	 *             if the requested date is already taken or if the requested
	 *             date does not respect the specified daily constraints.
	 * @throws NonExistingAppointmentException
	 *             if the current date of the appointment does not exist.
	 */
	void rescheduleAppointment(Calendar currentDate, Calendar newDate)
			throws DateNotAvailableException, NonExistingAppointmentException;
}
