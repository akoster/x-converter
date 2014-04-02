package xcon.example.webcrawler;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A web crawler with a Worker pool
 * 
 * @author Adriaan
 */
public class WebCrawler implements Manager {

	private Set<Worker> workers = new HashSet<Worker>();
	private List<String> toCrawl = new ArrayList<String>();
	private Set<String> crawled = new HashSet<String>();
	private Set<String> hosts = new HashSet<String>();
	private Set<String> results = new HashSet<String>();
	private int maxResults;

	public WebCrawler(String url, int numberOfWorkers, int maxResults) {
		this.maxResults = maxResults;
		toCrawl.add(url);
		createWorkers(numberOfWorkers);
	}

	public void createWorkers(int numberOfWorkers) {
		for (int i = 0; i < numberOfWorkers; i++) {
			workers.add(new Worker(this));
		}
	}

	private void stopWorkers() {
		for (Worker worker : workers) {
			worker.terminate();
		}
	}

	public synchronized Job getNewJob() {
		while (toCrawl.size() == 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				// ignore
			}
		}
		return new EmailAddressCrawlJob().setDescription(toCrawl.remove(0));
	}

	public synchronized void jobCompleted(Job job) {
		// System.out.println("crawled: " + job.getDescription());
		crawled.add(job.getDescription());
		String host = getHost(job.getDescription());
		boolean knownHost = hosts.contains(host);
		if (!knownHost) {
			System.out.println("host: " + host);
			hosts.add(host);
		}
		for (String url : job.getNewDescriptions()) {
			if (!crawled.contains(url)) {
				if (knownHost) {
					toCrawl.add(toCrawl.size() - 1, url);
				} else {
					toCrawl.add(url);
				}
			}
		}
		for (String result : job.getResults()) {
			if (results.add(result)) {
				System.out.println("result: " + result);
			}
		}
		notifyAll();
		if (results.size() >= maxResults) {
			stopWorkers();
			System.out.println("Crawled hosts:");
			for (String crawledHost : hosts) {
				System.out.println(crawledHost);
			}
			Set<String> uncrawledHosts = new HashSet<String>();
			for (String toCrawlUrl : toCrawl) {
				uncrawledHosts.add(getHost(toCrawlUrl));
			}
			System.out.println("Uncrawled hosts:");
			for (String unCrawledHost : uncrawledHosts) {
				System.out.println(unCrawledHost);
			}
		}
		if (crawled.size() % 10 == 0) {
			System.out.println("crawled=" + crawled.size() + " toCrawl="
					+ toCrawl.size() + " results=" + results.size() + " hosts="
					+ hosts.size() + " lastHost=" + host);
		}
	}

	public String getHost(String host) {
		int hostStart = host.indexOf("://") + 3;
		if (hostStart > 0) {
			int hostEnd = host.indexOf("/", hostStart);
			if (hostEnd < 0) {
				hostEnd = host.length();
			}
			host = host.substring(hostStart, hostEnd);
		}
		return host;
	}

	public static void main(String[] args) throws MalformedURLException {
		new WebCrawler("http://www.nu.nl/", 5, 20);
	}
}
