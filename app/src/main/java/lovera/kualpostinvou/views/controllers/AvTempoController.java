package lovera.kualpostinvou.views.controllers;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Calendar;

import lovera.kualpostinvou.R;
import lovera.kualpostinvou.modelos.HoraMinuto;
import lovera.kualpostinvou.views.utils.Utils_View;

public class AvTempoController {

    public static int NECESSARIO_LOGAR = 0;

    private final Activity activity;

    private TextView tvData;
    private TextView tvHora;
    private View progressoTemposAtendimento;
    private TextView mensagem;
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
        this.mensagem = (TextView) this.activity.findViewById(R.id.f8_mensagem);
        this.btnRegistrarAtendimento = (Button) this.activity.findViewById(R.id.f8_btnRegistrarAtend);
        this.btnEditarAtendimento = (Button) this.activity.findViewById(R.id.f8_btnEditarAtend);
    }

    private void estadoInicial(){
        this.mensagem.setVisibility(View.GONE);
        this.btnEditarAtendimento.setVisibility(View.GONE);
    }

    public void setTempo(HoraMinuto horaMinuto){
        this.progressoTemposAtendimento.setVisibility(View.GONE);
        if(horaMinuto == null){
            this.mensagem.setVisibility(View.VISIBLE);
        }
        this.tvHora.setText(horaMinuto.toString());
    }

    public void registroRealizado(){
        this.btnRegistrarAtendimento.setVisibility(View.GONE);
        this.btnEditarAtendimento.setVisibility(View.VISIBLE);
    }

    public void realizarAcao(int codAcao){
        if(codAcao == NECESSARIO_LOGAR){
            procedimentoPrecisaLogar();
        }
    }

    private void procedimentoPrecisaLogar(){
        this.mensagem.setText("É necessário logar p/ ver tempo de atendimento.");
        this.progressoTemposAtendimento.setVisibility(View.GONE);
    }
}
