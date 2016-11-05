package lovera.kualpostinvou.views.receivers;

import android.os.Handler;

public abstract class ReceiverPai implements CommonsReceiver.Receiver{

    private CommonsReceiver receiver;

    public ReceiverPai() {
        this.receiver = new CommonsReceiver(new Handler());
        this.receiver.setReceiver(this);
    }

    public CommonsReceiver getCommonsReceiver() {
        return receiver;
    }
}
