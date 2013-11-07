package group8.appointmentbook.business;

import group8.util.ListUtilities;
import java.util.Calendar;
import static java.util.Calendar.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Observable;

import dw314.appointmentbook.exceptions.DateNotAvailableException;
import dw314.appointmentbook.exceptions.NonExistingAppointmentException;
import dw314.appointmentbook.interfaces.AppointmentDBClient;
import dw314.appointmentbook.interfaces.Schedulable;
import dw314.lib.DailyConstraints;
import dw314.lib.DailySchedule;
import dw314.lib.Person;
import dw314.lib.TimeBlock;

public class AppointmentBook extends Observable implements
		Schedulable<Appointment> {

	private AppointmentDBClient<Appointment> connection;
	private DailyConstraints dailyConstraints;
	private String errorMessage = "";

	public AppointmentBook(AppointmentDBClient<Appointment> connection,
			DailyConstraints dailyConstraints) {
		if (connection == null || dailyConstraints == null)
			throw new IllegalArgumentException(
					"The parameters must not be null.");
		this.connection = connection;
		this.dailyConstraints = dailyConstraints;
	}

	/**
	 * Cancels an entry. This implies that the entry will be removed from the
	 * database.
	 * 
	 * @param entry
	 *            the entry to cancel.
	 * 
	 * @throws NonExistingAppointmentException
	 *             if the entry does not exist.
	 * @throws IllegalArgumentException
	 *             if the entry has already passed or is null.
	 */
	@Override
	public void cancelAppointment(Appointment entry)
			throws NonExistingAppointmentException {
		if (entry == null)
			throw new IllegalArgumentException(
					"The appointment cannot be null.");
		if (entry.getDate().before(getInstance())) {
			throw new IllegalArgumentException(
					"Cannot cancel an appointment that has already passed.");
		}
		connection.remove(entry);
		this.setChanged();
		this.notifyObservers(entry.getDate());
	}

	/**
	 * Changes either the contact information or the reason for the scheduled
	 * entry.
	 * 
	 * @param entry
	 *            the modified entry.
	 * 
	 * @throws NonExistingAppointmentException
	 *             if the appointment does not exist.
	 * @throws IllegalArgumentException
	 *             if the entry has already passed or is null.
	 */
	@Override
	public void changeAppointmentDetails(Appointment entry)
			throws NonExistingAppointmentException {
		if (entry == null)
			throw new IllegalArgumentException(
					"The appointment cannot be null.");
		if (entry.getDate().before(getInstance())) {
			throw new IllegalArgumentException(
					"Cannot change an appointment that has already passed.");
		}
		connection.update(entry);		
		this.setChanged();
		this.notifyObservers(entry.getDate());
	}

	/**
	 * Remove all passed appointments from the appointment book and closes the
	 * appointment book.
	 * 
	 * @throws Exception
	 *             if there is a problem closing the appointment book.
	 */
	@Override
	public void closeBook() throws Exception {

		// Before disconnecting, appointments that have passed are removed.

		Calendar maxPreviousDate = getInstance();
		maxPreviousDate.add(MONTH, -dailyConstraints.getMaxMonthsInAdvance());

		Calendar today = getInstance();
		today.set(HOUR_OF_DAY, 0);
		today.set(MINUTE, 0);

		List<Appointment> listOfAppointments;

		while (maxPreviousDate.before(today)) {
			listOfAppointments = connection
					.getDailyAppointments(maxPreviousDate);
			for (Appointment a : listOfAppointments)
				connection.remove(a);
			maxPreviousDate.add(DATE, 1);
		}
		connection.disconnect();
	}

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
	 * @throws IllegalArgumentException
	 *             if the date is null, passed or too far in the future.
	 */
	@Override
	public List<Calendar> getNextAvailableDatesStarting(Calendar startingDate) {

		if (startingDate == null)
			throw new IllegalArgumentException("The date cannot be null.");

		if (startingDate.before(getInstance()))
			throw new IllegalArgumentException(
					"The starting date/time cannot be prior to now.");

		Calendar maxDate = getInstance();
		maxDate.add(MONTH, dailyConstraints.getMaxMonthsInAdvance());
		if (maxDate.before(startingDate))
			throw new IllegalArgumentException(
					"The starting date/time cannot be after "
							+ String.format("%1$tA, %1$tB %1td, %1$tY", maxDate)
							+ ".");

		int timeInterval = dailyConstraints.getTimeInterval();
		List<Calendar> availableDates = new ArrayList<Calendar>(5);
		Calendar date = (Calendar) startingDate.clone();

		int numOfDates = 0;
		while (numOfDates < 5) {
			if (validate(date)) {
				availableDates.add((Calendar) date.clone());
				numOfDates++;
			}
			date.add(MINUTE, timeInterval);
			if (maxDate.before(date))
				numOfDates = 5;
		}
		return availableDates;
	}

	/**
	 * Returns the specified appointment. A NonExistingAppointmentException is
	 * thrown if the appointment doesn't exist.
	 * 
	 * @param date
	 *            the date of the appointment to get.
	 * @return an appointment.
	 * @throws NonExistingAppointmentException
	 *             if the specified date is not scheduled.
	 * @throws IllegalArgumentException
	 *             if the date is null.
	 */
	@Override
	public Appointment getAppointment(Calendar date)
			throws NonExistingAppointmentException {
		return connection.getAppointment(date);
	}

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
	 * @throws IllegalArgumentException
	 *             if the telephone number is null.
	 */
	@Override
	public List<Appointment> getAppointmentsFor(String telephoneNumber) {
		return connection.getAppointmentsFor(telephoneNumber);
	}

	/**
	 * Returns the appointments scheduled for the specified day in a given year.
	 * Returns a reference to a list of size zero if there are no daily
	 * appointments scheduled on the specified date.
	 * 
	 * @param date
	 *            date of requested daily schedule.
	 * @return a list of daily appointments.
	 * 
	 * @throws IllegalArgumentException
	 *             if the date is null.
	 */
	@Override
	public List<Appointment> getDailyAppointments(Calendar date) {
		return connection.getDailyAppointments(date);
	}

	/**
	 * Determines if a specified date is available for appointments.
	 * 
	 * @param date
	 *            the date to verify for availability.
	 * @return true if the specified date is available for appointments; false
	 *         if it isn't available for appointments.
	 * 
	 * @throws IllegalArgumentException
	 *             if the date is invalid.
	 */
	@Override
	public boolean isAvailable(Calendar date) {

		errorMessage = "";

		if (date == null)
			throw new IllegalArgumentException("The date cannot be null.");

		if (date.before(getInstance())) {
			errorMessage = "Cannot schedule appointments in the past. "
					+ String.format("%1$tA, %1$tB %1td, %1$tY", date);
			return false;
		}

		Calendar maxDate = getInstance();
		maxDate.add(MONTH, dailyConstraints.getMaxMonthsInAdvance());
		if (maxDate.before(date)) {
			errorMessage = "Cannot schedule appointments after "
					+ String.format("%1$tA, %1$tB %1td, %1$tY", maxDate) + ".";
			return false;
		}

		return validate(date);
	}

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
	 * @throws IllegalArgumentException
	 *             if the appointment is null.
	 */
	@Override
	public void scheduleAppointment(Appointment appointment)
			throws DateNotAvailableException {

		if (appointment == null)
			throw new IllegalArgumentException(
					"The appointment must not be null.");

		Calendar appDate = appointment.getDate();

		if (isAvailable(appDate))
			connection.add(appointment);
		else
			throw new DateNotAvailableException(errorMessage);
		this.setChanged();
		this.notifyObservers(appointment.getDate());
	}

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
	 * @throws IllegalArgumentException
	 *             if either of the dates is null.
	 */
	@Override
	public void rescheduleAppointment(Calendar currentDate, Calendar newDate)
			throws DateNotAvailableException, NonExistingAppointmentException {

		if (currentDate == null || newDate == null)
			throw new IllegalArgumentException(
					"The parameters must not be null.");

		// If the appointment doesn't exist it will throw a
		// NonExistingAppointmentException.
		Appointment app = connection.getAppointment(currentDate);

		connection.remove(app);

		// If true, it will add the new appointment, if not it will put back the
		// old appointment.
		if (isAvailable(newDate)) {
			Appointment newApp = new Appointment(newDate.get(YEAR),
					newDate.get(MONTH), newDate.get(DATE),
					newDate.get(HOUR_OF_DAY), newDate.get(MINUTE),
					app.getContact(), app.getReason());
			connection.add(newApp);
		} else {
			connection.add(app);
			throw new DateNotAvailableException(errorMessage);
		}
		this.setChanged();
		this.notifyObservers(new Calendar[]{currentDate,newDate});
	}

	/*
	 * This method validates a date to see if it fits with the DailyConstraints
	 * and that it doesn't interfere with other Appointments.
	 */
	private boolean validate(Calendar date) {

		// Time at which the appointment starts.
		int day = date.get(DAY_OF_WEEK) - 1;

		// Constraints
		int timeInterval = dailyConstraints.getTimeInterval();
		DailySchedule[] constraints = dailyConstraints.getDayConstraints();
		TimeBlock timePeriod = constraints[day].getTimePeriod();
		TimeBlock lunchTime = constraints[day].getLunchTime();

		// Time at which the appointment ends.
		Calendar endTime = (Calendar) date.clone();
		endTime.add(MINUTE, timeInterval - 1);

		// To check for conflicting appointments before
		Calendar conflictApp = (Calendar) date.clone();
		conflictApp.add(MINUTE, -(timeInterval - 30));

		// Creates two dummy appointments to check that there are no conflicting
		// appointments.
		Appointment dummyConflictApp = new Appointment(conflictApp.get(YEAR),
				conflictApp.get(MONTH), conflictApp.get(DATE),
				conflictApp.get(HOUR_OF_DAY), conflictApp.get(MINUTE),
				new Person("First", "Dummy"));
		Appointment dummyEndApp = new Appointment(endTime.get(YEAR),
				endTime.get(MONTH), endTime.get(DATE),
				endTime.get(HOUR_OF_DAY), endTime.get(MINUTE), new Person(
						"Second", "Dummy"));

		int startDayMins = timePeriod.getStartHours() * 60
				+ timePeriod.getStartMinutes();
		int endDayMins = timePeriod.getEndHours() * 60
				+ timePeriod.getEndMinutes();

		int startLunchMins = lunchTime.getStartHours() * 60
				+ lunchTime.getStartMinutes();
		int endLunchMins = lunchTime.getEndHours() * 60
				+ lunchTime.getEndMinutes();

		int startMins = date.get(HOUR_OF_DAY) * 60 + date.get(MINUTE);
		int endMins = endTime.get(HOUR_OF_DAY) * 60 + endTime.get(MINUTE);

		// Strings about the appointment
		String dayName = "("
				+ date.getDisplayName(DAY_OF_WEEK, LONG, Locale.CANADA) + ")";
		String appTimes = "\nAppointment from " + date.get(HOUR_OF_DAY) + ":"
				+ String.format("%02d", date.get(MINUTE)) + " to "
				+ endTime.get(HOUR_OF_DAY) + ":"
				+ String.format("%02d", endTime.get(MINUTE));

		// If there are appointments in between, it means that the appointment
		// time is invalid.
		List<Appointment> dailyAppointments = connection
				.getDailyAppointments(date);

		if (ListUtilities.binarySearch(dailyAppointments, dummyConflictApp) != ListUtilities
				.binarySearch(dailyAppointments, dummyEndApp)) {
			errorMessage = "Conflicts with other appointments." + appTimes;
			return false;
		}

		// Error messages
		String lunchTimeError = "Cannot schedule appointments during lunch time "
				+ dayName
				+ " : "
				+ lunchTime.getStartHours()
				+ ":"
				+ String.format("%02d", lunchTime.getStartMinutes())
				+ " - "
				+ lunchTime.getEndHours()
				+ ":"
				+ String.format("%02d", lunchTime.getEndMinutes()) + appTimes;
		String startDayError = "Cannot schedule appointments before the start of the day "
				+ dayName
				+ " : "
				+ timePeriod.getStartHours()
				+ ":"
				+ String.format("%02d", timePeriod.getStartMinutes())
				+ appTimes;
		String endDayError = "Cannot schedule appointments after the end of the day "
				+ dayName
				+ " : "
				+ timePeriod.getEndHours()
				+ ":"
				+ String.format("%02d", timePeriod.getEndMinutes()) + appTimes;

		boolean isValid = false;

		switch (constraints[day]) {

		case FULL1: // Time Period(00, 00, 00, 00) Lunch Time(00, 00, 00, 00)
		case FULL2:
			if (startMins >= startDayMins) {
				if (startMins < startLunchMins) {
					if (endMins < startLunchMins)
						isValid = true;
					else
						errorMessage = lunchTimeError;
				} else if (startMins >= endLunchMins) {
					if (endMins < endDayMins)
						isValid = true;
					else
						errorMessage = endDayError;
				} else
					errorMessage = lunchTimeError;
			} else {
				errorMessage = startDayError;
			}
			break;

		case AM: // Time Block(00, 00, 00, 00)
		case PM:
			if (startMins >= startDayMins)
				if (endMins < endDayMins)
					isValid = true;
				else
					errorMessage = endDayError;
			else
				errorMessage = startDayError;
			break;

		case NONE: // No Time Block
			errorMessage = "Cannot schedule appointments on "
					+ date.getDisplayName(DAY_OF_WEEK, LONG, Locale.CANADA)
					+ ".";
			break;
		}

		return isValid;
	}
}