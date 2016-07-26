package task.goeuro.goeurotest.helpers;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author developer
 */
public class DateHelper {

    public static String dateFormat(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    public static String timeFormat(Date date) {
        return new SimpleDateFormat("HH:mm:ss").format(date);
    }

}
