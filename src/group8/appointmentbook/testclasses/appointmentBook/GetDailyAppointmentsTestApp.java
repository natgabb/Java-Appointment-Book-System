package group8.appointmentbook.testclasses.appointmentBook;

import static java.lang.System.out;
import static java.util.Calendar.getInstance;
import group8.appointmentbook.business.Appointment;
import group8.appointmentbook.business.AppointmentBook;
import group8.appointmentbook.data.AppointmentListDA;

import java.util.Calendar;
import java.util.List;

import dw314.lib.DailyConstraints;
import dw314.lib.FileType;

public class GetDailyAppointmentsTestApp {

	public static void main(String[] args) {
		try {
			AppointmentBook appBook = new AppointmentBook(
					new AppointmentListDA(
							"datafiles/database/appointments.ser",
							FileType.SERIALIZED), new DailyConstraints());

			try {// Case 1
				out.println("Case 1:");
				appBook.getDailyAppointments(null);
			} catch (IllegalArgumentException iae) {
				out.println(iae.getMessage());
			}

			{// Case 2
				out.println("\nCase 2:");
				Calendar date = getInstance();
				date.set(2011, 10, 28, 8, 30, 0);
				List<Appointment> apps = appBook.getDailyAppointments(date);
				for (Appointment a : apps)
					out.println(a);
			}

			{// Case 3
				out.println("\nCase 3:");
				Calendar date = getInstance();
				date.set(2011, 10, 27, 8, 30, 0);
				List<Appointment> app = appBook
						.getDailyAppointments(date);
				out.println(app);
			}

		} catch (Exception e) {
			out.println(e.getMessage() + " Outer catch");
		}
	}

}
