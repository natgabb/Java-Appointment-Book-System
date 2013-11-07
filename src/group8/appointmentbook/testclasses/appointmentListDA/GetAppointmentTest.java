package group8.appointmentbook.testclasses.appointmentListDA;

import static java.lang.System.out;
import java.io.IOException;
import java.util.Calendar;

import dw314.appointmentbook.exceptions.NonExistingAppointmentException;
import dw314.lib.FileType;
import group8.appointmentbook.business.Appointment;
import group8.appointmentbook.data.AppointmentListDA;

public class GetAppointmentTest {

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
		{// Date is found
			try {
				out.println("Case 1:");
				Calendar date = Calendar.getInstance();
				date.set(2011, 8, 11, 9, 30, 0);
				date.set(Calendar.MILLISECOND, 0);
				Appointment app = apps.getAppointment(date);
				out.println("Appointment found: " + app);
			} catch (NonExistingAppointmentException neae) {
				out.println(neae.getMessage());
			}
		}
		{// Date is not found
			try {
				out.println("\nCase 2:");
				Calendar date = Calendar.getInstance();
				date.set(2011, 2, 9, 11, 30, 0);
				date.set(Calendar.MILLISECOND, 0);
				Appointment app = apps.getAppointment(date);
				out.println("Appointment found: " + app);
			} catch (NonExistingAppointmentException neae) {
				out.println(neae.getMessage());
			}
		}

		{// Date is null
			try {
				out.println("\nCase 3:");
				Appointment app = apps.getAppointment(null);
				out.println("Appointment found: " + app);
			} catch (NonExistingAppointmentException neae) {
				out.println(neae.getMessage());
			}
		}
	}

}
