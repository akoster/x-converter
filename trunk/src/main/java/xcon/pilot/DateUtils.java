package xcon.pilot;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

	public static void main(String[] args) {

		Date date = new Date();

		System.out.println("Year="
				+ Integer.parseInt(new SimpleDateFormat("yyyy").format(date)));

		System.out.println("Month="
				+ Integer.parseInt(new SimpleDateFormat("M").format(date)));
	}
}
