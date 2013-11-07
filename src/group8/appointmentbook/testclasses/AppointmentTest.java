package group8.appointmentbook.testclasses;

//import java.util.Calendar;

//import dw314.lib.Person;
//import group8.appointmentbook.business.Appointment;

/**
 * This class tests the different methods of the Appointment class.
 * 
 * @author Caroline Castonguay-Boisvert
 * @author Kim Parisé
 * @author Natacha Gabbamonte
 * 
 * @since 1.5 16/09/2011
 */
public class AppointmentTest {

	public static void main(String[] args) {
/**
		Person person = new Person("Chris", "Goliday", "(514)898-3986");
		Calendar date = Calendar.getInstance();
		date.set(2011, 10, 15, 10, 30, 0);

		// Test Data & Expected Results for Appointment(Calendar date, Person
		// contact) and Case 2 of toString()

		{
			// Case 1 of Appointment(Calendar date,Person contact) and Case 2 of
			// toString()
			System.out
					.println("Testing 2-parameter constructor and Case 2 of toString:\n");
			Appointment app = new Appointment(date, person);
			System.out
					.println("Case 1 of 2-parameter constructor and Case 2 of toString\n"
							+ app + "\n");

			// Case 2 of Appointment(Calendar date,Person contact)

			System.out.println("Case 2:\nOriginal: "
					+ app.getContact().getName());
			person.setName("Larry", "Smith");
			System.out.println("The changed name: " + person.getName()
					+ "\nThe name from the Appointment class: "
					+ app.getContact().getName() + "\n");

			// Resetting the name
			person.setName("Chris", "Goliday");

			// Case 3 of Appointment(Calendar date,Person contact)

			System.out.println("Case 3:\nOriginal: " + app.getDate().getTime());
			date.set(2012, 12, 12, 12, 12, 0);
			System.out.println("The changed date: " + date.getTime()
					+ "\nThe date from the Appointment class: "
					+ app.getDate().getTime() + "\n");

			// Resetting the date
			date.set(2011, 10, 15, 10, 30, 0);
		}

		// Test Data & Expected Results for Appointment(Calendar date, Person
		// contact, String reason) and Case 1 of toString()

		{
			// Case 1 of Appointment(Calendar date, Person contact, String
			// reason) and Case 1 of toString()
			System.out
					.println("\nTesting 3-parameter constructor and Case 1 of toString:\n");
			Appointment app = new Appointment(date, person,
					"Soccer game for son");
			System.out
					.println("Case 1 of 3-parameter constructor and Case 1 of toString():\n"
							+ app + "\n");
			// Case 2 of Appointment(Calendar date,Person contact, String
			// reason)

			System.out.println("Case 2:\nOriginal: "
					+ app.getContact().getName());
			person.setName("Larry", "Smith");
			System.out.println("The changed name: " + person.getName()
					+ "\nThe name from the Appointment class: "
					+ app.getContact().getName() + "\n");

			// Resetting the name
			person.setName("Chris", "Goliday");

			// Case 3 of Appointment(Calendar date,Person contact, String
			// reason)

			System.out.println("Case 3:\nOriginal: " + app.getDate().getTime());
			date.set(2012, 12, 12, 12, 12, 0);
			System.out.println("The changed date: " + date.getTime()
					+ "\nThe date from the Appointment class: "
					+ app.getDate().getTime() + "\n");

			// Resetting the date
			date.set(2011, 10, 15, 10, 30, 0);
		}

		// Test Data & Expected Results for getContact(Person contact)

		{
			System.out.println("\nTesting getContact(Person contact):\n");
			// Case 1

			Appointment app = new Appointment(date, person);
			System.out.println("Case 1:\n" + app.getContact() + "\n");

			// Case 2

			System.out.println("Case 2:\nOriginal: " + app.getContact());
			Person newPerson = app.getContact();
			newPerson.setName("Gary", "Oldman");
			System.out.println("The changed person: " + newPerson
					+ "\nThe Person object from the Appointment class: "
					+ app.getContact() + "\n");
		}

		// Test data & Expected Results for getDate(Calendar date)

		{
			System.out.println("\nTesting getDate(Calendar date):\n");

			// Case 1

			Appointment app = new Appointment(date, person);
			System.out.println("Case 1:\n" + app.getDate().getTime() + "\n");

			// Case 2

			System.out.println("Case 2:\nOriginal: " + app.getDate().getTime());
			Calendar newCalendar = app.getDate();
			newCalendar.set(2012, 12, 12, 12, 12, 0);
			System.out.println("The changed date: " + newCalendar.getTime()
					+ "\nThe Calendar object from the Appointment class: "
					+ app.getDate().getTime() + "\n");
		}

		// Test data & Expected Results for getReason(String reason)

		{
			System.out.println("\nTesting getReason(String reason):\n");

			// Case 1

			Appointment app = new Appointment(date, person,
					"Bring dog to groomer");
			System.out.println("Case 1:\n" + app.getReason() + "\n");
		}

		// Test data & Expected Results for setContact(Person contact)

		{
			System.out.println("\nTesting setContact(Person contact):\n");

			// Case 1

			Appointment app = new Appointment(date, person);

			System.out.println("Case 1:\nThe original: " + app.getContact());

			Person person2 = new Person("Mary", "Johnson", "(514)234-5606");
			app.setContact(person2);

			System.out.println("The changed contact: " + app.getContact()
					+ "\n");
		}

		// Test Data & Expected Results for setReason(String reason)
		{
			System.out.println("\nTesting setReason(String reason):\n");

			// Case 1

			Appointment app = new Appointment(date, person,
					"Going to get a haircut");
			System.out.println("Case 1:\nOriginal reason: " + app.getReason());
			app.setReason("Going to the movies");
			System.out.println("Changed reason: " + app.getReason() + "\n");
		}

		// Test Data & Expected Results for equals(Object obj)
		{
			System.out.println("\nTesting equals(Object obj):\n");

			// Case 1

			Appointment app = new Appointment(date, person);
			String aString = "Hello this is an object";
			System.out.println("Case 1:\nAppointment equals String? "
					+ app.equals(aString) + "\n");

			// Case 2

			Calendar date2 = Calendar.getInstance();
			date2.set(2013, 8, 20, 12, 30, 0);
			Appointment app2 = new Appointment(date2, person);

			System.out
					.println("Case 2:\nAppointment equals Appointment with different date? "
							+ app.equals(app2) + "\n");

			// Case 3

			Person person2 = new Person("Christopher", "Jackson",
					"(514)675-6543");
			Appointment app3 = new Appointment(date, person2);

			System.out
					.println("Case 3:\nAppointment equals Appointment with same date? "
							+ app.equals(app3) + "\n");
		}*/
	}// end main
}