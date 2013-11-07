package group8.appointmentbook.data;

import java.util.List;
import group8.appointmentbook.business.Appointment;
import group8.util.Utilities;

public class PersistAppointments {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		List<Appointment> appointments = null;
		try {
			appointments = AppointmentFileLoader
					.getListFromSequentialFile("datafiles/database/appointments.txt");
			Utilities.serializeObject(appointments,
					"datafiles/database/appointments.ser");
			appointments = (List<Appointment>) Utilities
					.deserializeObject("datafiles/database/appointments.ser");
			for (Appointment appointment : appointments)
				System.out.println(appointment);
		} catch (Exception e) {
			System.out.println("Error - " + e.getMessage());
		}
	}
}
