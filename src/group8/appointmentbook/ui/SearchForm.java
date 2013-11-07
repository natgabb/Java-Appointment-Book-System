package group8.appointmentbook.ui;

import static java.awt.BorderLayout.*;

import dw314.appointmentbook.interfaces.Schedulable;
import dw314.lib.Person;
import dw314.lib.ui.DatePanel;
import dw314.lib.ui.PersonalDataPanel;
import dw314.lib.ui.TimePanel;
import group8.appointmentbook.business.Appointment;
import group8.appointmentbook.ui.AppointmentBookGUI;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Calendar;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class SearchForm extends JFrame {

	private static final long serialVersionUID = 1L;
	private AppointmentBookGUI mainGUI;
	private DatePanel dateSelector;
	private PersonalDataPanel personalData;
	private TimePanel timeSelector;
	private Appointment appointment;
	private String dateTimeStr = "%1$tA, %1$tB %1$td, %1$tY %1$tl:%1$tM %Tp";
	private JLabel dateLabel;
	private JButton rescheduleButton;
	private JButton cancelButton;
	private JPanel dateAndTimePanel;

	public SearchForm(AppointmentBookGUI mainGUI, Appointment appointment) {
		this.mainGUI = mainGUI;
		this.appointment = appointment;
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new Terminate());
		this.setTitle("Appointment Details");
		this.add(getSearchPanel());
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}

	private JPanel getButtonPanel() {
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 20));
		// top, Left, Bottom, Right
		JPanel buttonPanel = new JPanel(new GridLayout(0, 2, 5, 5));
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));
		cancelButton = new JButton("Cancel Appointment");
		cancelButton.setEnabled(true);
		cancelButton.setMnemonic('C');
		cancelButton.addActionListener(new CancelButtonListener());
		JButton modifyButton = new JButton("Modify Details");
		modifyButton.setMnemonic('M');
		modifyButton.addActionListener(new ModifyButtonListener());
		buttonPanel.add(cancelButton);
		buttonPanel.add(modifyButton);
		panel.add(buttonPanel);
		return panel;
	}

	private JPanel getCenterPanel() {
		JPanel center = new JPanel(new BorderLayout(5, 5));
		personalData = new PersonalDataPanel();
		center.add(personalData, CENTER);
		center.add(getButtonPanel(), SOUTH);
		return center;
	}

	private JPanel getDateAndTimePanel() {
		// date and time panel selection
		dateAndTimePanel = new JPanel(new BorderLayout(5, 5));
		dateAndTimePanel
				.setBorder(BorderFactory.createCompoundBorder(
						BorderFactory
								.createTitledBorder("To Reschedule Change the Date And Time To"),
						BorderFactory.createEmptyBorder(20, 20, 20, 20)));
		// top, Left, Bottom, Right
		dateSelector = new DatePanel(mainGUI.getTheLocale());
		dateSelector.setDate(appointment.getDate());
		timeSelector = new TimePanel();
		dateAndTimePanel.add(dateSelector, WEST);
		dateAndTimePanel.add(timeSelector, EAST);
		dateAndTimePanel.setEnabled(true);
		return dateAndTimePanel;
	}

	private JPanel getScheduledDatePanel() {
		JPanel panel = new JPanel();
		String strDate = String.format(mainGUI.getTheLocale(), dateTimeStr,
				appointment.getDate().getTime());
		dateLabel = new JLabel("Appointment date " + strDate);
		panel.add(dateLabel);

		return panel;
	}

	private JPanel getSearchPanel() {
		JPanel panel = new JPanel(new BorderLayout(5, 5));
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		panel.setLayout(new BorderLayout());
		panel.add(getScheduledDatePanel(), NORTH);
		panel.add(getCenterPanel(), CENTER);
		panel.add(getSouthPanel(), SOUTH);
		// initialize the personal data, date and time panels
		personalData.setFirstNameTextField(appointment.getContact().getName()
				.getFirstName());
		personalData.setLastNameTextField(appointment.getContact().getName()
				.getLastName());
		personalData.setTelephoneTextField(appointment.getContact()
				.getTelephoneNumber());
		personalData.setReasonTextField(appointment.getReason());
		dateSelector.setDate(appointment.getDate());
		timeSelector.setTime(appointment.getDate().get(Calendar.HOUR_OF_DAY),
				appointment.getDate().get(Calendar.MINUTE));
		return panel;
	}

	private JPanel getSouthPanel() {
		JPanel south = new JPanel(new BorderLayout(5, 5));
		south.add(getDateAndTimePanel(), NORTH);
		JPanel button = new JPanel();
		rescheduleButton = new JButton("Reschedule Appointment");
		rescheduleButton.setEnabled(true);
		rescheduleButton.setMnemonic('R');
		rescheduleButton.addActionListener(new RescheduleButtonListener());
		button.add(rescheduleButton);
		south.add(button, SOUTH);
		return south;
	}

	// private class

	private class CancelButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			try {

				int cancelAppointment = JOptionPane.showConfirmDialog(
						SearchForm.this,
						"Do you want to cancel the appointment?",
						"Cancel Appontment", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE);
				if (cancelAppointment == JOptionPane.YES_OPTION) {
					((Schedulable<Appointment>) mainGUI.getModel())
							.cancelAppointment(appointment);
					java.awt.Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(SearchForm.this,
							"Appointment has been cancelled.");
				}
				cancelButton.setEnabled(false);

			} catch (Exception e) {

				java.awt.Toolkit.getDefaultToolkit().beep();
				JOptionPane.showMessageDialog(SearchForm.this, e.getMessage(),
						"Invalid Data", JOptionPane.ERROR_MESSAGE);
			}
		}

	}

	private class ModifyButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			try {

				Calendar date = dateSelector.getDate();
				date.set(Calendar.HOUR_OF_DAY, timeSelector.getHourOfDay());
				date.set(Calendar.MINUTE, timeSelector.getMinutes());
				((Schedulable<Appointment>) mainGUI.getModel())
						.changeAppointmentDetails(new Appointment(date
								.get(Calendar.YEAR), date.get(Calendar.MONTH),
								date.get(Calendar.DATE), date
										.get(Calendar.HOUR_OF_DAY), date
										.get(Calendar.MINUTE), new Person(
										personalData.getFirstName(),
										personalData.getLastName(),
										personalData.getTelephoneNumber()),
								personalData.getReason()));

				java.awt.Toolkit.getDefaultToolkit().beep();
				JOptionPane.showMessageDialog(SearchForm.this,
						"Appointment has been modified.");

			} catch (Exception e) {

				java.awt.Toolkit.getDefaultToolkit().beep();
				JOptionPane.showMessageDialog(SearchForm.this, e.getMessage(),
						"Invalid Data", JOptionPane.ERROR_MESSAGE);
			}
		}

	}

	private class RescheduleButtonListener implements ActionListener {
		@SuppressWarnings("rawtypes")
		public void actionPerformed(ActionEvent ae) {
			try {

				Calendar date = dateSelector.getDate();
				date.set(Calendar.HOUR_OF_DAY, timeSelector.getHourOfDay());
				date.set(Calendar.MINUTE, timeSelector.getMinutes());
				date.set(Calendar.SECOND, 0);
				date.set(Calendar.MILLISECOND, 0);

				((Schedulable) mainGUI.getModel()).rescheduleAppointment(
						appointment.getDate(), date);

				java.awt.Toolkit.getDefaultToolkit().beep();
				JOptionPane.showMessageDialog(SearchForm.this,
						"Appointment has been rescheduled.");
				// following statements will be executed after the
				// closure of the above modal showMessageDialog
				String strDate = String.format(mainGUI.getTheLocale(),
						dateTimeStr, date.getTime());
				dateLabel.setText("Appointment date " + strDate);

				rescheduleButton.setEnabled(false);
				cancelButton.setEnabled(false);
				dateSelector.setEnabled(false);
				timeSelector.setEnabled(false);

			} catch (Exception e) {

				java.awt.Toolkit.getDefaultToolkit().beep();
				JOptionPane.showMessageDialog(SearchForm.this, e.getMessage(),
						"Invalid Data", JOptionPane.ERROR_MESSAGE);
			}
			// will only be done after you interact with the showMessageDialog
			// since it is a modal window.
		}
	}

	private class Terminate extends WindowAdapter {
		public void windowClosing(WindowEvent we) {

			SearchForm.this.dispose();
		}
	}

}
