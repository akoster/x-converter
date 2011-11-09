package xcon.pilot;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

public class Timezone {

	public static Calendar convertCalendar(final Calendar calendar,
			final TimeZone timeZone) {
		Calendar ret = new GregorianCalendar(timeZone);
		ret.setTimeInMillis(calendar.getTimeInMillis()
				+ timeZone.getOffset(calendar.getTimeInMillis())
				- TimeZone.getDefault().getOffset(calendar.getTimeInMillis()));
		ret.getTime();
		return ret;
	}

	public static void main(String[] args) {

		SimpleDateFormat df = new SimpleDateFormat();
		Calendar in = Calendar.getInstance();
		System.out.println(df.format(in.getTime()));

		TimeZone tz = new SimpleTimeZone(2 * 60 * 60 * 1000, "test");

		Calendar out = convertCalendar(in, tz);
		System.out.println(df.format(out.getTime()));
	}
}
