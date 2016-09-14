package lovera.kualpostinvou.views.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import lovera.kualpostinvou.views.utils.HelperGoogleApi;

public class LocalizacaoService extends Service{

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
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
            final HelperGoogleApi helper = HelperGoogleApi.getHelperGoogleUnicaInstancia();
            while(!helper.temLastLocation() && count < 10){
                try{
                    Thread.sleep(1500);
                }
                catch (InterruptedException e){}
                count++;
            }
            if(helper.temLastLocation()){
                helper.passarLocalizacao();
            }
            else{
            }
            stopSelf(this.startId);
        }
    }
}