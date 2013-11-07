package group8.appointmentbook.testclasses.appointmentListDA;

import static java.lang.System.out;
import group8.appointmentbook.business.Appointment;
import group8.appointmentbook.data.AppointmentListDA;
import java.io.IOException;
import java.util.List;

import dw314.lib.FileType;

public class GetAppointmentsForTest {

	public static void main(String[] args) {
		String filename = "datafiles/testfiles/testAppointments.ser";
		AppointmentListDA apps = null;
		List<Appointment> app = null;

		try {
			apps = new AppointmentListDA(filename, FileType.SERIALIZED);
		} catch (IOException ioe) {
			out.println(ioe.getMessage());
		} catch (Exception e) {
			out.println(e.getMessage());
		}
		{// Telephone is found
			out.println("Case 1:");
			app = apps.getAppointmentsFor("(514)884-3642");
			out.println("Appointments found:");
			for (Appointment a : app)
				out.println(a);
		}
		{// Telephone is not found
			out.println("\nCase 2:");
			app = apps.getAppointmentsFor("(999)999-9999");
			out.println("Size of List of Appointments: " + app.size());
		}
		{// Telephone is null
			out.println("\nCase 3:");
			app = apps.getAppointmentsFor(null);
			out.println("Size of List of Appointments: " + app.size());
		}
	}

}
