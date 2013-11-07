package group8.appointmentbook.ui;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Locale;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.border.LineBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observer;

@SuppressWarnings("serial")
public class CalendarPanel extends JPanel {
	private JLabel monthHeaderLabel;
	private JLabel[] dayLabels;
	private JPanel daysPanel;
	private Locale locale;
	private Calendar selectedDate;

	private CurrentMonthLabelListener currentMonthLabelListener;
	private NextMonthLabelListener nextMonthLabelListener;
	private PreviousMonthLabelListener previousMonthLabelListener;

	private Observer observer;

	public CalendarPanel(Locale locale) {
		this(locale, null);
	}

	public CalendarPanel(Locale locale, Observer observer) {
		currentMonthLabelListener = new CurrentMonthLabelListener();
		nextMonthLabelListener = new NextMonthLabelListener();
		previousMonthLabelListener = new PreviousMonthLabelListener();
		this.observer = observer;
		this.locale = locale;
		setLayout(new BorderLayout());
		add(getCalendarPanel(), BorderLayout.CENTER);
	}

	private JPanel getCalendarPanel() {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new java.awt.Dimension(300, 175));

		selectedDate = Calendar.getInstance(locale);
		selectedDate.setLenient(false);
		monthHeaderLabel = new JLabel("", JLabel.CENTER);
		monthHeaderLabel.setFont(new Font("Arial", Font.BOLD, 20));
		displayMonthHeader(selectedDate);
		panel.setLayout(new BorderLayout());
		panel.add(monthHeaderLabel, BorderLayout.CENTER);
		panel.add(getDayLabelsPanel(), BorderLayout.SOUTH);
		displayDayNames();
		displayDates(selectedDate);

		JPanel flowPanel = new JPanel();
		flowPanel.add(panel);
		return flowPanel;
	}

	public Calendar getSelectedDate() {
		return (Calendar) selectedDate.clone();
	}

	/*
	 * @throws IllegalArgumentException if date is null or invalid.
	 */
	public void setSelectedDate(Calendar selectedDate) {
		if (selectedDate == null)
			throw new IllegalArgumentException(
					"The selected date can't be null.");
		selectedDate.getTime(); // to validate date
		this.selectedDate = (Calendar) selectedDate.clone();

		updateCalendar();

		// Request that the observer updates its view since the selectedDate
		// has changed.
		if (observer != null)
			observer.update(null, this.selectedDate);
	}

	private void displayDates(Calendar selectedDate) {

		// clone the date referenced by the parameter
		Calendar currentCalendar = (Calendar) selectedDate.clone();
		// set the date to 1 so the date can be used to determine what day of
		// the week the first day of the month falls on
		currentCalendar.set(Calendar.DATE, 1);
		// determine the day number within the week (1 is Sunday – 7 is
		// Saturday)
		int firstDayOfCurrentMonth = currentCalendar.get(Calendar.DAY_OF_WEEK);
		int numDays = currentCalendar.getActualMaximum(Calendar.DATE);
		// Determining the previous month
		Calendar previousMonth = (Calendar) currentCalendar.clone();
		previousMonth.add(Calendar.MONTH, -1);

		int start = firstDayOfCurrentMonth + 6;

		if (firstDayOfCurrentMonth == Calendar.SUNDAY)
			start += 7;

		// Previous month
		int dayPast = previousMonth.getActualMaximum(Calendar.DATE);
		for (int i = start - 1; i > 6; i--, dayPast--) {
			dayLabels[i].setText("" + dayPast);
			dayLabels[i].setForeground(Color.GRAY);
			removeLabelListener(dayLabels[i]);
			dayLabels[i].addMouseListener(previousMonthLabelListener);
		}

		// Current month
		int constraint = numDays + start;
		for (int i = start, dayPresent = 1; i < constraint; i++, dayPresent++) {
			dayLabels[i].setText("" + dayPresent);
			currentCalendar.set(Calendar.DATE, dayPresent);
			dayLabels[i].setForeground(Color.BLACK);
			removeLabelListener(dayLabels[i]);
			dayLabels[i].addMouseListener(currentMonthLabelListener);
		}

		Calendar today = Calendar.getInstance();
		int currentDate = today.get(Calendar.DATE) + start - 1;

		if (today.get(Calendar.YEAR) == selectedDate.get(Calendar.YEAR)
				&& today.get(Calendar.MONTH) == selectedDate
						.get(Calendar.MONTH))
			dayLabels[currentDate].setForeground(Color.RED);

		// Next month
		for (int i = constraint, dayFuture = 1; i < dayLabels.length; i++, dayFuture++) {
			dayLabels[i].setText("" + dayFuture);
			dayLabels[i].setForeground(Color.GRAY);
			removeLabelListener(dayLabels[i]);
			dayLabels[i].addMouseListener(nextMonthLabelListener);
		}

	}

	private void displayDayNames() {
		DateFormatSymbols dateFmt = new DateFormatSymbols(locale);
		String dayNames[] = dateFmt.getShortWeekdays();
		for (int i = 1; i < 8; i++)
			dayLabels[i - 1].setText(dayNames[i]);
	}

	private void displayMonthHeader(Calendar selectedDate) {
		String month = String.format(locale, "%1$tB %1$tY",
				selectedDate.getTime());
		monthHeaderLabel.setText(month);
	}

	private JPanel getDayLabelsPanel() {
		dayLabels = new JLabel[49];
		daysPanel = new JPanel(new GridLayout(0, 7));
		for (int i = 0; i < 49; i++) {
			dayLabels[i] = new JLabel();
			dayLabels[i].setBorder(new LineBorder(Color.black, 1));
			dayLabels[i].setHorizontalAlignment(JLabel.CENTER);
			daysPanel.add(dayLabels[i]);
		}
		return daysPanel;
	}

	private void updateCalendar() {
		displayMonthHeader(selectedDate);
		displayDates(selectedDate);
	}

	private void removeLabelListener(JLabel label) {
		MouseListener[] listeners = label.getMouseListeners();
		if (listeners.length != 0) {
			label.removeMouseListener(listeners[0]);
		}
	}

	// inner classes
	private class CurrentMonthLabelListener extends MouseAdapter {
		public void mouseClicked(MouseEvent event) {
			JLabel dateLabel = (JLabel) event.getSource();
			int intDate = Integer.parseInt(dateLabel.getText());
			selectedDate.set(Calendar.DATE, intDate);
			updateCalendar();
			// notify the observer if there is one
			if (observer != null)
				observer.update(null, selectedDate);
		}
	}

	private class NextMonthLabelListener extends MouseAdapter {
		public void mouseClicked(MouseEvent event) {
			selectedDate.add(Calendar.MONTH, 1);
			JLabel dateLabel = (JLabel) event.getSource();
			int intDate = Integer.parseInt(dateLabel.getText());
			selectedDate.set(Calendar.DATE, intDate);
			updateCalendar();
			// notify the observer if there is one
			if (observer != null)
				observer.update(null, selectedDate);
		}
	}

	private class PreviousMonthLabelListener extends MouseAdapter {
		public void mouseClicked(MouseEvent event) {
			selectedDate.add(Calendar.MONTH, -1);
			JLabel dateLabel = (JLabel) event.getSource();
			int intDate = Integer.parseInt(dateLabel.getText());
			selectedDate.set(Calendar.DATE, intDate);
			updateCalendar();
			// notify the observer if there is one
			if (observer != null)
				observer.update(null, selectedDate);
		}
	}

}
