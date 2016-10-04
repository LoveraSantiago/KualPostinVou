package lovera.kualpostinvou.views.components.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import lovera.kualpostinvou.R;

public class TimePickerDialogo extends AlertDialog {

    private NumberPicker pickerHora;
    private NumberPicker pickerMin;

    private Button btnEnviar;
    private Button btnSair;

    public TimePickerDialogo(Activity activity) {
        super(activity);
        View mainView = inflarDialogXML(activity);
        inicializarComponentes(mainView);
    }

    private View inflarDialogXML(Activity activity){
        LayoutInflater inflater = activity.getLayoutInflater();
        View mainView = inflater.inflate(R.layout.dialog_avtempo, null);
        setView(mainView);
        return mainView;
    }

    private void inicializarComponentes(View mainView){
        this.pickerHora = (NumberPicker) mainView.findViewById(R.id.d2_pickerhora);
        this.pickerHora.setMinValue(0);
        this.pickerHora.setMaxValue(8);
        this.pickerHora.setWrapSelectorWheel(false);

        this.pickerMin = (NumberPicker) mainView.findViewById(R.id.d2_pickerminuto);
        this.pickerMin.setMinValue(0);
        this.pickerMin.setMaxValue(60);

        this.btnSair = (Button) mainView.findViewById(R.id.d2_btnsair);
        this.btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        this.btnEnviar = (Button) mainView.findViewById(R.id.d2_btnenviar);
        this.btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hora = pickerHora.getValue();
                int min = pickerMin.getValue();
            }
        });
    }
}
