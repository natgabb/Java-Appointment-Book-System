package group8.appointmentbook.testclasses;

import group8.appointmentbook.ui.CalendarPanel;
import java.util.Locale;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class CalendarFrame extends JFrame {
	public CalendarFrame() {
		CalendarPanel panel = new CalendarPanel(Locale.CANADA);
		setTitle("Calendar EX4");
		add(panel);
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public static void main(String[] args) {
		new CalendarFrame();
	}
}
