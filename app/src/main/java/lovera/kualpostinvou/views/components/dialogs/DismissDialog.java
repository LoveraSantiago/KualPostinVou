package lovera.kualpostinvou.views.components.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.view.MotionEvent;

public class DismissDialog extends AlertDialog{

    public DismissDialog(Context context) {
        super(context);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        dismiss();
        return false;
    }
}
