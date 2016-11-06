package lovera.kualpostinvou.views.fragments.fragestabelecimento;

import android.os.Bundle;

import lovera.kualpostinvou.modelos.Localizacao;
import lovera.kualpostinvou.views.receivers.ReceiverPai;
import lovera.kualpostinvou.views.services.ServicesNames;

class Receiver extends ReceiverPai{

    private final FragEstabelecimento fragment;

    public Receiver(FragEstabelecimento fragment) {
        super();
        this.fragment = fragment;
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        if(resultCode == ServicesNames.NOME_GEOLOCALIZACAO){
            Localizacao localizacao = (Localizacao) resultData.getSerializable("LOCALIZACAO");
            this.fragment.getController().setLocalizacao(localizacao);
        }
    }
}
