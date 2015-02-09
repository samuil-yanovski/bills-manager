package yanovski.billsmanager.util;

import org.joda.time.DateTime;
import org.joda.time.ReadableInstant;
import org.joda.time.format.DateTimeFormat;

import java.util.Date;

import yanovski.billsmanager.BillsManagerApplication;

/**
 * Created by Samuil on 1/19/2015.
 */
public class DateUtils {
    public static String FORMAT_SHORT_DATE = "d/M";

    public static Date toDayDate(DateTime dt) {
        Date day = new Date(dt.getYear(), dt.getMonthOfYear() - 1, dt.getDayOfMonth() - 1);
        return day;
    }

    public static String formatShowDate(ReadableInstant instant) {
        return net.danlew.android.joda.DateUtils.formatDateTime(BillsManagerApplication.context,
            instant, net.danlew.android.joda.DateUtils.FORMAT_SHOW_DATE);
    }

    public static String format(ReadableInstant instant, String format) {
        return DateTimeFormat.forPattern(format)
            .print(instant);
    }

    public static String format(long instant, String format) {
        return DateTimeFormat.forPattern(format)
            .print(instant);
    }
}
