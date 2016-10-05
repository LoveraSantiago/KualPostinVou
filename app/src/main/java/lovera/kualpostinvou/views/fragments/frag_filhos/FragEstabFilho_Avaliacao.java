package lovera.kualpostinvou.views.fragments.frag_filhos;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lovera.kualpostinvou.Aplicacao;
import lovera.kualpostinvou.R;
import lovera.kualpostinvou.modelos.Estabelecimento;
import lovera.kualpostinvou.views.PrincipalActivity;
import lovera.kualpostinvou.views.components.dialogs.AvAtendPermissoesDialog;
import lovera.kualpostinvou.views.components.dialogs.DismissDialog;
import lovera.kualpostinvou.views.components.dialogs.AvTempoDialog;
import lovera.kualpostinvou.views.contratos.MsgToFragFilhos;
import lovera.kualpostinvou.views.controllers.AvTempoController;
import lovera.kualpostinvou.views.fragments.FragmentFilho;
import lovera.kualpostinvou.views.receivers.CommonsReceiver;
import lovera.kualpostinvou.views.redes_sociais.google.HelperGeolocalizacao;
import lovera.kualpostinvou.views.services.ServicesNames;

public class FragEstabFilho_Avaliacao extends FragmentFilho implements CommonsReceiver.Receiver{

    public static String TITULO_FRAGMENT = "Filho Avaliacao";
    public static int ID_FRAGMENT = 2;
    public static int ICONE = R.drawable.ic_grade_black_24dp;

    private Estabelecimento estabelecimento;

    private AvTempoController tempoController;

    private HelperGeolocalizacao helperGPS;

    private MsgToFragFilhos msg;

    private CommonsReceiver receiver;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_estabelecimento_avaliacao, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.tempoController = new AvTempoController(getActivity());
        this.helperGPS = new HelperGeolocalizacao(getActivity());
        inicializarReceivers();
    }

    private void inicializarReceivers(){
        this.receiver = new CommonsReceiver(new Handler());
        this.receiver.setReceiver(this);
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

    public void showDialogCadastrarTempoDeAtendimento(){
        if(validarPermissoesCadastroAtendimento()){
            AvTempoDialog dialogTimer = new AvTempoDialog(this);
            dialogTimer.show();
        }
    }

    public void cadastrarTempoDeAtendimento(int hora, int minuto){

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
                AvAtendPermissoesDialog dialogAtend = new AvAtendPermissoesDialog(this);
                if(!temToken){
                    dialogAtend.configurarLinhaLogado(true, false);
                }
                else{
                    dialogAtend.configurarLinhaLogado(true, true);
                }
                if(!temLocalizacao){
                    dialogAtend.configurarLinhaGps(true, false);
                }
                else{
                    dialogAtend.configurarLinhaGps(true, true);
                }
                dialogAtend.show();
                return false;
            }
        }
    }

    public void ligarGps(){
        PrincipalActivity principalActivity = (PrincipalActivity) getActivity();
        principalActivity.abrirProgresso();
        principalActivity.setarTextoProgresso("Buscando localização.");

        this.helperGPS.popupLigarGps(this.receiver);
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

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        if(resultCode == ServicesNames.GPS_SERVICE && resultData != null){
            ((PrincipalActivity) getActivity()).fecharProgresso();
        }
        else{
            showDialogGpsCancelado();
        }
    }

    private void showDialogGpsCancelado(){
        DismissDialog dialog = new DismissDialog(getActivity());
        dialog.setTitle("Localização Requerida");
        dialog.setMessage("Para realizar a avaliação de tempo do estabelecimento é necessario autorizar o gps");
        dialog.show();
    }
}
