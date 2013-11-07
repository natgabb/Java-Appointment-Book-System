package group8.appointmentbook.data;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

import dw314.lib.Person;
import group8.appointmentbook.business.Appointment;
import group8.util.Utilities;

/**
 * This class contains methods for loading files into lists.
 * 
 * @author Kim Parisé
 * @author Natacha Gabbamonte
 * @author Caroline Castonguay-Boisvert
 * 
 * @since 1.5 01/11/2011
 */
public class AppointmentFileLoader {

	/**
	 * This method loads Appointment objects from a sequential file into a
	 * List<Appointment>.
	 * 
	 * @param filename
	 *            The name of a sequential file.
	 * @return a list containing the appointments in the file.
	 * 
	 * @throws IllegalArgumentException
	 *             If the file contains illegal entries or cannot be found.
	 */
	public static List<Appointment> getListFromSequentialFile(String filename) {

		if (filename == null)
			throw new IllegalArgumentException("The file name cannot be null.");

		// To contain the Appointment objects
		List<Appointment> appointments = new ArrayList<Appointment>();
		Scanner inputFile = null;
		String recordStr = null;

		try {
			BufferedReader b = new BufferedReader(new InputStreamReader(
					new FileInputStream(filename)));
			inputFile = new Scanner(b);

			String[] fields = null;

			// To load the Appointment objects into the list
			while (inputFile.hasNext()) {
				recordStr = inputFile.nextLine();
				fields = recordStr.split("\\*");

				try {
					if (fields.length < 8)
						throw new IllegalArgumentException(
								"Invalid. Not enough fields");
					int year = Integer.parseInt(fields[0]);
					int month = Integer.parseInt(fields[1]);
					int day = Integer.parseInt(fields[2]);
					int hour = Integer.parseInt(fields[3]);
					int mins = Integer.parseInt(fields[4]);

					Person contact = new Person(fields[5], fields[6], fields[7]);

					if (fields.length == 9) {
						// Creates a new appointment object with a reason.
						appointments.add(new Appointment(year, month, day,
								hour, mins, contact, fields[8]));
					} else if (fields.length == 8) {
						// Creates a new appointment object without a reason.
						appointments.add(new Appointment(year, month, day,
								hour, mins, contact));
					} else
						throw new IllegalArgumentException(
								"Invalid. Too many fields");

				}// end of inner try
				catch (NumberFormatException nfe) {
					throw new IllegalArgumentException(
							"Illegal argument(s) in date and/or time in "
									+ recordStr);
				} catch (IllegalArgumentException iae) {
					throw new IllegalArgumentException("Error: "
							+ iae.getMessage() + " in " + recordStr);
				}
			}// end of while loop

			// Shrinks the array to contain only it's elements.
			((ArrayList<Appointment>) appointments).trimToSize();

		}// end of outer try

		catch (FileNotFoundException fnf) {
			throw new IllegalArgumentException("Error: " + filename
					+ " not found.");
		} finally {
			if (inputFile != null)
				inputFile.close();
		}
		return appointments;
	}

	/**
	 * This method loads Appointment objects from a serialized file into a
	 * List<Appointment>.
	 * 
	 * @param filename
	 *            The name of a sequential file.
	 * @return a list containing the appointments in the file.
	 * 
	 * @throws IllegalArgumentException
	 *             If the file name is null or cannot be found.
	 * @throws Exception
	 *             If there is a problem while de-serializing the file.
	 */

	@SuppressWarnings({ "unchecked" })
	public static List<Appointment> getListFromObjectFile(String filename)
			throws Exception {

		if (filename == null)
			throw new IllegalArgumentException("The file name cannot be null.");

		ObjectInputStream in = null;

		try {
			List<Appointment> appointments = (List<Appointment>) Utilities.deserializeObject(filename);
			return appointments;
		} catch (FileNotFoundException fnfe) {
			throw new IllegalArgumentException("Error: " + filename
					+ " not found.");
		} catch (Exception e) {
			throw new Exception("Error de-serializing object:  "
					+ e.getMessage());
		} finally {
			if (in != null)
				in.close();
		}

	}
}
