package xcon.example.webcrawler;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A Job which crawls HTTP or HTTPS URL's for email adresses, collecting new
 * URL's to crawl along the way.
 * 
 * @author Adriaan
 */
public class EmailAddressCrawlJob extends Job {

	@Override
	public void execute() {
		try {
			URL url = new URL(getDescription());
			if (url != null) {
				String text = readText(url);
				extractNewDescriptions(text, url);
				extractResults(text);
			}
		} catch (MalformedURLException e) {
			System.err.println("Bad url " + getDescription());
		}
	}

	private String readText(URL url) {
		URLConnection connection;
		try {
			connection = url.openConnection();
			InputStream input = connection.getInputStream();
			byte[] buffer = new byte[1000];
			int num = input.read(buffer);
			if (num > 0) {
				StringBuilder builder = new StringBuilder();
				builder.append(new String(buffer, 0, num));
				while (num != -1) {
					num = input.read(buffer);
					if (num != -1) {
						builder.append(new String(buffer, 0, num));
					}
				}
				return builder.toString();
			}
		} catch (IOException e) {
			//System.err.println("Could not read from " + url);
		}
		return "";
	}

	private void extractNewDescriptions(String text, URL url) {

		// URL extracting code from Sun example
		String lowerCaseContent = text.toLowerCase();
		int index = 0;
		while ((index = lowerCaseContent.indexOf("<a", index)) != -1) {

			if ((index = lowerCaseContent.indexOf("href", index)) == -1) {
				break;
			}

			if ((index = lowerCaseContent.indexOf("=", index)) == -1) {
				break;
			}

			index++;
			String remaining = text.substring(index);
			StringTokenizer st = new StringTokenizer(remaining, "\t\n\r\">#");
			String strLink = st.nextToken();

			if (strLink.startsWith("javascript:")) {
				continue;
			}

			URL urlLink;
			try {
				urlLink = new URL(url, strLink);
			} catch (MalformedURLException e) {
				// System.err.println("Could not create url: " + target
				// + " + " + urlLink.toString());
				continue;
			}
			// only look at http links
			String protocol = urlLink.getProtocol();
			if (protocol.compareTo("http") != 0
					&& protocol.compareTo("https") != 0) {
				// System.err.println("Ignoring: " + protocol
				// + " protocol in " + urlLink);
				continue;
			}
			addNewDescription(urlLink.toString());
		}
	}

	private void extractResults(String text) {
		Pattern p = Pattern
				.compile("([\\w\\-]([\\.\\w])+[\\w]+@([\\w\\-]+\\.)+[A-Za-z]{2,4})");
		Matcher m = p.matcher(text);
		while (m.find()) {
			addResult(m.group(1));
		}
	}

	// test code
	public static void main(String[] args) throws MalformedURLException {
		Job job = new EmailAddressCrawlJob().setDescription("http://www.nu.nl");
		job.execute();
		System.out.println("urls=" + job.getNewDescriptions());
		System.out.println("results=" + job.getResults());
	}
}
