package dw314.lib;

public enum DailySchedule {
	FULL1(new TimeBlock(8, 00, 17, 00), new TimeBlock(12, 00, 13, 30)), FULL2(
			new TimeBlock(8, 00, 17, 00), new TimeBlock(13, 00, 14, 30)), NONE(
			TimeBlock.NONE, TimeBlock.NONE), AM(new TimeBlock(8, 00, 12, 0),
			TimeBlock.NONE), PM(new TimeBlock(13, 00, 17, 00), TimeBlock.NONE);

	private TimeBlock timePeriod;
	private TimeBlock lunchTime;

	private DailySchedule(TimeBlock timePeriod, TimeBlock lunchTime) {
		this.timePeriod = timePeriod;
		this.lunchTime = lunchTime;
	}

	public TimeBlock getTimePeriod() {
		return timePeriod;
	}

	public TimeBlock getLunchTime() {
		return lunchTime;
	}
}// end DailySchedule enum type

