package yanovski.billsmanager.util;

import org.joda.time.ReadableInstant;
import org.joda.time.format.DateTimeFormat;

import yanovski.billsmanager.BillsManagerApplication;

/**
 * Created by Samuil on 1/19/2015.
 */
public class DateUtils {
    public static String FORMAT_SHORT_DATE = "d/M";

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
