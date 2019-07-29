/**
 * 
 */
package io.crs.mws.client.core.util;

import java.util.Date;
import java.util.logging.Logger;

import com.google.gwt.i18n.client.DateTimeFormat;

import io.crs.mws.client.core.i18n.CoreMessages;

/**
 * @author robi
 *
 */
public class DateUtils {
	private static Logger logger = Logger.getLogger(DateUtils.class.getName());

	public static final long SECOND_IN_MILISEC = 1000l;
	public static final long MINUTE_IN_MILISEC = 60000l;
	public static final long HOUR_IN_MILISEC = 3600000l;
	public static final long DAY_IN_MILISEC = 86400000l;
	public static final long WEEK_IN_MILISEC = 604800000l;
	public static final long MONTH_IN_MILISEC = 2592000000l;
	public static final long YEAR_IN_MILISEC = 31536000000l;

	public static Date mergeDateAndTime(Date date, Date time) {
		if (date == null)
			return time;
		if (time == null)
			return date;

		long daysInDate = (date.getTime() / DAY_IN_MILISEC) + 1;
		date.setTime(daysInDate * DAY_IN_MILISEC);

		long daysInTime = time.getTime() / DAY_IN_MILISEC;
		time.setTime(time.getTime() - (daysInTime * DAY_IN_MILISEC));

		Date dateTime = new Date(date.getTime() + time.getTime());
		return dateTime;
	}

	public static String elapsedText(Date startTime, CoreMessages i18n) {
		Date endTime = new Date();
		long durationMs = endTime.getTime() - startTime.getTime();
		if (durationMs < MINUTE_IN_MILISEC) {
			Long durationS = durationMs / SECOND_IN_MILISEC;
			if (durationS < 2)
				return i18n.com1SecondAgo(durationS.toString());
			if (durationS < 5)
				return i18n.com2SecondsAgo(durationS.toString());
			return i18n.com5SecondsAgo(durationS.toString());
		}

		if (durationMs < (5 * MINUTE_IN_MILISEC)) {
			Long durationS = durationMs / MINUTE_IN_MILISEC;
			if (durationS < 2)
				return i18n.com1MinuteAgo(durationS.toString());
			return i18n.com2MinutesAgo(durationS.toString());
		}

		if (durationMs < DAY_IN_MILISEC) {
			Long durationS = durationMs / HOUR_IN_MILISEC;
			if (durationS < 2)
				return i18n.com1HourAgo(durationS.toString());
			if (durationS < 5)
				return i18n.com2HoursAgo(durationS.toString());
			return i18n.com5HoursAgo(durationS.toString());
		}

		if (durationMs < WEEK_IN_MILISEC) {
			Long durationS = durationMs / DAY_IN_MILISEC;
			if (durationS < 2)
				return i18n.com1DayAgo(durationS.toString());
			if (durationS < 5)
				return i18n.com2DaysAgo(durationS.toString());
			return i18n.com5DaysAgo(durationS.toString());
		}

		if (durationMs < MONTH_IN_MILISEC) {
			Long durationS = durationMs / WEEK_IN_MILISEC;
			if (durationS < 2)
				return i18n.com1WeekAgo(durationS.toString());
			if (durationS < 5)
				return i18n.com2WeeksAgo(durationS.toString());
			return i18n.com5WeeksAgo(durationS.toString());
		}

		if (durationMs < YEAR_IN_MILISEC) {
			Long durationS = durationMs / MONTH_IN_MILISEC;
			if (durationS < 2)
				return i18n.com1MonthAgo(durationS.toString());
			if (durationS < 5)
				return i18n.com2MonthsAgo(durationS.toString());
			return i18n.com5MonthsAgo(durationS.toString());
		}

		Long durationS = durationMs / YEAR_IN_MILISEC;
		return i18n.com1YearAgo(durationS.toString());
	}

	public static String formatDateTime(Date date, String locale) {
		logger.info("DateUtils().formatDateTime()->locale=" + locale);
		String pattern;

		if (isTime(date))
			pattern = getDateTimeFormat(locale);
		else
			pattern = getDateFormat(locale);

		DateTimeFormat fmt = DateTimeFormat.getFormat(pattern);
		return fmt.format(date);

	}

	private static Boolean isTime(Date date) {
		DateTimeFormat timeformat = DateTimeFormat.getFormat("HH:mm");
		String timeText = timeformat.format(date);
		logger.info("DateUtils().isTime()->timeTe+xt=" + timeText);

		return (!timeText.equals("00:00"));
	}

	private static String getDateTimeFormat(String locale) {
		String dateformat = "dd.MM.yyyy HH:mm";
		if (locale.startsWith("hu"))
			dateformat = "yyyy.MM.dd HH:mm";

		if (locale.startsWith("uk"))
			dateformat = "mm.MM.yyyy HH:mm";
		return dateformat;
	}

	private static String getDateFormat(String locale) {
		String dateformat = "dd.MM.yyyy";
		if (locale.startsWith("hu"))
			dateformat = "yyyy.MM.dd";

		if (locale.startsWith("uk"))
			dateformat = "dd.MM.yyyy";
		return dateformat;
	}
}
