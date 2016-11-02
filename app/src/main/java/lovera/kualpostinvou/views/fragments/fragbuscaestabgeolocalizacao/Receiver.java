package lovera.kualpostinvou.views.fragments.fragbuscaestabgeolocalizacao;

import android.os.Bundle;
import android.os.Handler;

import lovera.kualpostinvou.views.contratos.MsgToActivity;
import lovera.kualpostinvou.views.receivers.CommonsReceiver;
import lovera.kualpostinvou.views.services.ServicesNames;

class Receiver implements CommonsReceiver.Receiver{

    private CommonsReceiver receiver;
    private final FragBuscaEstabGeoLocalizacao2 fragment;

    public Receiver(FragBuscaEstabGeoLocalizacao2 fragment) {
        this.receiver = new CommonsReceiver(new Handler());
        this.receiver.setReceiver(this);

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
                    this.fragment.getConsumer().consumirEstabelecimentos();
                }
                else{
                    ((MsgToActivity) fragment.getActivity()).fecharProgresso();
                    this.fragment.getDialogs().showDialogGpsLocalizacaoFalha();
                }
            }
        }
    }

    public CommonsReceiver getCommonsReceiver() {
        return receiver;
    }
}
