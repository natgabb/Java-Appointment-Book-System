package dw314.lib;

public class DailyConstraints {
	private DailySchedule[] dayConstraints = new DailySchedule[7];
	private int maxMonthsInAdvance;
	private int timeInterval;

	public DailyConstraints() {
		// set default constraints
		maxMonthsInAdvance = 3;
		timeInterval = 30;
		dayConstraints[0] = DailySchedule.NONE;
		for (int i = 1; i < 6; i++)
			dayConstraints[i] = DailySchedule.FULL1;
		dayConstraints[6] = DailySchedule.AM;

	}

	public DailyConstraints(DailySchedule[] dayConstraints,
			int maxMonthsInAdvance, int timeInterval) {
		this.dayConstraints = dayConstraints;
		this.maxMonthsInAdvance = maxMonthsInAdvance;
		this.timeInterval = timeInterval;
	}

	public DailySchedule[] getDayConstraints() {
		return dayConstraints;
	}

	public void setDayConstraints(DailySchedule[] dayConstraints) {
		this.dayConstraints = dayConstraints;
	}

	public int getMaxMonthsInAdvance() {
		return maxMonthsInAdvance;
	}

	public void setMaxMonthsInAdvance(int maxMonthsInAdvance) {
		this.maxMonthsInAdvance = maxMonthsInAdvance;
	}

	public int getTimeInterval() {
		return timeInterval;
	}

	public void setTimeInterval(int timeInterval) {
		this.timeInterval = timeInterval;
	}
}
