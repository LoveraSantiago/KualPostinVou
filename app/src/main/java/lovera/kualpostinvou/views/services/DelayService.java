package lovera.kualpostinvou.views.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.ResultReceiver;

import lovera.kualpostinvou.views.receivers.ReceiversNames;

public class DelayService extends IntentService{

    public DelayService(){
        super("DelayService");
    }

    public DelayService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        ResultReceiver resultReceiver = intent.getParcelableExtra(ReceiversNames.DELAY);
        int tempo = intent.getIntExtra("tempo", 1000);
        try {
            Thread.sleep(tempo);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        resultReceiver.send(ServicesNames.DELAY, null);
    }
}
