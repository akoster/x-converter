package xcon.example.webcrawler;

/**
 * A Worker proactively gets a Job, executes it and notifies its manager that
 * the Job is completed.
 * 
 * @author Adriaan
 */
public class Worker extends Thread {

	private final Manager manager;
	private Job job = null;
	private boolean isWorking;

	public Worker(Manager manager) {
		this.manager = manager;
		isWorking = true;
		start();
	}

	@Override
	public void run() {
		System.out.println("Worker " + Thread.currentThread().getId()
				+ " starting ");
		while (isWorking) {
			job = manager.getNewJob();
			job.execute();
			manager.jobCompleted(job);
		}
	}

	public void terminate() {
		isWorking = false;
	}
}
