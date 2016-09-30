package lovera.kualpostinvou.views.utils;

import android.view.View;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils_View {

    private static DateFormat dateFormat;

    static{
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }

    public static String dateToString(Date date){
        return dateFormat.format(date);
    }

    public static void setTextToLabel(String texto, int id, View layout){
        TextView lblCodigo = (TextView) layout.findViewById(id);
        lblCodigo.setText(texto);
    }

    public static void setTextToLabel(int texto, int id, View layout){
        setTextToLabel(String.valueOf(texto), id, layout);
    }

    public static void setTextToLabel(double texto, int id, View layout){
        setTextToLabel(String.valueOf(texto), id, layout);
    }
}
