package xcon.webcrawler;

public class Worker implements Runnable {

	public enum State {
		IDLE, BUSY, DONE;
	}

	private final WebCrawler crawler;
	private Job job = null;
	private State state = State.IDLE;

	public Worker(WebCrawler crawler) {
		this.crawler = crawler;
	}

	@Override
	public void run() {

		System.out.println("Worker " + Thread.currentThread().getId()
				+ " starting ");

		while (true) {
			job = crawler.getJob();
			state = State.BUSY;
			job.execute();
			state = State.DONE;
		}
	}

	public State getState() {
		return state;
	}

	public Job reset() {
		Job result = job;
		job = null;
		state = State.IDLE;
		return result;
	}
}
