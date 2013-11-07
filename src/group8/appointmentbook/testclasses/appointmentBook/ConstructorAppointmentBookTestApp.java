package group8.appointmentbook.testclasses.appointmentBook;

import static java.lang.System.out;
import group8.appointmentbook.business.AppointmentBook;
import group8.appointmentbook.data.AppointmentListDA;
import dw314.lib.DailyConstraints;
import dw314.lib.FileType;

public class ConstructorAppointmentBookTestApp {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		try {
			out.println("Case 1");
			AppointmentBook appBook = new AppointmentBook(null,
					new DailyConstraints());
			out.println("Object created!");
		} catch (IllegalArgumentException iae) {
			out.println(iae.getMessage());
		}
		try {
			out.println("Case 2");
			AppointmentBook appBook2 = new AppointmentBook(
					new AppointmentListDA(
							"datafiles/database/appointments.ser",
							FileType.SERIALIZED), new DailyConstraints());
			out.println("Object created!");
		} catch (IllegalArgumentException iae) {
			out.println(iae.getMessage());
		} catch (Exception e) {
			out.println(e.getMessage());
		}
	}
}
