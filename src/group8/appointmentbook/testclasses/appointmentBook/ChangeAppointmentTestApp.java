package group8.appointmentbook.testclasses.appointmentBook;

import static java.lang.System.out;
import static java.util.Calendar.getInstance;
import group8.appointmentbook.business.Appointment;
import group8.appointmentbook.business.AppointmentBook;
import group8.appointmentbook.data.AppointmentListDA;

import java.util.Calendar;

import dw314.appointmentbook.exceptions.NonExistingAppointmentException;
import dw314.lib.DailyConstraints;
import dw314.lib.FileType;
import dw314.lib.Person;

public class ChangeAppointmentTestApp {

	public static void main(String[] args) {

		try {
			AppointmentBook appBook = new AppointmentBook(
					new AppointmentListDA(
							"datafiles/database/appointments.ser",
							FileType.SERIALIZED), new DailyConstraints());
			try {
				{// Case 1
					out.println("Case 1:");
					appBook.changeAppointmentDetails(null);
				}
			} catch (IllegalArgumentException iae) {
				out.println(iae.getMessage());
			}

			{// Case 2
				Calendar date = getInstance();
				date.set(2011, 10, 28, 8, 30, 0);
				out.println("\nCase 2: \nOriginal appointment:");
				Appointment dummyApp = new Appointment(2011, 10, 28, 8, 30,
						new Person("NewFirstName", "NewLastName"),
						"This is a reason.");
				Appointment app = appBook.getAppointment(date);
				out.println(app);
				appBook.changeAppointmentDetails(dummyApp);
				out.println("\nThe changed appointment:");
				app = appBook.getAppointment(date);
				out.println(app);
			}
			try {
				{// Case 3
					out.println("\nCase 3:");
					Appointment dummyApp = new Appointment(2011, 10, 27, 8, 30,
							new Person("NewFirstName", "NewLastName"),
							"This is a reason.");
					appBook.changeAppointmentDetails(dummyApp);
				}
			} catch (NonExistingAppointmentException iae) {
				out.println(iae.getMessage());
			}
			try {
				{// Case 4
					out.println("\nCase 4:");
					Appointment dummyApp = new Appointment(2010, 10, 27, 8, 30,
							new Person("NewFirstName", "NewLastName"),
							"This is a reason.");
					appBook.changeAppointmentDetails(dummyApp);
				}
			} catch (IllegalArgumentException iae) {
				out.println(iae.getMessage());
			}
		} catch (Exception e) {
			out.println(e.getMessage());
		}

	}
}
