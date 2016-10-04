package lovera.kualpostinvou.views.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;

import lovera.kualpostinvou.Aplicacao;
import lovera.kualpostinvou.views.receivers.ReceiversNames;
import lovera.kualpostinvou.views.redes_sociais.google.HelperGeolocalizacao;

public class LocalizacaoService extends IntentService{

    public LocalizacaoService(){
        super("LocalizacaoService");
    }

    public LocalizacaoService(String name){
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        ResultReceiver resultReceiver = intent.getParcelableExtra(ReceiversNames.GPSLOCALIZACAO);

        int count = 0;
        final HelperGeolocalizacao helper = Aplicacao.getGoogleCoisas().getHelperGps();
        while(!helper.temLastLocation() && count < 10){
            try{
                Thread.sleep(1500);
            }
            catch (InterruptedException e){}
            count++;
        }
        if(helper.temLastLocation()){
            resultReceiver.send(ServicesNames.GPS_SERVICE, new Bundle());
        }
        else{
            //TODO tratativa localização não encontrada
        }
        stopSelf();
    }
}
