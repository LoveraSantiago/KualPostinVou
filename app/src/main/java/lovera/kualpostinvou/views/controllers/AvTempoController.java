package lovera.kualpostinvou.views.controllers;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;

import lovera.kualpostinvou.R;
import lovera.kualpostinvou.modelos.HoraMinuto;
import lovera.kualpostinvou.views.utils.Utils_View;

public class AvTempoController {

    private final Activity activity;
    private TextView tvData;
    private TextView tvHora;
    private View progressoTemposAtendimento;
    private View semRegistrosTempo;
    private Button btnRegistrarAtendimento;
    private Button btnEditarAtendimento;

    public AvTempoController(Activity activity) {
        this.activity = activity;
        inicializarComponentes();
        estadoInicial();
    }

    private void inicializarComponentes(){
        this.tvData = (TextView) this.activity.findViewById(R.id.f8_lbldia);
        this.tvData.setText(Utils_View.dateToString(Calendar.getInstance().getTime(), "dd/MM/yyyy"));

        this.tvHora = (TextView) this.activity.findViewById(R.id.f8_lblHora);
        this.progressoTemposAtendimento = this.activity.findViewById(R.id.f8_progressoTempoAtend);
        this.semRegistrosTempo = this.activity.findViewById(R.id.f8_semRegistrosAtend);
        this.btnRegistrarAtendimento = (Button) this.activity.findViewById(R.id.f8_btnRegistrarAtend);
        this.btnEditarAtendimento = (Button) this.activity.findViewById(R.id.f8_btnEditarAtend);
    }

    private void estadoInicial(){
        this.semRegistrosTempo.setVisibility(View.GONE);
        this.btnEditarAtendimento.setVisibility(View.GONE);
    }

    public void setTempo(HoraMinuto horaMinuto){
        this.progressoTemposAtendimento.setVisibility(View.GONE);
        if(horaMinuto == null){
            this.semRegistrosTempo.setVisibility(View.VISIBLE);
        }
        this.tvHora.setText(horaMinuto.toString());
    }

    public void registroRealizado(){
        this.btnRegistrarAtendimento.setVisibility(View.GONE);
        this.btnEditarAtendimento.setVisibility(View.VISIBLE);
    }
}
