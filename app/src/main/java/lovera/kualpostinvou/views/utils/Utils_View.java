package lovera.kualpostinvou.views.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import lovera.kualpostinvou.R;

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

    public static LinearLayout gerarLinhaTabelaEspecialidade(Activity activity){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 0, (int)converterDpParaPixel(1, activity));

        LinearLayout linhaResult = new LinearLayout(activity);
        linhaResult.setLayoutParams(params);
        linhaResult.setOrientation(LinearLayout.HORIZONTAL);
        linhaResult.setGravity(Gravity.CENTER);
        linhaResult.setBackgroundColor(ContextCompat.getColor(activity, R.color.mAzulBranco));
        return linhaResult;
    }

    public static float converterDpParaPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }
}
