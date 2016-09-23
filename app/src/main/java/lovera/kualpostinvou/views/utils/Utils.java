package lovera.kualpostinvou.views.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    private static DateFormat dateFormat;

    static{
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }

    public static String dateToString(Date date){
        return dateFormat.format(date);
    }

}
