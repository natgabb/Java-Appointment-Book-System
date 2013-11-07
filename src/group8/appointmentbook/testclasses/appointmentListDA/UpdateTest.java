package group8.appointmentbook.testclasses.appointmentListDA;

import static java.lang.System.out;
import group8.appointmentbook.business.Appointment;
import group8.appointmentbook.data.AppointmentListDA;
import java.io.IOException;

import dw314.appointmentbook.exceptions.NonExistingAppointmentException;
import dw314.lib.FileType;
import dw314.lib.Person;

public class UpdateTest {

	public static void main(String[] args) {
		
		String filename = "datafiles/testfiles/testAppointments.ser";
		AppointmentListDA apps = null;

		try {
			apps = new AppointmentListDA(filename, FileType.SERIALIZED);
		} catch (IOException ioe) {
			out.println(ioe.getMessage());
		} catch (Exception e) {
			out.println(e.getMessage());
		}

		out.println("Original database:\n" + apps);

		{// The appointment exists.
			try {
				out.println("Case 1:");
				Appointment app = new Appointment(2011, 8, 11, 9, 30,
						new Person("I have", "Been changed"));
				apps.update(app);
				out.println(apps);
			} catch (NonExistingAppointmentException neae) {
				out.println(neae.getMessage());
			}
		}

		{// The appointment does not exist.
			try {
				out.println("Case 2:");
				Appointment app = new Appointment(2011, 8, 10, 9, 30,
						new Person("First", "Last"));
				apps.update(app);
			} catch (NonExistingAppointmentException neae) {
				out.println(neae.getMessage());
			}
		}
		
		{// The appointment is null.
			try {
				out.println("\nCase 3:");
				apps.remove(null);
			} catch (NonExistingAppointmentException neae) {
				out.println(neae.getMessage());
			}
		}
		
	}

}
