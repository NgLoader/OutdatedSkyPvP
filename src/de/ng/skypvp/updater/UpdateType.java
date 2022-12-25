package de.ng.skypvp.updater;

public enum UpdateType {
	
	TICK(49),
	FASTEST(125),
	FASTER(250),
	FAST(500),
	SEC(1000),
	SEC2(2000),
	SEC4(4000),
	SEC10(10000),
	SEC30(30000),
	MIN01(60000),
	MIN02(120000),
	MIN04(240000),
	MIN08(480000),
	MIN15(960000),
	MIN30(1800000),
	HOUR(3600000);
	
	private long time;
	private long last;
	private long timeSpent;
	private long timeCount;
	
	private UpdateType(long time) {
		this.time = time;
		this.last = System.currentTimeMillis();
	}
	
	public boolean isElapsed() {
		if(System.currentTimeMillis() - this.last > this.time) {
			this.last = System.currentTimeMillis();
			return true;
		}
		return false;
	}

	public void startTime() {
		this.timeCount = System.currentTimeMillis();
	}
	
	public void stopTime() {
		this.timeSpent = System.currentTimeMillis() - this.timeCount;
	}
	
	public void printAndResetTime() {
		System.out.println(name() + " in a second: " + this.timeSpent);
		this.timeSpent = 0;
	}
}