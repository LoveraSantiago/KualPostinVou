package lovera.kualpostinvou.views.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.MotionEvent;
import android.widget.TextView;

public class DialogGpsCancelado extends AlertDialog{


    public DialogGpsCancelado(Context context) {
        super(context);
        setTitle("Localização Requerida");
        setMessage("Para realizar a busca por estabelecimentos é necessario autorizar o gps");
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        dismiss();
        return false;
    }
}
