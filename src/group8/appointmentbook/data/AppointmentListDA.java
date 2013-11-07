package group8.appointmentbook.data;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import group8.appointmentbook.business.Appointment;
import group8.util.ListUtilities;
import group8.util.Utilities;
import dw314.appointmentbook.exceptions.DateNotAvailableException;
import dw314.appointmentbook.exceptions.NonExistingAppointmentException;
import dw314.appointmentbook.interfaces.AppointmentDBClient;
import dw314.lib.FileType;
import dw314.lib.Person;

/**
 * An AppointmentListDA object defines a database (List) containing Appointment
 * objects.
 * 
 * @author Kim Parisé
 * @author Caroline Castonguay-Boisvert
 * @author Natacha Gabbamonte
 * 
 * @since 1.5 01/11/2011
 */
public class AppointmentListDA implements AppointmentDBClient<Appointment> {

	private List<Appointment> database = null;
	private String filename = null;
	private FileType medium;

	/**
	 * Creates a database with the Appointments contained in the file sent in.
	 * 
	 * Precondition: Assumes that the file matches the FileType. Assumes that
	 * all Appointments are valid.
	 * 
	 * @param filename
	 *            the name of the file
	 * @param medium
	 *            the file type
	 * @throws IllegalArgumentException
	 *             if the file name is invalid.
	 * @throws Exception
	 *             if an error occurs while loading a serialized file.
	 */
	public AppointmentListDA(String filename, FileType medium) throws Exception {

		this.medium = medium;
		this.filename = filename;

		if (this.medium == FileType.TEXT_SEQUENTIAL)
			database = AppointmentFileLoader
					.getListFromSequentialFile(filename);
		else if (this.medium == FileType.SERIALIZED) {
			database = AppointmentFileLoader.getListFromObjectFile(filename);
			if (database == null)
				database = new ArrayList<Appointment>();
		}
	}

	/**
	 * Adds an appointment to the database.
	 * 
	 * @param appointment
	 *            The appointment to add to the database.
	 * @throws DateNotAvailableException
	 *             date is already booked.
	 * @throws IllegalArgumentException
	 *             if the appointment is null.
	 */
	@Override
	public void add(Appointment appointment) throws DateNotAvailableException {

		if (appointment == null)
			throw new IllegalArgumentException(
					"The appointment cannot be null.");

		int result = ListUtilities.binarySearch(database, appointment);
		if (result < 0) {
			result = -(result + 1);
			Appointment copyOfAppointment = Utilities.copyOf(appointment);
			database.add(result, copyOfAppointment);
		} else
			throw new DateNotAvailableException();
	}

	/**
	 * Disconnects from the database.
	 * 
	 * @throws Exception
	 *             problem disconnecting from database.
	 */
	@Override
	public void disconnect() throws Exception {

		if (medium == FileType.TEXT_SEQUENTIAL)
			ListUtilities.saveListToTextFile(database, filename);

		else if (medium == FileType.SERIALIZED)
			Utilities.serializeObject(database, filename);

		database = null;
	}

	/**
	 * Returns the specified appointment. A NoneExistingAppointmentException is
	 * thrown if the appointment doesn't exist.
	 * 
	 * @param date
	 *            The date of the appointment to get.
	 * @return An appointment.
	 * @throws NonExistingAppointmentException
	 *             the specified date is not scheduled.
	 * @throws IllegalArgumentException
	 *             if the date is null or invalid.
	 */
	@Override
	public Appointment getAppointment(Calendar date)
			throws NonExistingAppointmentException {
		if (date == null)
			throw new IllegalArgumentException("The date cannot be null.");

		// Creates a dummy appointment with the date sent in.
		Appointment app = new Appointment(date.get(Calendar.YEAR),
				date.get(Calendar.MONTH), date.get(Calendar.DATE),
				date.get(Calendar.HOUR_OF_DAY), date.get(Calendar.MINUTE),
				new Person("A", "Person"));

		// Checks if an appointment has the same date
		int indexOfAppointment = ListUtilities.binarySearch(database, app);
		if (indexOfAppointment >= 0)
			return Utilities.copyOf(database.get(indexOfAppointment));

		throw new NonExistingAppointmentException();
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
	 *            The telephoneNumber of the appointment to get.
	 * @return An appointment list.
	 * @throws IllegalArgumentException
	 *             if the telephoneNumber is null.
	 */
	@Override
	public List<Appointment> getAppointmentsFor(String telephoneNumber) {
		List<Appointment> matchingApps = new ArrayList<Appointment>();

		if (telephoneNumber == null)
			throw new IllegalArgumentException(
					"The telephone number cannot be null.");

		for (Appointment app : database)
			if (app.getContact().getTelephoneNumber()
					.compareTo(telephoneNumber) == 0)
				matchingApps.add(Utilities.copyOf(app));

		((ArrayList<Appointment>) matchingApps).trimToSize();

		return matchingApps;
	}

	/**
	 * Returns the appointments scheduled for the specified day in a given year.
	 * If there are no daily appointments scheduled, a reference to a list of
	 * size zero is returned.
	 * 
	 * @param date
	 *            Date of requested daily schedule.
	 * @return A list of daily appointments.
	 * @throws IllegalArgumentException
	 *             if the date is null or invalid.
	 */
	@Override
	public List<Appointment> getDailyAppointments(Calendar date) {

		List<Appointment> appointments = new ArrayList<Appointment>();

		if (date == null)
			throw new IllegalArgumentException("The date cannot be null.");

		Calendar newDate = copyOfDateWithoutTime(date);

		// Creates a dummy appointment with the newDate.
		Appointment app = new Appointment(newDate.get(Calendar.YEAR),
				newDate.get(Calendar.MONTH),
				newDate.get(Calendar.DAY_OF_MONTH), 0, 0, new Person("A",
						"Person"));

		// Finds the place where the appointments on that day start, then looks
		// to find all the appointments on that same day.
		int index = -(ListUtilities.binarySearch(database, app) + 1);
		while (index < database.size()) {
			Calendar appsDate = copyOfDateWithoutTime(database.get(index)
					.getDate());
			if (newDate.compareTo(appsDate) == 0)
				appointments.add(Utilities.copyOf(database.get(index)));
			else
				index = database.size();
			index++;
		}

		((ArrayList<Appointment>) appointments).trimToSize();

		return appointments;
	}

	/**
	 * Determines is a specified date is available for appointments.
	 * 
	 * @param date
	 *            The date to verify for availability.
	 * @return true if the specified date is available for appointments; false
	 *         if it isn't available for appointments.
	 * 
	 * @throws IllegalArgumentException
	 *             if the date is null or invalid
	 */
	@Override
	public boolean isAvailable(Calendar date) {
		if (date == null)
			throw new IllegalArgumentException("The date cannot be null.");

		// Create a dummy appointment with the date sent in.
		Appointment app = new Appointment(date.get(Calendar.YEAR),
				date.get(Calendar.MONTH), date.get(Calendar.DATE),
				date.get(Calendar.HOUR_OF_DAY), date.get(Calendar.MINUTE),
				new Person("A", "Person"));

		if (ListUtilities.binarySearch(database, app) < 0)
			return true;

		return false;
	}

	/**
	 * Removes the appointment that is scheduled on the given date.
	 * 
	 * @param appointment
	 *            The appointment to be removed.
	 * @throws NonExistingAppointmentException
	 *             The appointment to be removed is not in database.
	 * @throws IllegalArgumentException
	 *             if the appointment is null.
	 */
	@Override
	public void remove(Appointment appointment)
			throws NonExistingAppointmentException {

		if (appointment == null)
			throw new IllegalArgumentException(
					"The appointment cannot be null.");

		// Looks for the appointment
		int indexOfAppointment = ListUtilities.binarySearch(database,
				appointment);
		if (indexOfAppointment < 0)
			throw new NonExistingAppointmentException();

		database.remove(indexOfAppointment);
	}

	/**
	 * Returns a string representation of the contents of the database.
	 * 
	 * @return A string representation of the database.
	 */
	@Override
	public String toString() {
		String s = "database: Number of appointments in database: "
				+ database.size() + "\n";
		for (Appointment app : database)
			s += app + "\n";
		return s;
	}

	/**
	 * Modifies the details of an appointment scheduled on a specified date. All
	 * attributes can be changed with the exception of the key that is used to
	 * uniquely identity the appointment.
	 * 
	 * @param modifiedAppointment
	 *            The appointment to be update.
	 * @throws NonExistingAppointmentException
	 *             The appointment is not in database.
	 * @throws IllegalArgumentException
	 *             if modifiedAppointment is null.
	 */
	@Override
	public void update(Appointment modifiedAppointment)
			throws NonExistingAppointmentException {

		if (modifiedAppointment == null)
			throw new IllegalArgumentException(
					"The appointment cannot be null.");

		// Looks for the appointment
		int indexOfAppointment = ListUtilities.binarySearch(database,
				modifiedAppointment);

		if (indexOfAppointment < 0)
			throw new NonExistingAppointmentException();

		database.set(indexOfAppointment, Utilities.copyOf(modifiedAppointment));
	}

	/*
	 * Makes a copy of a date and sets the time (hour, minute, second,
	 * millisecond) to zero.
	 * 
	 * @param date The date to copy.
	 * 
	 * @return The copy (modified) of the date.
	 */
	private Calendar copyOfDateWithoutTime(Calendar date) {

		Calendar appDate = Calendar.getInstance();
		appDate.set(date.get(Calendar.YEAR), date.get(Calendar.MONTH),
				date.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		appDate.set(Calendar.MILLISECOND, 0);

		return appDate;
	}

}
