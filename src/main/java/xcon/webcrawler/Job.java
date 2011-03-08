package xcon.webcrawler;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class Job {

	private URL target;
	private Set<String> urls = new HashSet<String>();
	private String text;

	public Job(String url) {
		try {
			this.target = new URL(url);
		} catch (MalformedURLException e) {
			System.err.println("Bad url: " + url);
		}
	}

	public Set<String> getUrls() {
		return urls;
	}

	public String getText() {
		return text.toString().trim();
	}

	public void execute() {

		System.out.println("reading " + target);
		try {
			URLConnection connection = target.openConnection();
			InputStream input = connection.getInputStream();
			byte[] buffer = new byte[1000];
			int num = input.read(buffer);
			if (num < 0) {
				// System.err.println("Empty: " + target);
				return;
			}
			StringBuilder builder = new StringBuilder();
			builder.append(new String(buffer, 0, num));
			while (num != -1) {
				num = input.read(buffer);
				if (num != -1) {
					builder.append(new String(buffer, 0, num));
				}
			}

			text = builder.toString();

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
				StringTokenizer st = new StringTokenizer(remaining,
						"\t\n\r\">#");
				String strLink = st.nextToken();

				if (strLink.startsWith("javascript:")) {
					continue;
				}

				URL urlLink;
				try {
					urlLink = new URL(target, strLink);
					strLink = urlLink.toString();
				} catch (MalformedURLException e) {
					// System.err.println("Could not create url: " + target
					// + " + " + strLink);
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
				urls.add(urlLink.toString());
			}
		} catch (IOException e) {
			// System.err.println("Error " + e.getMessage());
		}
	}

	public static void main(String[] args) throws MalformedURLException {
		Job job = new Job("http://www.nu.nl");
		job.execute();
		System.out.println("urls=" + job.urls);
		System.out.println("text" + job.text);
	}
}
