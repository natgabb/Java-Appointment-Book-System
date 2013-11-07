package group8.appointmentbook.testclasses.appointmentListDA;

import static java.lang.System.out;
import group8.appointmentbook.business.Appointment;
import group8.appointmentbook.data.AppointmentListDA;
import java.io.IOException;

import dw314.appointmentbook.exceptions.DateNotAvailableException;
import dw314.lib.FileType;
import dw314.lib.Person;

public class AddTest {

	public static void main(String[] args) {
		String filename = "datafiles/testfiles/testAppointments.ser";
		AppointmentListDA apps = null;
		Appointment app = null;

		try {
			apps = new AppointmentListDA(filename, FileType.SERIALIZED);
		} catch (IOException ioe) {
			out.println(ioe.getMessage());
		} catch (Exception e) {
			out.println(e.getMessage());
		}

		out.println("Original database:\n" + apps);

		{// Date is not available.
			try {
				out.println("Case 1:");
				app = new Appointment(2011, 8, 11, 9, 30, new Person("Me",
						"You"));
				apps.add(app);
			} catch (DateNotAvailableException dnae) {
				out.println(dnae.getMessage());
			}
		}

		{// The date is available and is the earliest.
			try {
				out.println("\nCase 2 to 4:");
				app = new Appointment(2011, 8, 10, 9, 30, new Person("On",
						"Top"));
				apps.add(app);
			} catch (DateNotAvailableException dnae) {
				out.println(dnae.getMessage());
			}
		}
		{// The date is available in the middle.
			try {
				app = new Appointment(2011, 9, 26, 9, 30, new Person("In",
						"Middle"));
				apps.add(app);
			} catch (DateNotAvailableException dnae) {
				out.println(dnae.getMessage());
			}
		}
		{// The date is available and is the latest.
			try {
				app = new Appointment(2011, 11, 30, 9, 30, new Person("At",
						"Bottom"));
				apps.add(app);
			} catch (DateNotAvailableException dnae) {
				out.println(dnae.getMessage());
			}
		}

		out.println(apps);

		{// The appointment is null.
			try {
				out.println("Case 5:");
				apps.add(null);
			} catch (DateNotAvailableException dnae) {
				out.println(dnae.getMessage());
			}
		}

	}
}
