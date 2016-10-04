package lovera.kualpostinvou.views.fragments.frag_filhos;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lovera.kualpostinvou.Aplicacao;
import lovera.kualpostinvou.R;
import lovera.kualpostinvou.modelos.Estabelecimento;
import lovera.kualpostinvou.views.components.dialogs.AvAtendPermissoesDialog;
import lovera.kualpostinvou.views.components.dialogs.DismissDialog;
import lovera.kualpostinvou.views.components.dialogs.TimePickerDialogo;
import lovera.kualpostinvou.views.contratos.MsgToFragFilhos;
import lovera.kualpostinvou.views.controllers.AvTempoController;
import lovera.kualpostinvou.views.fragments.FragmentFilho;
import lovera.kualpostinvou.views.redes_sociais.google.HelperGeolocalizacao;

public class FragEstabFilho_Avaliacao extends FragmentFilho {

    public static String TITULO_FRAGMENT = "Filho Avaliacao";
    public static int ID_FRAGMENT = 2;
    public static int ICONE = R.drawable.ic_grade_black_24dp;

    private Estabelecimento estabelecimento;

    private AvTempoController tempoController;

    private HelperGeolocalizacao helperGPS;

    private MsgToFragFilhos msg;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_estabelecimento_avaliacao, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.tempoController = new AvTempoController(getActivity());
        this.helperGPS = new HelperGeolocalizacao(this.msg.getPaiFragment());
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        this.estabelecimento = (Estabelecimento) args.get("ESTABELECIMENTO");
    }

    public void cadastrarTempoDeAtendimento(){
        if(validarPermissoesCadastroAtendimento()){
            DialogFragment dialogTimer = new TimePickerDialogo();
            dialogTimer.show(getActivity().getFragmentManager(), "timePicker");
        }
    }

    private boolean validarPermissoesCadastroAtendimento(){
        boolean temToken = Aplicacao.getPessoaLogada().hasToken();
        boolean temLocalizacao = this.helperGPS.temLastLocation();

        if(temToken && temLocalizacao){
            return true;
        }
        else{
            if(Aplicacao.getPessoaLogada().isServiceTokenEmAndamento()){
                DismissDialog dialog = new DismissDialog(getActivity());
                dialog.setTitle("Login em Andamento");
                dialog.setMessage("Aguarde um instante, login em andamento");
                dialog.show();
                return false;
            }
            else{
                AvAtendPermissoesDialog dialog = new AvAtendPermissoesDialog(getActivity());
                if(!temToken){
                    dialog.configurarLinhaLogado(true, false);
                }
                else{
                    dialog.configurarLinhaLogado(true, true);
                }
                if(!temLocalizacao){
                    dialog.configurarLinhaGps(true, false);
                }
                else{
                    dialog.configurarLinhaGps(true, true);
                }
                dialog.show();
                return false;
            }
        }
    }

    public void setMsg(MsgToFragFilhos msg) {
        this.msg = msg;
    }

    @Override
    public int getFragmentId() {
        return ID_FRAGMENT;
    }

    @Override
    public String getFragmentTitulo() {
        return TITULO_FRAGMENT;
    }

    @Override
    public int getIcone() {
        return ICONE;
    }
}
