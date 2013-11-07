package group8.appointmentbook.testclasses.appointmentListDA;

import static java.lang.System.out;
import group8.appointmentbook.data.AppointmentListDA;
import group8.appointmentbook.business.Appointment;
import java.io.IOException;

import dw314.lib.FileType;
import dw314.lib.Person;

public class DisconnectTestApp {

	public static void main(String[] args) {

		String filename = "datafiles/testfiles/testAppointmentsDisconnect.txt";
		AppointmentListDA apps = null;

		try {// Sequential file.
			out.println("Case 1");
			apps = new AppointmentListDA(filename, FileType.TEXT_SEQUENTIAL);
			out.println(apps);
			apps.add(new Appointment(2011, 8, 11, 11, 30, new Person("New",
					"Appointment"), "(514)856-4564"));
			apps.disconnect();
			out.println("Disconnected from " + filename + "\n");

			// Reconnects and displays
			apps = new AppointmentListDA(filename, FileType.TEXT_SEQUENTIAL);
			out.println(apps);
		} catch (IOException ioe) {
			out.println(ioe.getMessage());
		} catch (Exception e) {
			out.println(e.getMessage());
		}

		try {// Serialized file.
			out.println("\nCase 2");
			filename = "datafiles/testfiles/testAppointmentsDisconnect.ser";
			apps = new AppointmentListDA(filename, FileType.SERIALIZED);
			out.println(apps);
			apps.add(new Appointment(2011, 8, 11, 11, 30, new Person("New",
					"Appointment"), "(514)856-4564"));
			apps.disconnect();
			out.println("Disconnected from " + filename + "\n");

			// Reconnects and displays
			apps = new AppointmentListDA(filename, FileType.SERIALIZED);
			out.println(apps);
		} catch (IOException ioe) {
			out.println(ioe.getMessage());
		} catch (Exception e) {
			out.println(e.getMessage());
		}

	}
}
