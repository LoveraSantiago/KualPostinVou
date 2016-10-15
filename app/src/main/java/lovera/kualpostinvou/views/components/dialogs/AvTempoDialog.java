package lovera.kualpostinvou.views.components.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import lovera.kualpostinvou.R;
import lovera.kualpostinvou.modelos.HoraMinuto;
import lovera.kualpostinvou.views.fragments.frag_filhos.FragEstabFilho_Avaliacao;

public class AvTempoDialog extends AlertDialog {

    private boolean jaCadastrouTempo;

    private NumberPicker pickerHora;
    private NumberPicker pickerMin;

    private Button btnEnviar;
    private Button btnSair;

    private View progresso;
    private View body;

    private int hora;
    private int minuto;

    public AvTempoDialog(FragEstabFilho_Avaliacao fragment) {
        super(fragment.getActivity());
        View mainView = inflarDialogXML(fragment.getActivity());
        inicializarComponentes(fragment, mainView);
    }

    private View inflarDialogXML(Activity activity){
        LayoutInflater inflater = activity.getLayoutInflater();
        View mainView = inflater.inflate(R.layout.dialog_avtempo, null);
        setView(mainView);
        return mainView;
    }

    private void inicializarComponentes(final FragEstabFilho_Avaliacao fragment, View mainView){
        this.body = mainView.findViewById(R.id.d2_body);
        this.progresso = mainView.findViewById(R.id.d2_progresso);

        this.pickerHora = (NumberPicker) mainView.findViewById(R.id.d2_pickerhora);
        this.pickerHora.setMinValue(0);
        this.pickerHora.setMaxValue(8);
        this.pickerHora.setWrapSelectorWheel(false);

        this.pickerMin = (NumberPicker) mainView.findViewById(R.id.d2_pickerminuto);
        this.pickerMin.setMinValue(0);
        this.pickerMin.setMaxValue(59);

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
                if(progresso.getVisibility() == View.VISIBLE) return;
                hora = pickerHora.getValue();
                minuto = pickerMin.getValue();

                if(jaCadastrouTempo){
                    fragment.editarTempoAtend();
                }
                else{
                   fragment.cadastrarTempoAtend();
                }
            }
        });
    }

    public void show(boolean jaCadastrouTempo){
        this.jaCadastrouTempo = jaCadastrouTempo;
        acaoEstadoDaTelaTempoCadastrado();
        show();
    }

    private void acaoEstadoDaTelaTempoCadastrado(){
        if(this.jaCadastrouTempo){
            this.body.setVisibility(View.GONE);
            this.progresso.setVisibility(View.VISIBLE);
            this.btnEnviar.setText("Atualizar");
        }
        else{
            this.progresso.setVisibility(View.GONE);
            this.body.setVisibility(View.VISIBLE);
            this.btnEnviar.setText("Enviar");
        }
    }

    public void setTempoCadastrado(HoraMinuto horaMinuto){
        this.body.setVisibility(View.VISIBLE);
        this.progresso.setVisibility(View.GONE);
        this.pickerHora.setValue(horaMinuto.getHora());
        this.pickerMin.setValue(horaMinuto.getMinuto());
    }

    public int getMinutos() {
        return (this.hora * 60) + minuto;
    }
}
