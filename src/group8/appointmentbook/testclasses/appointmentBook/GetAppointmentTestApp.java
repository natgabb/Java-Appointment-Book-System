package group8.appointmentbook.testclasses.appointmentBook;

import java.util.Calendar;

import group8.appointmentbook.business.Appointment;
import group8.appointmentbook.business.AppointmentBook;
import group8.appointmentbook.data.AppointmentListDA;
import dw314.appointmentbook.exceptions.NonExistingAppointmentException;
import dw314.lib.DailyConstraints;
import dw314.lib.FileType;
import static java.lang.System.*;

public class GetAppointmentTestApp {

	public static void main(String[] args) {
		try {
			AppointmentBook appBook = new AppointmentBook(
					new AppointmentListDA(
							"datafiles/database/appointments.ser",
							FileType.SERIALIZED), new DailyConstraints());
			try {// Case 1
				out.println("Case 1:");
				appBook.getAppointment(null);
			} catch (IllegalArgumentException iae) {
				out.println(iae.getMessage());
			}
			try {// Case 2
				out.println("\nCase 2:");
				Calendar date = Calendar.getInstance();
				date.set(2010, 2, 3, 10, 0, 0);
				Appointment a = appBook.getAppointment(date);
				out.println(a);
			} catch (NonExistingAppointmentException iae) {
				out.println(iae.getMessage());
			}
			try {// Case 3
				out.println("\nCase 3:");
				Calendar date = Calendar.getInstance();
				date.set(2011, 11, 27, 14, 30);
				Appointment a = appBook.getAppointment(date);
				out.println("Appointment found!\n" + a);
			} catch (IllegalArgumentException iae) {
				out.println(iae.getMessage());
			}

		} catch (Exception e) {
			out.println(e.getMessage());
		}

	}

}
