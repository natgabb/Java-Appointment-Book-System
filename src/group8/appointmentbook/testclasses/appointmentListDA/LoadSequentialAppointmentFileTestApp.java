package group8.appointmentbook.testclasses.appointmentListDA;

import group8.appointmentbook.data.AppointmentFileLoader;
import group8.appointmentbook.business.Appointment;
import java.util.List;
import static java.lang.System.*;

/**
 * This class test the AppointmentFileLoader class.
 * 
 * @author Kim Parisé
 * @author Natacha Gabbamonte
 * @author Caroline Castonguay-Boisvert
 * 
 * @since 1.5 10/21/2011
 */

public class LoadSequentialAppointmentFileTestApp {

	public static void main(String[] args) {
		
		List<Appointment> appointments = null;

		{
			out.println("Case 1 and 2:");
			String filename = "datafiles/testfiles/testAppointments.txt";

			appointments = AppointmentFileLoader
					.getListFromSequentialFile(filename);

			for (Appointment app : appointments)
				System.out.println(app);
		}

		{
			// The directory or the file cannot be found
			try {
				out.println("\nCase 3:");
				String filename = "datafiles/testfiles/nonexistingfile.txt";
				appointments = AppointmentFileLoader
						.getListFromSequentialFile(filename);
			} catch (IllegalArgumentException iae) {
				out.println(iae.getMessage());
			}
		}

		{
			// The file is empty.
			try {
				out.println("\nCase 4:");
				String filename = "datafiles/testfiles/new.txt";
				appointments = AppointmentFileLoader
						.getListFromSequentialFile(filename);
				out.println("Size of list: " + appointments.size());
			} catch (IllegalArgumentException iae) {
				out.println(iae.getMessage());
			}
		}

		{
			// A record in the file does not have enough fields.
			try {
				out.println("\nCase 5:");
				String filename = "datafiles/testfiles/notEnoughFields.txt";
				appointments = AppointmentFileLoader
						.getListFromSequentialFile(filename);
			} catch (IllegalArgumentException iae) {
				out.println(iae.getMessage());
			}
		}

		{
			// A record in the file has too many fields
			try {
				out.println("\nCase 6:");
				String filename = "datafiles/testfiles/tooManyFields.txt";
				appointments = AppointmentFileLoader
						.getListFromSequentialFile(filename);
			} catch (IllegalArgumentException iae) {
				out.println(iae.getMessage());
			}
		}

		{
			// The date or time field in one of the records is an invalid
			// number.
			try {
				out.println("\nCase 7:");
				String filename = "datafiles/testfiles/errorInTime.txt";
				appointments = AppointmentFileLoader
						.getListFromSequentialFile(filename);

			} catch (IllegalArgumentException iae) {
				out.println(iae.getMessage());
			}
		}

		{
			// The date or time field in one of the records is not a number.
			try {
				out.println("\nCase 8:");
				String filename = "datafiles/testfiles/errorInTimeNotNum.txt";
				appointments = AppointmentFileLoader
						.getListFromSequentialFile(filename);

			} catch (IllegalArgumentException iae) {
				out.println(iae.getMessage());
			}
		}

		{
			// File name is null.
			try {
				out.println("\nCase 9:");
				appointments = AppointmentFileLoader
						.getListFromSequentialFile(null);
				out.println("Done.");
			} catch (IllegalArgumentException iae) {
				out.println(iae.getMessage());
			}
		}

	}
}
