package group8.appointmentbook.ui;

import group8.appointmentbook.business.Appointment;
import group8.appointmentbook.business.AppointmentBook;
import group8.appointmentbook.data.AppointmentListDA;

import dw314.appointmentbook.exceptions.NonExistingAppointmentException;
import dw314.lib.DailyConstraints;
import dw314.lib.FileType;
import dw314.lib.Person;
import dw314.lib.ui.DateAndTimeDialog;

import java.text.DateFormat;

import static java.awt.BorderLayout.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static java.util.Calendar.*;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Timer;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 * This class implements an AppointmentBook and displays a frame with a calendar
 * and the selected date's appointments and four buttons that allow the user to
 * view the details of an appointment, available time slots, schedule an
 * appointment, and search through the appointments with a specified telephone
 * number. It deletes the appointments that have past once the appointment book
 * has been closed.
 * 
 * @author Natacha Gabbamonte
 * @author Caroline Castonguay-Boisvert
 * @author Kim Parisé
 * 
 * @since JDK 1.5 09/12/2011
 */
@SuppressWarnings("serial")
public class AppointmentBookGUI extends JFrame implements Observer {

	private Locale locale = Locale.CANADA;

	private AppointmentBook appBook;

	private ImageIcon[] icons = new ImageIcon[12];
	private JLabel imageLabel = new JLabel();
	private Calendar currentlySelectedDate = getInstance();
	private JLabel rightLabel = new JLabel();
	private JTextArea contentTextArea;
	private JButton todaysAppsButton;
	private CalendarPanel calendarPanel;

	/**
	 * This no-parameter constructor instantiates an AppointmentBookGUI.
	 */
	public AppointmentBookGUI() {
		try {
			appBook = new AppointmentBook(
					new AppointmentListDA(
							"datafiles/database/appointments.ser",
							FileType.SERIALIZED), new DailyConstraints());
			appBook.addObserver(this);
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		for (int i = 0; i < 12; i++) {
			icons[i] = new ImageIcon("images/month" + (i + 1) + ".gif");
		}
		add(getTitlePanel(), NORTH);
		add(getCalendarPanel(), WEST);
		add(getContentPanel(), CENTER);
		add(getNavigationPanel(), EAST);
		add(getSouthPanel(), SOUTH);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Timer t = new Timer(1000, new TimerListener());
		t.setInitialDelay(0);
		t.start();

		// For when the window is closed
		// It will delete the past appointments
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				try {
					appBook.closeBook();
					JOptionPane.showMessageDialog(null,
							"Thank you for using Group 8's "
									+ "Appointment Book System.");
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}
		});

		todaysAppsButton.doClick();
	}

	/**
	 * Starts the program.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new AppointmentBookGUI();
			}
		});
	}

	/**
	 * This returns the AppointmentBook associated with this GUI.
	 * 
	 * @return the AppointmentBook object
	 */
	public AppointmentBook getModel() {
		return appBook;
	}

	/**
	 * This returns the locale of this appointment book.
	 * 
	 * @return the locale
	 */
	public Locale getTheLocale() {
		return locale;
	}

	/**
	 * Updates the view.
	 * 
	 * @param o
	 *            The Observable object sending the request.
	 * @param arg
	 *            The argument(s) that is sent in.
	 */
	@Override
	public void update(Observable o, Object arg) {
		String allApps = "";

		// Checks if there is only one date sent in.
		if (arg instanceof Calendar) {
			Calendar date = (Calendar) arg;
			int newMonth = date.get(MONTH);
			if (newMonth != currentlySelectedDate.get(MONTH))
				imageLabel.setIcon(icons[newMonth]);
			currentlySelectedDate = (Calendar) date.clone();

			allApps = getFormattedAppointmentList(date,
					appBook.getDailyAppointments(date));
		}
		// Checks if there is more than one date sent in.
		else if (arg instanceof Calendar[]) {
			Calendar[] dates = (Calendar[]) arg;
			allApps = getFormattedAppointmentList(dates[0],
					appBook.getDailyAppointments(dates[0]));
			// If the date didn't change, it won't display again.
			if (dates[0].get(MONTH) != dates[1].get(MONTH)
					|| dates[0].get(DATE) != dates[1].get(DATE))
				allApps += "\n\n"
						+ getFormattedAppointmentList(dates[1],
								appBook.getDailyAppointments(dates[1]));
		}
		if (allApps.length() != 0)
			contentTextArea.setText(allApps);
	}

	/*
	 * Creates the panel holding the calendar and each month's corresponding
	 * images.
	 * 
	 * @return The CalendarPanel
	 */
	private JPanel getCalendarPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		calendarPanel = new CalendarPanel(locale, this);

		int i = Calendar.getInstance().get(MONTH);
		imageLabel.setIcon(icons[i]);

		panel.add(imageLabel, NORTH);
		panel.add(calendarPanel, SOUTH);

		JPanel wrapperPanel = new JPanel();
		wrapperPanel.add(panel, CENTER);
		return wrapperPanel;
	}

	/*
	 * Creates the panel holding the content and Today's Appointments button.
	 * 
	 * @return The ContentPanel
	 */
	private JPanel getContentPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		todaysAppsButton = new JButton("Today's Appointments");
		todaysAppsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				calendarPanel.setSelectedDate(getInstance());
			}
		});
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(todaysAppsButton);
		panel.add(buttonPanel, SOUTH);

		contentTextArea = new JTextArea(20, 95);
		contentTextArea.setEditable(false);
		contentTextArea.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.GRAY, 1),
				BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		contentTextArea.setFont(new Font("Courier New", Font.PLAIN, 12));
		JScrollPane scrollPane = new JScrollPane(contentTextArea,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		panel.add(scrollPane, NORTH);
		panel.add(new JLabel(" "));
		JPanel wrapperPanel = new JPanel();
		wrapperPanel.add(panel);
		return wrapperPanel;
	}

	/*
	 * Creates the panel holding the navigation buttons.
	 * 
	 * @return The NavigationPanel
	 */
	private JPanel getNavigationPanel() {
		JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
		panel.setAlignmentX(CENTER_ALIGNMENT);
		panel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder("Options"),
				BorderFactory.createEmptyBorder(15, 40, 15, 40)));

		JButton displayDetailsButton = new JButton("Display Details");
		displayDetailsButton
				.addActionListener(new DisplayDetailsButtonListener());
		displayDetailsButton.setMnemonic('d');

		JButton getAvailableDatesButton = new JButton("Get Available Dates");
		getAvailableDatesButton
				.addActionListener(new GetAvailableDatesButtonListener());
		getAvailableDatesButton.setMnemonic('g');

		JButton scheduleAppointmentButton = new JButton("Schedule Appointment");
		scheduleAppointmentButton
				.addActionListener(new ScheduleAppointmentButtonListener());
		scheduleAppointmentButton.setMnemonic('s');

		JButton searchByTelephoneButton = new JButton("Search by Telephone");
		searchByTelephoneButton
				.addActionListener(new SearchByTelephoneButtonListener());
		searchByTelephoneButton.setMnemonic('t');

		panel.add(displayDetailsButton);
		panel.add(getAvailableDatesButton);
		panel.add(scheduleAppointmentButton);
		panel.add(searchByTelephoneButton);

		return panel;
	}

	/*
	 * Creates the panel containing the authors' names, and today's date.
	 * 
	 * @return The SouthPanel
	 */
	private JPanel getSouthPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		JLabel leftLabel = new JLabel(
				"Natacha Gabbamonte, Kim Parisé, Caroline Castonguay-Boisvert");
		panel.add(leftLabel, WEST);
		panel.add(rightLabel, EAST);
		panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 20, 20));
		return panel;
	}

	/*
	 * Creates the panel holding the title.
	 * 
	 * @return The TitlePanel
	 */
	private JPanel getTitlePanel() {
		JPanel panel = new JPanel(new BorderLayout());
		Color colour = Color.BLUE;
		Font font = new Font("Arial", Font.BOLD, 18);

		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		JLabel label = new JLabel("Dawson College", SwingConstants.CENTER);
		label.setForeground(colour);
		label.setFont(font);
		JLabel label2 = new JLabel("Group 8's Appointment Book System",
				SwingConstants.CENTER);
		label2.setForeground(colour);
		label2.setFont(font);

		label2.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));
		panel.add(label, NORTH);
		panel.add(label2, SOUTH);
		return panel;
	}

	/*
	 * Creates a formatted string containing all the appointments sent in.
	 * 
	 * @param displayDate The starting date of the appointments.
	 * 
	 * @param apps The list of appointments.
	 * 
	 * @return The formatted string
	 */
	private String getFormattedAppointmentList(Calendar displayDate,
			List<Appointment> apps) {
		String formattedStr = "Scheduled appointments for "
				+ DateFormat.getDateInstance(DateFormat.FULL, locale).format(
						displayDate.getTime()) + "\n";
		Calendar aDate;
		Person contact;
		String time;

		for (Appointment a : apps) {
			aDate = a.getDate();
			contact = a.getContact();
			time = DateFormat.getTimeInstance(DateFormat.SHORT, locale).format(
					aDate.getTime());
			formattedStr += String.format("%3s%-27s%3s%10s%14s%3s%s\n", "",
					contact.getName().getFullName(), " @ ", time,
					contact.getTelephoneNumber(), "", a.getReason());
		}
		// displaying appointments
		return formattedStr;
	}

	/*
	 * This sets the clock.
	 */
	private class TimerListener implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			Calendar today = Calendar.getInstance();
			rightLabel.setText(DateFormat.getDateInstance(DateFormat.FULL,
					locale).format(today.getTime())
					+ "  "
					+ DateFormat.getTimeInstance(DateFormat.MEDIUM, locale)
							.format(today.getTime()));
		}
	}

	/*
	 * This handles the Display Details button when clicked.
	 */
	private class DisplayDetailsButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			Calendar date = DateAndTimeDialog.showDateAndTimeDialog(
					AppointmentBookGUI.this, true, locale,
					currentlySelectedDate);
			try {
				Appointment app = appBook.getAppointment(date);

				@SuppressWarnings("unused")
				SearchForm searchForm = new SearchForm(AppointmentBookGUI.this,
						app);

			} catch (NonExistingAppointmentException neae) {
				JOptionPane.showMessageDialog(AppointmentBookGUI.this,
						neae.getMessage());
			}
		}
	}

	/*
	 * This handles the Get Available Dates button when clicked.
	 */
	private class GetAvailableDatesButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			try {
				Calendar startingDate = DateAndTimeDialog
						.showDateAndTimeDialog(AppointmentBookGUI.this, true,
								locale);
				List<Calendar> dates = appBook
						.getNextAvailableDatesStarting(startingDate);
				String availableDates;

				if (dates.size() > 0) {

					availableDates = "Available times starting "
							+ DateFormat.getDateInstance(DateFormat.LONG,
									locale).format(startingDate.getTime())
							+ " "
							+ DateFormat.getTimeInstance(DateFormat.SHORT,
									locale).format(startingDate.getTime())
							+ "\n\n";

					for (Calendar date : dates)
						availableDates += String.format(
								"%3s%s%5s%8s\n",
								"",
								DateFormat.getDateInstance(DateFormat.LONG,
										locale).format(date.getTime()),
								"",
								DateFormat.getTimeInstance(DateFormat.SHORT,
										locale).format(date.getTime()));
				} else {
					availableDates = "There are no available dates starting\n\n"
							+ DateFormat.getDateInstance(DateFormat.FULL,
									locale).format(startingDate.getTime())
							+ "\n\n\nCheck the date to ensure that it is correct."
							+ "\n\nIf the date is correct, you are trying to schedule too far in"
							+ " advance. Try again tomorrow.";
				}
				contentTextArea.setText(availableDates);
			} catch (IllegalArgumentException iae) {
				JOptionPane.showMessageDialog(AppointmentBookGUI.this,
						iae.getMessage());
			}
		}
	}// End GetAvailableDatesButtonListener

	/*
	 * This handles the Schedule Appointment button when clicked.
	 */
	private class ScheduleAppointmentButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			@SuppressWarnings("unused")
			ScheduleForm scheduleForm = new ScheduleForm(
					AppointmentBookGUI.this);
		}
	}

	/*
	 * This handles the Search by Telephone button when clicked.
	 */
	private class SearchByTelephoneButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			String telephoneNumber = JOptionPane.showInputDialog(null,
					"Enter the telephone number as (999)999-9999", "Input",
					JOptionPane.QUESTION_MESSAGE);
			if (telephoneNumber != null) {
				List<Appointment> apps = appBook
						.getAppointmentsFor(telephoneNumber);

				String formattedApps;
				if (apps.size() != 0) {

					formattedApps = "Scheduled appointments for telephone number "
							+ telephoneNumber + "\n\n";

					Calendar date;
					for (Appointment a : apps) {
						date = a.getDate();
						formattedApps += String.format(
								"%4s%-27s%-20s%s%s\n",
								"",
								a.getContact().getName().getFullName(),
								DateFormat.getDateInstance(DateFormat.LONG,
										locale).format(date.getTime()),
								"@ ",
								DateFormat.getTimeInstance(DateFormat.SHORT,
										locale).format(date.getTime()));
					}
				} else
					formattedApps = "There are no scheduled appointments for telephone number "
							+ telephoneNumber;
				contentTextArea.setText(formattedApps);
			}
		}
	}// End SearchByTelephoneNumber
}
