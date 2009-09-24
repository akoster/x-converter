package xcon.webcrawler;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * <pre>
 * lees een inhoud van een gegeven url mbv java 
 * 1 - geef een url via de cmd (
 * arg meegeven ) 
 * 2 - valideer of het een goede valide url is 
 *     - bij verkeerder validatie: foutmelding geven en stoppen
 * 3 - gebruik de url om verbinding te maken 
 * 4 - content ophalen
 * 5 - uitprinten op de command lijn
 */

public class UrlLoader {

	private URL url;

	public UrlLoader(String url) throws MalformedURLException {

		this.url = new URL(url);

	}

	public static void main(String args[]) {

		if (args != null && args.length > 0) {

			try {
				UrlLoader loader = new UrlLoader(args[0]);
				loader.load();
			} catch (MalformedURLException e) {
				System.err.println("verkeerde URL:  " + e.getMessage());
			} catch (IOException e) {

				System.err.println("kon geen verbinding maken met URL");
			}

		} else {
			System.out.println("geef een URL");
		}

	}

	private void load() throws IOException {

		URLConnection connection = url.openConnection();
		InputStream input = connection.getInputStream();
		byte[] buffer = new byte[1000];
		int num = input.read(buffer);
		StringBuilder builder = new StringBuilder();
		builder.append(new String(buffer, 0, num));
		while (num != -1) {
			num = input.read(buffer);
			if (num != -1) {
				builder.append(new String(buffer, 0, num));
			}
		}
		System.out.println(builder.toString());
	}

}
