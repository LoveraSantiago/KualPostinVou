package lovera.kualpostinvou.views.components.helpers;

import android.view.View;
import android.widget.Button;

import lovera.kualpostinvou.R;
import lovera.kualpostinvou.views.components.dialogs.AvAtendPermissoesDialog;
import lovera.kualpostinvou.views.components.dialogs.DismissDialog;
import lovera.kualpostinvou.views.fragments.fragestabelecimento.frag_filhos.FragEstabFilho_Avaliacao;

import static lovera.kualpostinvou.views.utils.FactoryViews.factoryDismissDialog;

public class FragEstabFilhoAvComponents {
    
    private FragEstabFilho_Avaliacao fragment;

    public FragEstabFilhoAvComponents(FragEstabFilho_Avaliacao fragment) {
        this.fragment = fragment;
        inicializarComponentes();
    }

    private void inicializarComponentes(){
        Button btnRegistrarTempo = (Button) this.fragment.getActivity().findViewById(R.id.f8_btnRegistrarAtend);
        btnRegistrarTempo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.showDialogCadastrarTempoDeAtendimento();
            }
        });
    }

    public void showDialogGpsCancelado(){
        DismissDialog dialog = factoryDismissDialog(this.fragment.getActivity(), "Localização Requerida",
                "Para realizar a avaliação de tempo do estabelecimento é necessario autorizar o gps");
        dialog.show();
    }

    public void showDialogAguardeLogin(){
        DismissDialog dialog = factoryDismissDialog(this.fragment.getActivity(), "Login em Andamento",
                "Aguarde um instante, login em andamento");
        dialog.show();
    }

    public void showDialogDistanteEstabelecimento(){
        DismissDialog dialog = factoryDismissDialog(this.fragment.getActivity(),"Distante do Estabelecimento",
                "Só é permitido registrar avaliação sobre um estabelecimento estando proximo dele.");
        dialog.show();
    }

    public void showDialogTempoZerado(){
        DismissDialog dialog = factoryDismissDialog(this.fragment.getActivity(),"Tempo inválido",
                "O registro de tempo deve ser diferente de zero.");
        dialog.show();
    }

    public void showDialogPermissoes(boolean temToken, boolean temLocalizacao){
        AvAtendPermissoesDialog dialogAtend = new AvAtendPermissoesDialog(this.fragment);
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
    }
}
