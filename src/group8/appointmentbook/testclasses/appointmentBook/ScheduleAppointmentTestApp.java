package group8.appointmentbook.testclasses.appointmentBook;

import static java.lang.System.out;
import group8.appointmentbook.business.Appointment;
import group8.appointmentbook.business.AppointmentBook;
import group8.appointmentbook.data.AppointmentListDA;

import dw314.appointmentbook.exceptions.DateNotAvailableException;
import dw314.lib.DailyConstraints;
import dw314.lib.FileType;
import dw314.lib.Person;

public class ScheduleAppointmentTestApp {

	public static void main(String[] args) {
		try {
			AppointmentBook appBook = new AppointmentBook(
					new AppointmentListDA(
							"datafiles/database/appointments.ser",
							FileType.SERIALIZED), new DailyConstraints());

			DailyConstraints dCon = new DailyConstraints();
			dCon.setTimeInterval(60);
			AppointmentBook appBookTimeInt1h = new AppointmentBook(
					new AppointmentListDA(
							"datafiles/database/appointments.ser",
							FileType.SERIALIZED), dCon);

			try {// Case 1
				out.println("Case 1:");
				appBook.scheduleAppointment(null);
			} catch (IllegalArgumentException iae) {
				out.println(iae.getMessage());
			}
			try {// Case 2
				out.println("\nCase 2:");
				Appointment dummyApp = new Appointment(2011, 11, 16, 7, 30,
						new Person("A", "Person"));
				appBook.scheduleAppointment(dummyApp);
			} catch (DateNotAvailableException dnae) {
				out.println(dnae.getMessage());
			}
			try {// Case 3
				out.println("\nCase 3:");
				Appointment dummyApp = new Appointment(2011, 11, 16, 12, 30,
						new Person("A", "Person"));
				appBook.scheduleAppointment(dummyApp);
			} catch (DateNotAvailableException dnae) {
				out.println(dnae.getMessage());
			}
			try {// Case 4
				out.println("\nCase 4:");
				Appointment dummyApp = new Appointment(2011, 11, 16, 12, 00,
						new Person("A", "Person"));
				appBook.scheduleAppointment(dummyApp);
			} catch (DateNotAvailableException dnae) {
				out.println(dnae.getMessage());
			}
			try {// Case 5
				out.println("\nCase 5:");
				Appointment dummyApp = new Appointment(2011, 11, 16, 11, 30,
						new Person("A", "Person"));
				appBookTimeInt1h.scheduleAppointment(dummyApp);
			} catch (DateNotAvailableException dnae) {
				out.println(dnae.getMessage());
			}
			try {// Case 6
				out.println("\nCase 6:");
				Appointment dummyApp = new Appointment(2011, 11, 16, 20, 30,
						new Person("A", "Person"));
				appBook.scheduleAppointment(dummyApp);
			} catch (DateNotAvailableException dnae) {
				out.println(dnae.getMessage());
			}
			try {// Case 7
				out.println("\nCase 7:");
				Appointment dummyApp = new Appointment(2011, 11, 17, 4, 30,
						new Person("A", "Person"));
				appBook.scheduleAppointment(dummyApp);
			} catch (DateNotAvailableException dnae) {
				out.println(dnae.getMessage());
			}
			try {// Case 8
				out.println("\nCase 8:");
				Appointment dummyApp = new Appointment(2011, 11, 17, 16, 30,
						new Person("A", "Person"));
				appBook.scheduleAppointment(dummyApp);
			} catch (DateNotAvailableException dnae) {
				out.println(dnae.getMessage());
			}
			try {// Case 9
				out.println("\nCase 9:");
				Appointment dummyApp = new Appointment(2011, 11, 19, 15, 00,
						new Person("A", "Person"));
				appBookTimeInt1h.scheduleAppointment(dummyApp);
			} catch (DateNotAvailableException dnae) {
				out.println(dnae.getMessage());
			}
			try {// Case 10
				out.println("\nCase 10:");
				Appointment dummyApp = new Appointment(2011, 11, 19, 14, 00,
						new Person("A", "Person"));
				appBookTimeInt1h.scheduleAppointment(dummyApp);
			} catch (DateNotAvailableException dnae) {
				out.println(dnae.getMessage());
			}
			try {// Case 11
				out.println("\nCase 11:");
				Appointment dummyApp = new Appointment(2011, 11, 19, 14, 30,
						new Person("A", "Person"));
				appBook.scheduleAppointment(dummyApp);
			} catch (DateNotAvailableException dnae) {
				out.println(dnae.getMessage());
			}
			try {// Case 12
				out.println("\nCase 12:");
				Appointment dummyApp = new Appointment(2013, 10, 16, 12, 30,
						new Person("A", "Person"));
				appBook.scheduleAppointment(dummyApp);
			} catch (DateNotAvailableException dnae) {
				out.println(dnae.getMessage());
			}
			try {// Case 13
				out.println("\nCase 13:");
				Appointment dummyApp = new Appointment(2010, 10, 16, 12, 30,
						new Person("A", "Person"));
				appBook.scheduleAppointment(dummyApp);
			} catch (DateNotAvailableException dnae) {
				out.println(dnae.getMessage());
			}
			try {// Case 14
				out.println("\nCase 14:");
				Appointment dummyApp = new Appointment(2011, 11, 18, 12, 30,
						new Person("A", "Person"));
				appBook.scheduleAppointment(dummyApp);
			} catch (DateNotAvailableException dnae) {
				out.println(dnae.getMessage());
			}
			{// Case 15
				out.println("\nCase 15:");
				Appointment dummyApp = new Appointment(2011, 11, 14, 13, 30,
						new Person("A", "Person"));
				appBook.scheduleAppointment(dummyApp);
			}
			{// Case 16
				out.println("\nCase 16:");
				Appointment dummyApp = new Appointment(2011, 11, 15, 8, 00,
						new Person("A", "Person"));
				appBook.scheduleAppointment(dummyApp);
			}
			{// Case 17
				out.println("\nCase 17:");
				Appointment dummyApp = new Appointment(2011, 11, 17, 8, 00,
						new Person("A", "Person"));
				appBook.scheduleAppointment(dummyApp);
			}
		} catch (Exception e) {
			out.println(e.getMessage() + e.getClass());
		}

	}

}
