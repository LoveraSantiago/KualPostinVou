package lovera.kualpostinvou.views.utils;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils_View {

    public static String dateToString(Date date, String formato){
        DateFormat dateFormat = new SimpleDateFormat(formato);
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

    public static void setImageToImgView(int img, int id, View layout){
        ImageView imgView = (ImageView) layout.findViewById(id);
        imgView.setImageResource(img);
    }
}
