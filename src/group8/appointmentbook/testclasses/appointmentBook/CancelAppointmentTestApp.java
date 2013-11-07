package group8.appointmentbook.testclasses.appointmentBook;

import static java.lang.System.out;
import static java.util.Calendar.getInstance;
import group8.appointmentbook.business.Appointment;
import group8.appointmentbook.business.AppointmentBook;
import group8.appointmentbook.data.AppointmentListDA;

import java.util.Calendar;
import java.util.List;

import dw314.appointmentbook.exceptions.NonExistingAppointmentException;
import dw314.lib.DailyConstraints;
import dw314.lib.FileType;
import dw314.lib.Person;

public class CancelAppointmentTestApp {

	public static void main(String[] args) {

		try {
			AppointmentBook appBook = new AppointmentBook(
					new AppointmentListDA(
							"datafiles/database/appointments.ser",
							FileType.SERIALIZED), new DailyConstraints());

			try {
				{// Case 1
					out.println("Case 1: ");
					appBook.cancelAppointment(null);
					out.println("Appointment cancelled!");
				}
			} catch (IllegalArgumentException iae) {
				out.println(iae.getMessage());
			}

			{// Case 2
				Calendar date = getInstance();
				date.set(2011, 10, 28, 8, 30, 0);
				out.println("\nCase 2: \nOriginal appointments for 28/11/2011:");
				List<Appointment> apps = appBook.getDailyAppointments(date);
				for (Appointment a : apps)
					out.println(a);
				Appointment dummyApp = new Appointment(2011, 10, 28, 8, 30,
						new Person("A", "Person"));
				appBook.cancelAppointment(dummyApp);
				out.println("\nAppointment cancelled at " + date.getTime());
				out.println("\nRemaining appointments for 28/11/2011:");
				apps = appBook.getDailyAppointments(date);
				for (Appointment a : apps)
					out.println(a);
			}

			try {
				{// Case 3
					Calendar date = getInstance();
					date.set(2011, 10, 28, 8, 30, 0);
					out.println("\nCase 3: \nOriginal appointments for 28/11/2011:");
					List<Appointment> apps = appBook.getDailyAppointments(date);
					for (Appointment a : apps)
						out.println(a);
					Appointment dummyApp = new Appointment(2011, 10, 28, 8, 30,
							new Person("A", "Person"));
					out.println("\nTrying to cancel appointment at " + date.getTime());
					appBook.cancelAppointment(dummyApp);
				}
			} catch (NonExistingAppointmentException neae) {
				out.println(neae.getMessage());
			}
			
			try {
				{// Case 4
					Calendar date = getInstance();
					date.set(2010, 10, 28, 8, 30, 0);
					out.println("\nCase 4: \nOriginal appointments for 28/11/2010:");
					List<Appointment> apps = appBook.getDailyAppointments(date);
					for (Appointment a : apps)
						out.println(a);
					Appointment dummyApp = new Appointment(2010, 10, 28, 8, 30,
							new Person("A", "Person"));
					out.println("\nTrying to cancel appointment at " + date.getTime());
					appBook.cancelAppointment(dummyApp);
				}
			} catch (IllegalArgumentException iae) {
				out.println(iae.getMessage());
			}

		} catch (Exception e) {
			out.println(e.getMessage() + e.getClass());
		}

	}

}
