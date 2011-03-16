package xcon.pilot.callback;

import java.util.concurrent.TimeUnit;

public class Trigger implements Runnable {

	public interface Target {
		public void fire(Trigger trigger);
	}

	private String id;
	private long seconds;
	private Target target;

	public Trigger(String id, long seconds, Target target) {
		this.id = id;
		this.seconds = seconds;
		this.target = target;
		Thread triggerThread = new Thread(this);
		triggerThread.start();
		System.out.println(this + " fires in " + seconds + " seconds");
	}

	@Override
	public void run() {

		try {
			TimeUnit.SECONDS.sleep(seconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		target.fire(this);
	}

	@Override
	public String toString() {
		return "Trigger " + id;
	}
}
