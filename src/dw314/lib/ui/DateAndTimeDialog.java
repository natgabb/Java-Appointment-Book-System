package dw314.lib.ui;

import static java.awt.BorderLayout.*;

import dw314.lib.ui.DatePanel;
import dw314.lib.ui.TimePanel;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

public class DateAndTimeDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	private static DatePanel dateSelector;
	private static TimePanel timeSelector;

	public static Calendar showDateAndTimeDialog(java.awt.Frame owner,
			boolean modal, Locale aLocale) throws IllegalArgumentException {
		new DateAndTimeDialog(owner, modal, aLocale, Calendar.getInstance());
		Calendar date = dateSelector.getDate();
		date.set(Calendar.HOUR_OF_DAY, timeSelector.getHourOfDay());
		date.set(Calendar.MINUTE, timeSelector.getMinutes());
		date.set(Calendar.SECOND, 0);
		date.set(Calendar.MILLISECOND, 0);
		return date;
	}

	//If the person wants the DateAndTimeDialog to show a specific date.
	public static Calendar showDateAndTimeDialog(java.awt.Frame owner,
			boolean modal, Locale aLocale, Calendar selectedDate) {
		new DateAndTimeDialog(owner, modal, aLocale, selectedDate);
		Calendar date = (Calendar) selectedDate.clone();
		date.set(Calendar.HOUR_OF_DAY, timeSelector.getHourOfDay());
		date.set(Calendar.MINUTE, timeSelector.getMinutes());
		date.set(Calendar.SECOND, 0);
		date.set(Calendar.MILLISECOND, 0);
		return date;
	}

	private DateAndTimeDialog(java.awt.Frame owner, boolean modal,
			Locale aLocale, Calendar date) {
		super(owner, modal);
		this.setTitle("Date/Time");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.add(getDateAndTimePanel(aLocale));
		this.pack();
		this.setLocationRelativeTo(owner);
		dateSelector.setDate(date);
		this.setVisible(true);
	}

	private JPanel getDateAndTimePanel(Locale aLocale) {
		JPanel datePanel = new JPanel(new BorderLayout(5, 5));
		datePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		JButton okButton = new JButton("OK");
		JPanel wrapPanel = new JPanel();
		okButton.setMnemonic('O');
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				DateAndTimeDialog.this.dispose();
			}
		});
		wrapPanel.add(okButton);
		dateSelector = new DatePanel(aLocale);
		timeSelector = new TimePanel();
		datePanel.add(dateSelector, WEST);
		datePanel.add(timeSelector, EAST);
		datePanel.add(wrapPanel, SOUTH);
		return datePanel;
	}
}
