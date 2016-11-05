package lovera.kualpostinvou.views.fragments.fragestabelecimento;

import android.os.Bundle;

import com.google.android.gms.maps.model.LatLng;

import lovera.kualpostinvou.modelos.Localizacao;
import lovera.kualpostinvou.views.receivers.ReceiverPai;
import lovera.kualpostinvou.views.services.ServicesNames;

class Receiver extends ReceiverPai{

    public Receiver() {
        super();
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        if(resultCode == ServicesNames.NOME_GEOLOCALIZACAO){
            Localizacao localizacao = (Localizacao) resultData.getSerializable("LOCALIZACAO");
            this.latLng = new LatLng(localizacao.getLatitude(), localizacao.getLongitude());
            this.fragment.getViews().setarPosicaoToPanorama(this.latLng);
            this.msgToActivity.fecharProgresso();
        }
    }
}
