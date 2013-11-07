package mvc.clock;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Observable;
import javax.swing.Timer;

public class Clock extends Observable {
	private Calendar date;
	
	public Clock()
	{
		date = Calendar.getInstance();
		Timer t = new Timer(1000, new TimerListener());
		t.setInitialDelay(0);
		t.start();
	}
	
	public String getDate()
	{
		return DateFormat.getDateInstance(
				DateFormat.FULL, Locale.CANADA).format(date.getTime());
	}

	public String getTime()
	{
		return DateFormat.getTimeInstance(
				DateFormat.MEDIUM, Locale.CANADA).format(date.getTime());
	}
	
	private void updateTime()
	{
		date = Calendar.getInstance();
		this.setChanged();
		this.notifyObservers();
	}

    //nested controller
	private class TimerListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event) 
		{
			updateTime();
		}
	}
}
