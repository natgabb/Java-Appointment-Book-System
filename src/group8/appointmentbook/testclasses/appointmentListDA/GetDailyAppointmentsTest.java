package group8.appointmentbook.testclasses.appointmentListDA;

import static java.lang.System.out;
import group8.appointmentbook.business.Appointment;
import group8.appointmentbook.data.AppointmentListDA;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import dw314.lib.FileType;

public class GetDailyAppointmentsTest {

	public static void main(String[] args) {
		String filename = "datafiles/testfiles/testAppointments.ser";
		AppointmentListDA database = null;
		List<Appointment> apps = null;

		try {
			database = new AppointmentListDA(filename, FileType.SERIALIZED);
		} catch (IOException ioe) {
			out.println(ioe.getMessage());
		} catch (Exception e) {
			out.println(e.getMessage());
		}
		{// Date is found
			out.println("Case 1:");
			Calendar date = Calendar.getInstance();
			date.setLenient(false);
			date.set(2011, 8, 11, 8, 30, 0);
			date.set(Calendar.MILLISECOND, 0);
			apps = database.getDailyAppointments(date);
			out.println("Appointment found:");
			for (Appointment app : apps)
				out.println(app);
		}
		{// Date is not found
			out.println("\nCase 2:");
			Calendar date = Calendar.getInstance();
			date.set(2011, 2, 9, 11, 30, 0);
			date.set(Calendar.MILLISECOND, 0);
			apps = database.getDailyAppointments(date);
			out.println("Size of List of Appointments: " + apps.size());
		}
		{// Date is null
			out.println("\nCase 3:");
			apps = database.getDailyAppointments(null);
			out.println("Size of List of Appointments: " + apps.size());
		}
	}

}
