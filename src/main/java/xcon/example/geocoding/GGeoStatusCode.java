package xcon.example.geocoding;

/**
 * A statuscode returned by the Google Maps API
 * 
 * @see http://code.google.com/intl/nl/apis/maps/documentation/reference.html
 *      #GGeoStatusCode
 */
public enum GGeoStatusCode {

	/**
	 * No errors occurred; the address was successfully parsed and its geocode
	 * was returned.
	 */
	G_GEO_SUCCESS(200),

	/**
	 * A directions request could not be successfully parsed. For example, the
	 * request may have been rejected if it contained more than the maximum
	 * number of waypoints allowed.
	 */
	G_GEO_BAD_REQUEST(400),

	/**
	 *A geocoding or directions request could not be successfully processed,
	 * yet the exact reason for the failure is unknown.
	 */
	G_GEO_SERVER_ERROR(500),

	/**
	 * An empty address was specified in the HTTP q parameter.
	 */
	G_GEO_MISSING_QUERY(601),

	/**
	 * No corresponding geographic location could be found for the specified
	 * address, possibly because the address is relatively new, or because it
	 * may be incorrect.
	 */
	G_GEO_UNKNOWN_ADDRESS(602),

	/**
	 * The geocode for the given address or the route for the given directions
	 * query cannot be returned due to legal or contractual reasons.
	 */
	G_GEO_UNAVAILABLE_ADDRESS(603),

	/**
	 * The given key is either invalid or does not match the domain for which it
	 * was given.
	 */
	G_GEO_BAD_KEY(610),

	/**
	 * The given key has gone over the requests limit in the 24 hour period or
	 * has submitted too many requests in too short a period of time. If you're
	 * sending multiple requests in parallel or in a tight loop, use a timer or
	 * pause in your code to make sure you don't send the requests too quickly.
	 */
	G_GEO_TOO_MANY_QUERIES(620);

	/**
	 * The return value for this GGeoStatusCode
	 */
	public int returnValue;

	private GGeoStatusCode(int returnValue) {
		this.returnValue = returnValue;
	}
}