package group8.appointmentbook.application;

import dw314.lib.FileType;
import group8.appointmentbook.business.AppointmentBook;
import group8.appointmentbook.data.AppointmentListDA;
import group8.appointmentbook.ui.AppointmentBookTUI;

public class AppointmentBookTUIApp {

	public static void main(String[] args) {		

		try 
		{
			AppointmentBook book =
				new AppointmentBook
				(new AppointmentListDA("datafiles/database/appointments.ser",
						FileType.SERIALIZED),
							
							new dw314.lib.DailyConstraints());

			new AppointmentBookTUI(book).activate();
		}
		catch (Exception e)
		{
			System.out.println("Error Connecting: " + e);
		}		

	}
}

