package dw314.lib.ui;

import java.awt.GridLayout;
import java.util.Calendar;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TimePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTextField hourTextField, minTextField;
	
	public TimePanel() {
		Calendar today = Calendar.getInstance();
		setLayout(new GridLayout(2, 0, 5, 5));
		setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder("Enter Time"),
				BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		hourTextField = new JTextField();
		hourTextField.setHorizontalAlignment(JTextField.RIGHT);
		minTextField = new JTextField();
		minTextField.setHorizontalAlignment(JTextField.RIGHT);
		add(new JLabel("Hour", JLabel.RIGHT));
		add(new JLabel("MIN", JLabel.RIGHT));
		// set time values
		hourTextField.setText("" + today.get(Calendar.HOUR_OF_DAY));
		minTextField.setText("" + today.get(Calendar.MINUTE));

		add(hourTextField);
		add(minTextField);
	}

	// note, since NumberFormatException is a runtime exception it is not
	// necessary to code the throws clause, however, coding it will force
	// the invoking method to pay attention to the exception.
	public int getHourOfDay() throws IllegalArgumentException {
		int hour;
		try {
			hour = Integer.parseInt(hourTextField.getText());
		} catch (NumberFormatException e) {
			throw new NumberFormatException("Hour must be numeric!");
		}

		if (hour < 0 || hour > 23)
			throw new IllegalArgumentException
				("Hours must be between 0 to 23!");
		return hour;
	}

	public int getMinutes() throws IllegalArgumentException {
		int min = 0;
		try {
			min = Integer.parseInt(minTextField.getText());
		} catch (NumberFormatException e) {
			throw new NumberFormatException("Minutes must be numeric!");
		}

		if (min < 0 || min > 59) {
			throw new IllegalArgumentException(
					"Minutes must be between 0 and 59!");
		}
		return min;
	}

	public void setHourTextField(int hour) throws IllegalArgumentException {
		if (hour < 0 || hour > 23)
			throw new IllegalArgumentException	
				("Hours must be between 0 to 23!");

		hourTextField.setText("" + hour);
	}

	public void setMinTextField(int min) throws IllegalArgumentException {
		if (min < 0 || min > 59) {
			throw new IllegalArgumentException(
					"Minutes must be between 0 and 59!");
		}
		minTextField.setText("" + min);
	}

	public void setTime(int hour, int min) {
		setHourTextField(hour);
		setMinTextField(min);
	}
}
