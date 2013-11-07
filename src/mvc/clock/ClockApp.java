package mvc.clock;

public class ClockApp {
	public static void main(String[] args) {

		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				Clock clock = new Clock();
				new DigitalClock(clock);
				new TextClock(clock);
			}
		});
	}
}
