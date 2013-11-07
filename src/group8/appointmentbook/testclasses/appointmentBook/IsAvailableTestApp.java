package group8.appointmentbook.testclasses.appointmentBook;

import group8.appointmentbook.business.AppointmentBook;
import group8.appointmentbook.data.AppointmentListDA;
import dw314.lib.DailyConstraints;
import dw314.lib.FileType;

import java.util.Calendar;
import static java.lang.System.*;
import static java.util.Calendar.*;

public class IsAvailableTestApp {

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

			{// Case 1
				Calendar date = getInstance();
				date.set(2011, 10, 16, 7, 0);
				out.println("Case 1: " + appBook.isAvailable(date));
			}
			{// Case 2
				Calendar date = getInstance();
				date.set(2011, 10, 16, 12, 30);
				out.println("\nCase 2: " + appBook.isAvailable(date));
			}
			{// Case 3
				Calendar date = getInstance();
				date.set(2011, 10, 16, 12, 00);
				out.println("\nCase 3: " + appBook.isAvailable(date));
			}
			{// Case 4
				Calendar date = getInstance();
				date.set(2011, 10, 16, 11, 30);
				out.println("\nCase 4: " + appBookTimeInt1h.isAvailable(date));
			}
			{// Case 5
				Calendar date = getInstance();
				date.set(2011, 10, 16, 21, 00);
				out.println("\nCase 5: " + appBook.isAvailable(date));
			}
			{// Case 6
				Calendar date = getInstance();
				date.set(2012, 0, 7, 7, 00);
				out.println("\nCase 6: " + appBook.isAvailable(date));
			}
			{// Case 7
				Calendar date = getInstance();
				date.set(2012, 0, 7, 14, 00);
				out.println("\nCase 7: " + appBook.isAvailable(date));
			}
			{// Case 8
				Calendar date = getInstance();
				date.set(2011, 11, 10, 10, 30);
				out.println("\nCase 8: " + appBookTimeInt1h.isAvailable(date));
			}
			{// Case 9
				Calendar date = getInstance();
				date.set(2011, 11, 10, 9, 30);
				out.println("\nCase 9: " + appBookTimeInt1h.isAvailable(date));
			}
			{// Case 10
				Calendar date = getInstance();
				date.set(2011, 11, 9, 8, 30);
				out.println("\nCase 10: " + appBook.isAvailable(date));
			}
			{// Case 11
				Calendar date = getInstance();
				date.set(2012, 1, 11, 0, 0);
				out.println("\nCase 11: " + appBook.isAvailable(date));
			}
			{// Case 12
				Calendar date = getInstance();
				date.set(YEAR, 2008);
				out.println("\nCase 12: " + appBook.isAvailable(date));
			}
			{// Case 13
				Calendar date = getInstance();
				date.set(2011, 11, 18, 10, 30);
				out.println("\nCase 13: " + appBook.isAvailable(date));
			}
			{// Case 14
				out.print("\nCase 14: ");
				try {
					appBook.isAvailable(null);
				} catch (IllegalArgumentException iae) {
					System.out.println(iae.getMessage());
				}
			}
			{// Case 15
				Calendar date = getInstance();
				date.set(2012, 0, 11, 13, 30);
				out.println("\nCase 15: " + appBook.isAvailable(date));
			}
			{// Case 16
				Calendar date = getInstance();
				date.set(2012, 0, 11, 8, 00);
				out.println("\nCase 16: " + appBook.isAvailable(date));
			}
			{// Case 17
				Calendar date = getInstance();
				date.set(2012, 0, 14, 8, 00);
				out.println("\nCase 17: " + appBook.isAvailable(date));
			}
		} catch (Exception e) {
			out.println(e.getMessage() + e.getClass());
		}
	}
}
