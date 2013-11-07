package group8.appointmentbook.testclasses.appointmentBook;

import java.util.Calendar;
import static java.lang.System.*;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

public class ScheduleAppointmentTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Calendar date = Calendar.getInstance();
		date.set(2013, 3, 9, 10, 10, 0);

		Calendar today = Calendar.getInstance();

		int dateDiff = (date.get(YEAR) - today.get(YEAR)) * 12;
		dateDiff -= today.get(MONTH) - date.get(MONTH);

		out.println(dateDiff);

		today.add(Calendar.MONTH, 3);

		out.println(today.getTime());

	}

}
