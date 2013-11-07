package group8.appointmentbook.testclasses;

import group8.appointmentbook.business.Appointment;
import group8.appointmentbook.business.AppointmentBook;
import group8.appointmentbook.data.AppointmentListDA;
import java.util.Calendar;
import java.util.List;

import dw314.lib.FileType;

public class AppointmentBookTestApp {

	public static void main(String[] args) {
		try {
			AppointmentBook myAppointmentBook = new AppointmentBook(
					new AppointmentListDA(
							"datafiles/database/appointments.ser",
							FileType.SERIALIZED),
					new dw314.lib.DailyConstraints());
			try {
				Calendar c = Calendar.getInstance();
				c.setLenient(false);
				c.set(2011, 11, 5, 0, 0, 0);
				c.set(Calendar.MILLISECOND, 0);
				List<Appointment> dailyAppointments = myAppointmentBook
						.getDailyAppointments(c);

				System.out.printf("%s %2$tA, %2$tB %2$td, %2$tY\n",
						"Daily appointments on", c);
				for (Appointment a : dailyAppointments)
					System.out.println("\t" + a);
			} catch (Exception e) {
				System.out.println("   " + e.getMessage());
			}

			myAppointmentBook.closeBook();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
