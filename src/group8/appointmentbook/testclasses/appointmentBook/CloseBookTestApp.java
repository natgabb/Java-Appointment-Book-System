package group8.appointmentbook.testclasses.appointmentBook;

import static java.lang.System.out;

import java.util.List;

import group8.appointmentbook.business.Appointment;
import group8.appointmentbook.business.AppointmentBook;
import group8.appointmentbook.data.AppointmentFileLoader;
import group8.appointmentbook.data.AppointmentListDA;

import dw314.lib.DailyConstraints;
import dw314.lib.FileType;

public class CloseBookTestApp {

	public static void main(String[] args) {

		{
			String filePath = "datafiles/testfiles/appointments.ser";
			AppointmentBook appBook;
			try {
				out.println("\nCase 1 (original run) Case 2 (second run):\nOriginal file:");

				List<Appointment> list = AppointmentFileLoader
						.getListFromObjectFile(filePath);
				out.println("Size: " + list.size());
				for (Appointment a : list)
					System.out.println(a);

				appBook = new AppointmentBook(new AppointmentListDA(filePath,
						FileType.SERIALIZED), new DailyConstraints());
				out.println("Appointment book created!");

				appBook.closeBook();
				out.println("Appointment book closed!");

				out.println("New file:");

				List<Appointment> newList = AppointmentFileLoader
						.getListFromObjectFile(filePath);
				out.println("Size: " + newList.size());
				for (Appointment a : newList)
					System.out.println(a);

			} catch (IllegalArgumentException iae) {
				out.println(iae.getMessage());
			} catch (Exception e) {
				out.println(e.getMessage());
			}
		}

		{
			String filePath = "datafiles/testfiles/emptyFile.ser";
			AppointmentBook appBook;
			try {
				out.println("\nCase 3:\nOriginal file:");

				List<Appointment> list = AppointmentFileLoader
						.getListFromObjectFile(filePath);
				out.println("Size: " + list.size());
				for (Appointment a : list)
					System.out.println(a);

				appBook = new AppointmentBook(new AppointmentListDA(filePath,
						FileType.SERIALIZED), new DailyConstraints());
				out.println("Appointment book created!");

				appBook.closeBook();
				out.println("Appointment book closed!");

				out.println("New file:");

				List<Appointment> newList = AppointmentFileLoader
						.getListFromObjectFile(filePath);
				out.println("Size: " + newList.size());
				for (Appointment a : newList)
					System.out.println(a);

			} catch (IllegalArgumentException iae) {
				out.println(iae.getMessage());
			} catch (Exception e) {
				out.println(e.getMessage() + e.getClass());
			}
		}
	}
}
