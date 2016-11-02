package lovera.kualpostinvou.views.fragments.fragbuscaestabgeolocalizacao;

import android.app.Activity;

import lovera.kualpostinvou.views.components.dialogs.DismissDialog;

import static lovera.kualpostinvou.views.utils.FactoryViews.factoryDismissDialog;

class Dialogs {

    private final Activity activity;

    public Dialogs(Activity activity) {
        this.activity = activity;
    }

    public void showDialogListaVazia(){
        DismissDialog dialog = factoryDismissDialog(this.activity,"Nenhum Resultado",
                "Não foi encontrado estabelecimentos. Tente aumentar o raio da busca.");
        dialog.show();
    }

    public void showDialogGpsCancelado(){
        DismissDialog dialog = factoryDismissDialog(this.activity,"Localização Requerida",
                "Para realizar a busca por estabelecimentos é necessario autorizar o gps.");
        dialog.show();
    }

    public void showDialogGpsLocalizacaoFalha(){
        DismissDialog dialog = factoryDismissDialog(this.activity,"Localização Não Encontrada",
                "Não foi possivel encontrar sua localização. Tente mais tarde por favor.");
        dialog.show();
    }
}
