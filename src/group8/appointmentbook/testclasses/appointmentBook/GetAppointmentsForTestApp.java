package group8.appointmentbook.testclasses.appointmentBook;

import java.util.List;

import group8.appointmentbook.business.Appointment;
import group8.appointmentbook.business.AppointmentBook;
import group8.appointmentbook.data.AppointmentListDA;
import dw314.lib.DailyConstraints;
import dw314.lib.FileType;
import static java.lang.System.*;

public class GetAppointmentsForTestApp {

	public static void main(String[] args) {
		try {
			AppointmentBook appBook = new AppointmentBook(
					new AppointmentListDA(
							"datafiles/database/appointments.ser",
							FileType.SERIALIZED), new DailyConstraints());

			try {// Case 1
				out.println("Case 1:");
				appBook.getAppointmentsFor(null);
			} catch (IllegalArgumentException iae) {
				out.println(iae.getMessage());
			}

			{// Case 2
				out.println("\nCase 2:");
				List<Appointment> app = appBook
						.getAppointmentsFor("(514)663-4211");
				out.println(app);
			}

			{// Case 3
				out.println("\nCase 3:");
				List<Appointment> app = appBook
						.getAppointmentsFor("(867)123-4567");
				out.println(app);
			}

		} catch (Exception e) {
			out.println(e.getMessage() + " Outer catch");
		}
	}

}
