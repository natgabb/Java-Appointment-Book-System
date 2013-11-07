package group8.appointmentbook.ui;

import group8.appointmentbook.business.Appointment;
import group8.appointmentbook.ui.AppointmentBookGUI;
import dw314.appointmentbook.interfaces.Schedulable;
import dw314.lib.Person;
import dw314.lib.ui.DatePanel;
import dw314.lib.ui.PersonalDataPanel;
import dw314.lib.ui.TimePanel;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class ScheduleForm extends JFrame {

	private static final long serialVersionUID = 1L;
	private AppointmentBookGUI mainGUI;
	private DatePanel dateSelector;
	private PersonalDataPanel personalData;
	private TimePanel timeSelector;

	public ScheduleForm(AppointmentBookGUI mainGUI) {
		this.mainGUI = mainGUI;
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new Terminate());
		this.setTitle("Schedule An Appointment");
		Container container = getContentPane();
		((JPanel) container).setBorder(new EmptyBorder(10, 10, 10, 10));
		container.setLayout(new BorderLayout());
		container.add(getDateAndTimePanel(), BorderLayout.NORTH);
		personalData = new PersonalDataPanel();
		container.add(personalData, BorderLayout.CENTER);
		container.add(getBottomPanel(), BorderLayout.SOUTH);
		this.pack();
		this.setSize(400, 500);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}

	private JPanel getBottomPanel() {
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 20));
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));
		JButton scheduleButton = new JButton("Schedule");
		scheduleButton.setMnemonic('S');
		scheduleButton.addActionListener(new ScheduleButtonListener());
		buttonPanel.add(scheduleButton);
		panel.add(buttonPanel, scheduleButton);
		return panel;
	}

	private JPanel getDateAndTimePanel() {
		// date and time panel selection
		JPanel datePanel = new JPanel(new BorderLayout());
		datePanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder("Appointment Date And Time"),
				BorderFactory.createEmptyBorder(20, 20, 20, 20)));
		dateSelector = new DatePanel(mainGUI.getTheLocale());
		timeSelector = new TimePanel();
		datePanel.add(dateSelector, BorderLayout.WEST);
		datePanel.add(timeSelector, BorderLayout.EAST);
		return datePanel;
	}

	// private class

	private class ScheduleButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			try {

				// check if all required fields have been entered
				if (personalData.getFirstName().equals("")
						|| personalData.getLastName().equals("")
						|| personalData.getTelephoneNumber().equals("")) {
					java.awt.Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(ScheduleForm.this,
							"You must enter the contact's first name,\n "
									+ "last name and telephone number.",
							"Invalid Data", JOptionPane.ERROR_MESSAGE);
				} else {
					Calendar date = dateSelector.getDate();
					date.set(Calendar.HOUR_OF_DAY, timeSelector.getHourOfDay());
					date.set(Calendar.MINUTE, timeSelector.getMinutes());
					((Schedulable<Appointment>) mainGUI.getModel())
							.scheduleAppointment(new Appointment(date
									.get(Calendar.YEAR), date
									.get(Calendar.MONTH), date
									.get(Calendar.DATE), date
									.get(Calendar.HOUR_OF_DAY), date
									.get(Calendar.MINUTE), new Person(
									personalData.getFirstName(), personalData
											.getLastName(), personalData
											.getTelephoneNumber()),
									personalData.getReason()));
					java.awt.Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(ScheduleForm.this,
							"Appointment has been scheduled.");
				}

			} catch (Exception e) {

				java.awt.Toolkit.getDefaultToolkit().beep();
				JOptionPane.showMessageDialog(ScheduleForm.this,
						e.getMessage(), "Invalid Data",
						JOptionPane.ERROR_MESSAGE);
			}
		}

	}

	private class Terminate extends WindowAdapter {
		public void windowClosing(WindowEvent we) {
			ScheduleForm.this.dispose();
		}
	}
}
