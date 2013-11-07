package dw314.lib;

public final class TimeBlock {
	private int startHr;
	private int startMin;
	private int endHr;
	private int endMin;
	public static final TimeBlock NONE = new TimeBlock(0, 0, 0, 0);

	public TimeBlock(int startHr, int startMin, int endHr, int endMin) {
		this.startHr = startHr;
		this.startMin = startMin;
		this.endHr = endHr;
		this.endMin = endMin;
	}

	public int getStartHours() {
		return startHr;
	}

	public int getStartMinutes() {
		return startMin;
	}

	public int getEndHours() {
		return endHr;
	}

	public int getEndMinutes() {
		return endMin;
	}

	public String toString() {
		String block = String.format("%d.2:%d.2 to %d.2:%d.2", startHr,
				startMin, endHr, endMin);
		return block;
	}

}// end TimePeriod class
