package xcon.webcrawler;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class WebCrawler implements Runnable {

	private List<Job> jobs = new ArrayList<Job>();
	private Set<Worker> workers = new HashSet<Worker>();

	public WebCrawler(String url, int numberOfWorkers) {

		jobs.add(new Job(url));

		for (int i = 0; i < numberOfWorkers; i++) {
			Worker worker = new Worker(this);
			workers.add(worker);
		}
	}

	@Override
	public void run() {

		for (Worker worker : workers) {
			new Thread(worker).start();
		}

		while (true) {
			manageWorkers();
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private synchronized void manageWorkers() {

		int idleCount = 0;
		int busyCount = 0;
		int doneCount = 0;
		for (Worker worker : workers) {

			switch (worker.getState()) {
			case IDLE:
				idleCount++;
				break;
			case BUSY:
				busyCount++;
				break;
			case DONE:
				doneCount++;
				Job job = worker.reset();
				for (String url : job.getUrls()) {
					jobs.add(new Job(url));
				}
				notifyAll();
				break;
			}
		}
		System.out.println("idle=" + idleCount + " busy=" + busyCount
				+ " done=" + doneCount + " jobs " + jobs.size());
	}

	public synchronized Job getJob() {

		while (jobs.size() == 0) {

			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return jobs.remove(0);
	}

	public static void main(String[] args) throws MalformedURLException {
		WebCrawler crawler = new WebCrawler("http://www.nu.nl", 5);
		crawler.run();
	}
}
