package xcon.pilot.geocoding;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;

public class GeoCoder {

	private final static String KEY = "ABQIAAAAXj-34qk56QPPCsoDEgWskhRB1hTz7k1Z3QWxjWsbw06syXuxKxQckD1pq75EEOyO6cJl9lgmLDQOPw";

	public static void main(String[] argv) throws Exception {
				
		GeoCoder.getLocation("Binckhorstlaan 36, Den Haag");
	}

	public static void getLocation(String address) throws IOException {

		String urlString = createUrlString(address);
		System.out.println("calling " + urlString);
		BufferedReader in = new BufferedReader(new InputStreamReader(new URL(
				urlString).openStream()));

		String line;
		while ((line = in.readLine()) != null) {
			System.out.println(line);
		}

	}

	private static String createUrlString(String address)
			throws UnsupportedEncodingException {

		String output = "xml"; // xml, kml, csv, or (default) json;
		String encodedAddress = URLEncoder.encode(address, "UTF-8");
		String urlString = "http://maps.google.com/maps/geo?q="
				+ encodedAddress + "&key=" + KEY + "&sensor=false" + "&output="
				+ output;
		return urlString;
	}
}