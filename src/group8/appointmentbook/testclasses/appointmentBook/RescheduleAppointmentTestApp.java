package group8.appointmentbook.testclasses.appointmentBook;

import static java.lang.System.out;
import static java.util.Calendar.getInstance;
import group8.appointmentbook.business.Appointment;
import group8.appointmentbook.business.AppointmentBook;
import group8.appointmentbook.data.AppointmentListDA;

import java.util.Calendar;
import java.util.List;

import dw314.appointmentbook.exceptions.DateNotAvailableException;
import dw314.appointmentbook.exceptions.NonExistingAppointmentException;
import dw314.lib.DailyConstraints;
import dw314.lib.FileType;

public class RescheduleAppointmentTestApp {

	public static void main(String[] args) {
		try {
			AppointmentBook appBook = new AppointmentBook(
					new AppointmentListDA(
							"datafiles/database/appointments.ser",
							FileType.SERIALIZED), new DailyConstraints());
			try {// Case 1
				out.println("Case 1:");
				appBook.rescheduleAppointment(null, null);
			} catch (IllegalArgumentException iae) {
				out.println(iae.getMessage());
			}
			{// Case 2
				Calendar oldDate = getInstance();
				oldDate.set(2011, 11, 20, 10, 0);
				Calendar newDate = (Calendar) oldDate.clone();
				newDate.add(Calendar.MINUTE, 30);
				out.println("\nCase 2:");
				appBook.rescheduleAppointment(oldDate, newDate);
				List<Appointment> apps = appBook.getDailyAppointments(oldDate);
				for (Appointment a : apps)
					out.println(a);
			}
			try {// Case 3
				Calendar oldDate = getInstance();
				oldDate.set(2011, 11, 20, 10, 30);
				Calendar newDate = (Calendar) oldDate.clone();
				newDate.add(Calendar.MINUTE, 30);
				out.println("\nCase 3:");
				appBook.rescheduleAppointment(oldDate, newDate);
			} catch (DateNotAvailableException dnae) {
				Calendar oldDate = getInstance();
				oldDate.set(2011, 11, 20, 10, 30);
				out.println(dnae.getMessage());
				List<Appointment> apps = appBook.getDailyAppointments(oldDate);
				for (Appointment a : apps)
					out.println(a);
			}
			try {// Case 4
				Calendar oldDate = getInstance();
				oldDate.set(2011, 11, 20, 10, 00);
				Calendar newDate = (Calendar) oldDate.clone();
				newDate.add(Calendar.MINUTE, 30);
				out.println("\nCase 4:");
				appBook.rescheduleAppointment(oldDate, newDate);
			} catch (NonExistingAppointmentException neae) {
				out.println(neae.getMessage());
			}
		} catch (Exception e) {
			out.println(e.getMessage() + e.getClass());
		}

	}

}
