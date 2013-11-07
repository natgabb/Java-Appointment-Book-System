package group8.appointmentbook.testclasses.appointmentListDA;

import group8.appointmentbook.data.AppointmentListDA;
import java.io.IOException;

import dw314.lib.FileType;
import static java.lang.System.*;

public class AppointmentListDATest {

	public static void main(String[] args) {
		// Testing AppointmentListDA constructor and toString
		String filename = "datafiles/testfiles/testAppointments.txt";
		AppointmentListDA apps = null;

		try {// Sequential file.
			out.println("Case 1");
			apps = new AppointmentListDA(filename, FileType.TEXT_SEQUENTIAL);
			out.println(apps);
		} catch (IOException ioe) {
			out.println(ioe.getMessage());
		} catch (Exception e) {
			out.println(e.getMessage());
		}

		try {// Serialized file.
			out.println("Case 2");
			filename = "datafiles/testfiles/testAppointments.ser";
			apps = new AppointmentListDA(filename, FileType.SERIALIZED);
			out.println(apps);
		} catch (IOException ioe) {
			out.println(ioe.getMessage());
		} catch (Exception e) {
			out.println(e.getMessage());
		}

		try {
			out.println("Case 3");
			filename = "datafiles/testfiles/thisfiledoesnotexist.txt";
			apps = new AppointmentListDA(filename, FileType.TEXT_SEQUENTIAL);
			out.println(apps);
		} catch (IOException ioe) {
			out.println(ioe.getMessage());
		} catch (Exception e) {
			out.println(e.getMessage());
		}

		try {
			out.println("\nCase 4");
			filename = null;
			apps = new AppointmentListDA(filename, FileType.TEXT_SEQUENTIAL);
			out.println(apps);
		} catch (IOException ioe) {
			out.println(ioe.getMessage());
		} catch (Exception e) {
			out.println(e.getMessage());
		}
		
	}// end main

}// end class
