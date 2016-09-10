package lovera.kualpostinvou.views.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import lovera.kualpostinvou.views.utils.HelperGoogleApi;

public class LocalizacaoService extends Service{

    private HelperGoogleApi helperGoogleApi;

    @Override
    public void onCreate() {
        Log.i("service", "service iniciado");
        helperGoogleApi = HelperGoogleApi.helperStatic;
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("service", "service onStartCommand");
        Worker worker = new Worker(startId);
        worker.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    class Worker extends Thread{

        public int count = 0;
        public int startId;

        public Worker(int startId) {
            this.startId = startId;
        }

        @Override
        public void run() {
            while(!helperGoogleApi.temLastLocation() && count < 1000){
                Log.i("service", "tentativa " + count);
                try{
                    Thread.sleep(1500);
                }
                catch (InterruptedException e){}
                count++;
            }
            Log.i("service", "saindo do loop");
            if(helperGoogleApi.temLastLocation()){
                helperGoogleApi.passarLocalizacao();
                Log.i("service", "retornar localizacao sucesso");
            }
            else{
                Log.i("service", "falhar localizacao");
            }
            stopSelf(this.startId);
        }
    }
}
