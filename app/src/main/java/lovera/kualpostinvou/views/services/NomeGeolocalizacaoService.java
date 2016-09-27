package lovera.kualpostinvou.views.services;

import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.ResultReceiver;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import lovera.kualpostinvou.modelos.Estabelecimento;
import lovera.kualpostinvou.modelos.Localizacao;

public class NomeGeolocalizacaoService extends IntentService{

    public NomeGeolocalizacaoService(){
        super("");
    }

    public NomeGeolocalizacaoService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Estabelecimento estabelecimento = (Estabelecimento) intent.getSerializableExtra("ESTABELECIMENTO");
        ResultReceiver resultReceiver = intent.getParcelableExtra("RECEIVER");

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> listAddress = null;
        try {
            listAddress = geocoder.getFromLocationName(estabelecimento.getNomeFantasia(), 1,
                    estabelecimento.getLat() - 10, estabelecimento.getLongi() - 10,
                    estabelecimento.getLat() + 10, estabelecimento.getLongi() + 10);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        if(listAddress != null && listAddress.size() > 0){
            Address address = listAddress.get(0);
            Localizacao localizacao = new Localizacao();
            localizacao.setLatitude(address.getLatitude());
            localizacao.setLongitude(address.getLongitude());

            Bundle bundle = new Bundle();
            bundle.putSerializable("LOCALIZACAO", localizacao);

            resultReceiver.send(0, bundle);
        }
    }
}
