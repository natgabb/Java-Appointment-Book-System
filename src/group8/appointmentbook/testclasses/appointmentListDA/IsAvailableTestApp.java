package group8.appointmentbook.testclasses.appointmentListDA;

import group8.appointmentbook.data.AppointmentListDA;

import java.io.IOException;
import java.util.Calendar;

import dw314.lib.FileType;

import static java.lang.System.*;

/**
 * This class tests the isAvailable() method from AppointmentListDA.
 * 
 * @author Kim Parisé
 * @author Natacha Gabbamonte
 * @author Caroline Castonguay-Boisvert
 * 
 * @since 1.5 01/11/2011
 */
public class IsAvailableTestApp {

	public static void main(String[] args) {

		String filename = "datafiles/testfiles/testAppointments.ser";
		AppointmentListDA apps = null;
		try {
			apps = new AppointmentListDA(filename, FileType.SERIALIZED);
		} catch (IOException ioe) {
			out.print(ioe.getMessage());
		} catch (Exception e) {
			out.println(e.getMessage());
		}

		{// The date already exists.
			Calendar date = Calendar.getInstance();
			date.set(2011, 8, 11, 10, 30, 0);
			date.set(Calendar.MILLISECOND, 0);

			out.println("Case 1:");
			out.println(apps.isAvailable(date));
		}
		{// The date does not already exist.
			Calendar date = Calendar.getInstance();
			date.set(2012, 9, 22, 9, 0, 0);
			date.set(Calendar.MILLISECOND, 0);

			out.println("\nCase 2:");
			out.println(apps.isAvailable(date));
		}
		{// The date is null
			Calendar date = null;

			out.println("\nCase 3:");
			out.println(apps.isAvailable(date));
		}

	}

}
