package xcon.pilot.geocoding;

import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import net.sf.json.JSONObject;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

public class GGcoder {

	private static final String URL = "http://maps.google.com/maps/geo?output=json";
	private static final String DEFAULT_KEY = "ABQIAAAAXj-34qk56QPPCsoDEgWskhRB1hTz7k1Z3QWxjWsbw06syXuxKxQckD1pq75EEOyO6cJl9lgmLDQOPw";

	public static GAddress geocode(String address, String key) throws Exception {
		URL url = new URL(URL + "&q=" + URLEncoder.encode(address, "UTF-8")
				+ "&key=" + key + "&hl=nl");
		URLConnection conn = url.openConnection();
		ByteArrayOutputStream output = new ByteArrayOutputStream(1024);
		IOUtils.copy(conn.getInputStream(), output);
		output.close();

		GAddress gaddr = new GAddress();
		System.out.println("output=" + output.toString());
		JSONObject json = JSONObject.fromObject(output.toString());
		JSONObject placemark = (JSONObject) query(json, "Placemark[0]");

		final String commonId = "AddressDetails.Country.AdministrativeArea";

		gaddr.setFullAddress(query(placemark, "address").toString());
		gaddr
				.setZipCode(query(
						placemark,
						commonId
								+ ".SubAdministrativeArea.Locality.PostalCode.PostalCodeNumber")
						.toString());
		gaddr
				.setAddress(query(
						placemark,
						commonId
								+ ".SubAdministrativeArea.Locality.Thoroughfare.ThoroughfareName")
						.toString());
		gaddr.setCity(query(placemark,
				commonId + ".SubAdministrativeArea.SubAdministrativeAreaName")
				.toString());
		gaddr.setState(query(placemark, commonId + ".AdministrativeAreaName")
				.toString());
		gaddr.setLat(Double
				.parseDouble(query(placemark, "Point.coordinates[1]")
						.toString()));
		gaddr.setLng(Double
				.parseDouble(query(placemark, "Point.coordinates[0]")
						.toString()));
		return gaddr;
	}

	public static GAddress geocode(String address) throws Exception {
		return geocode(address, DEFAULT_KEY);
	}

	/* allow query for json nested objects, ie. Placemark[0].address */
	private static Object query(JSONObject jo, String query) {
		String[] keys = query.split("\\.");
		Object r = queryHelper(jo, keys[0]);
		for (int i = 1; i < keys.length; i++) {
			r = queryHelper(JSONObject.fromObject(r), keys[i]);
		}
		return r;
		// return "";
	}

	/* help in query array objects: Placemark[0] */
	private static Object queryHelper(JSONObject jo, String query) {
		int openIndex = query.indexOf('[');
		int endIndex = query.indexOf(']');
		if (openIndex > 0) {
			String key = query.substring(0, openIndex);
			int index = Integer.parseInt(query.substring(openIndex + 1,
					endIndex));
			return jo.getJSONArray(key).get(index);
		}
		System.out.println("query=" + query);
		return jo.get(query);
	}

	public static void main(String[] args) throws Exception {
		System.out.println(GGcoder
				.geocode("1600 Amphitheatre Parkway, Mountain View, CA, USA"));
	}

	/* Class to hold geocode result */
	public static class GAddress {

		public String address;
		public String fullAddress;
		public String zipCode;
		public String city;
		public String state;
		public double lat;
		public double lng;

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public String getFullAddress() {
			return fullAddress;
		}

		public void setFullAddress(String fullAddress) {
			this.fullAddress = fullAddress;
		}

		public String getZipCode() {
			return zipCode;
		}

		public void setZipCode(String zipCode) {
			this.zipCode = zipCode;
		}

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public String getState() {
			return state;
		}

		public void setState(String state) {
			this.state = state;
		}

		public double getLat() {
			return lat;
		}

		public void setLat(double lat) {
			this.lat = lat;
		}

		public double getLng() {
			return lng;
		}

		public void setLng(double lng) {
			this.lng = lng;
		}

		public String toString() {
			return ToStringBuilder.reflectionToString(this);
		}
	}
}
