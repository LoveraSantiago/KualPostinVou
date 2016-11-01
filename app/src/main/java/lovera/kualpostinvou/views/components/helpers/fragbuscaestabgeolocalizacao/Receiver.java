package lovera.kualpostinvou.views.components.helpers.fragbuscaestabgeolocalizacao;

import android.os.Bundle;
import android.os.Handler;

import lovera.kualpostinvou.views.fragments.FragBuscaEstabGeoLocalizacao2;
import lovera.kualpostinvou.views.receivers.CommonsReceiver;
import lovera.kualpostinvou.views.services.ServicesNames;

public class Receiver implements CommonsReceiver.Receiver{

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
                    consumirEstabelecimentos();
                }
                else{
                    this.fragment.getDialogs().showDialogGpsLocalizacaoFalha();
                }
            }
        }

    }
}
