package lovera.kualpostinvou.views.utils;

import android.app.Activity;

import lovera.kualpostinvou.views.components.dialogs.DismissDialog;

public class FactoryViews {

    public static DismissDialog factoryDismissDialog(Activity activity, String titulo, String msg){
        DismissDialog dialog = new DismissDialog(activity);
        dialog.setTitle(titulo);
        dialog.setMessage(msg);
        return dialog;
    }

}
