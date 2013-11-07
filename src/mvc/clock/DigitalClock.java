package mvc.clock;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Container;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Font;
import java.util.Observer;
import java.util.Observable;

@SuppressWarnings("serial")
public class DigitalClock extends JFrame implements Observer {
	private JLabel dateLabel;
	private JLabel timeLabel;

	public DigitalClock(Observable model) {
		// register this view with the model
		model.addObserver(this);

		// initialize the frame
		this.setTitle("My first Java GUI Application");
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new Terminator());
		Container contentPane = this.getContentPane();

		// Instantiate the intermediate container
		JPanel digitalClockPanel = new JPanel(new FlowLayout());

		// Instantiate a date label to display the current time
		dateLabel = new JLabel("");
		dateLabel.setFont(new Font("Arial", Font.BOLD, 40));
		dateLabel.setForeground(Color.ORANGE);

		// Instantiate a time label to display the current time
		timeLabel = new JLabel("");
		timeLabel.setFont(new Font("Arial", Font.BOLD, 40));
		timeLabel.setForeground(Color.ORANGE);

		// Add the label to the panel
		digitalClockPanel.add(dateLabel);
		digitalClockPanel.add(timeLabel);

		// Add the panel to the frame's contentPane
		contentPane.add(digitalClockPanel);

		// Show the frame
		this.setSize(650, 150);
		this.setLocationRelativeTo(null); // center the frame on screen
		update(model, null);
		this.setVisible(true);

	}

	public void update(Observable model, Object obj) {
		dateLabel.setText(((Clock) model).getDate());
		timeLabel.setText(((Clock) model).getTime());
	}

	private class Terminator extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			int i = javax.swing.JOptionPane.showConfirmDialog(
					DigitalClock.this, "Exit application", "Exit",
					javax.swing.JOptionPane.YES_NO_OPTION);
			if (i == javax.swing.JOptionPane.YES_OPTION)
				System.exit(0);

		}

	}
}
