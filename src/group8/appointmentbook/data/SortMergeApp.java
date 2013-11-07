package group8.appointmentbook.data;

import group8.appointmentbook.business.Appointment;
import group8.util.ListUtilities;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;

import dw314.lib.Person;

/**
 * This application class sorts 11 appointment files from datafiles/unsorted,
 * and then merges them in a single file. It saves the 11 new sorted files in
 * the sorted datafiles/sorted. The new merged file is saved in
 * datafiles/database. If there are duplicates they are saved in
 * datafiles/sorted/duplicateAppointments.txt.
 * 
 * @author Natacha Gabbamonte
 * @author Kim Parisé
 * @author Caroline Castonguay-Boisvert
 * 
 * @since 1.5 12/10/2011
 */
public class SortMergeApp {

	@SuppressWarnings("rawtypes")
	public static void main(String[] args) {

		Comparable[] mergedList = new Appointment[0];

		// To hold the 11 appointments
		Appointment[][] apps = new Appointment[11][];

		try {
			// To load the appointment objects in the array.
			for (int i = 0; i < 11; i++) {
				apps[i] = loadList("datafiles\\unsorted\\appointments"
						+ (i + 1) + ".txt");
				ListUtilities.sort(apps[i]);
			}

			// To save the appointment objects to separate files.
			for (int i = 0; i < 11;) {
				ListUtilities.save(apps[i],
						"datafiles\\sorted\\sortedAppointments" + (++i)
								+ ".txt", false);
			}

			String fileName = "datafiles\\sorted\\duplicateAppointments.txt";

			// To merge the different Appointment arrays in one array.
			for (int i = 0; i < 11; i++) {
				mergedList = ListUtilities.merge(mergedList, apps[i], fileName);
			}

			// To save the merged array in one file.
			ListUtilities.save(mergedList,
					"datafiles\\database\\appointments.txt", false);

		} catch (IllegalArgumentException iae) {
			System.out.println("An error occured: " + iae.getMessage());
		} catch (IOException ioe) {
			System.out.println("An error occured: " + ioe.getMessage());
		}
	}

	/**
	 * This method loads a list of appointments from a file into an array.
	 * 
	 * @param filename
	 *            the name of the file
	 * @return an array of Appointment objects
	 * @throws IllegalArgumentException
	 *             if there are too many fields in a record or if any of them is
	 *             invalid.
	 */
	public static Appointment[] loadList(String filename) {
		Appointment[] appointments = new Appointment[10];
		Scanner inputFile = null;
		String recordStr = null;

		try {
			BufferedReader b = new BufferedReader(new InputStreamReader(
					new FileInputStream(filename)));
			inputFile = new Scanner(b);

			String[] fields = null;

			// i is keeping track of the number of objects in Appointment[]
			int i = 0;
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
						appointments[i] = new Appointment(year, month, day,
								hour, mins, contact, fields[8]);
					} else if (fields.length == 8) {
						// Creates a new appointment object without a reason.
						appointments[i] = new Appointment(year, month, day,
								hour, mins, contact);
					} else
						throw new IllegalArgumentException(
								"Invalid. Too many fields");
					i++;

					// check if capacity has been surpassed
					// if true, grow list
					if (i >= appointments.length)
						appointments = Arrays.copyOf(appointments,
								(appointments.length + 1) * 2);
				}// end of inner try
				catch (NumberFormatException nfe) {
					throw new IllegalArgumentException(
							"Illegal argument(s) in date and/or time in "
									+ recordStr);
				} catch (IllegalArgumentException iae) {
					throw new IllegalArgumentException(iae.getMessage()
							+ " in " + recordStr);
				}
			}// end of while loop

			// Check if full to capacity, if false, shrink list
			if (i < appointments.length)
				appointments = Arrays.copyOf(appointments, i);

		}// end of outer try

		catch (FileNotFoundException fnf) {
			System.out.println("Error: " + filename + " not found.");
		} finally {
			if (inputFile != null)
				inputFile.close();
		}
		return appointments;
	}
}
