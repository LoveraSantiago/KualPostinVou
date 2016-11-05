package lovera.kualpostinvou.views.fragments.fragbuscaestabgeolocalizacao;

import android.os.Bundle;

import lovera.kualpostinvou.views.contratos.MsgToActivity;
import lovera.kualpostinvou.views.receivers.ReceiverPai;
import lovera.kualpostinvou.views.services.ServicesNames;

class Receiver extends ReceiverPai{

    private final FragBuscaEstabGeoLocalizacao2 fragment;

    public Receiver(FragBuscaEstabGeoLocalizacao2 fragment) {
        super();
        this.fragment = fragment;
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        if(resultCode == ServicesNames.GPS_SERVICE){
            if(resultData == null){
                this.fragment.getDialogs().showDialogGpsCancelado();
            }
            else{
                if(resultData.getBoolean("resultado")){
                    this.fragment.getConsumer().inicio2_consumirEstabelecimentos();
                }
                else{
                    ((MsgToActivity) fragment.getActivity()).fecharProgresso();
                    this.fragment.getDialogs().showDialogGpsLocalizacaoFalha();
                }
            }
        }
    }
}
