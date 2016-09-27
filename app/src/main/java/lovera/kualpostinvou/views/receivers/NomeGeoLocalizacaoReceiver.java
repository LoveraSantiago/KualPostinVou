package lovera.kualpostinvou.views.receivers;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

@SuppressLint("ParcelCreator")
public class NomeGeoLocalizacaoReceiver extends ResultReceiver {

    private Receiver receiver;

    public NomeGeoLocalizacaoReceiver(Handler handler) {
        super(handler);
    }

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        if(this.receiver != null){
            this.receiver.onReceiveResult(resultCode, resultData);
        }
    }

    public interface Receiver{
        void onReceiveResult(int resultCode, Bundle resultData);
    }
}
