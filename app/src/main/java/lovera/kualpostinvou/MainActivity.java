package lovera.kualpostinvou;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import lovera.kualpostinvou.views.PrincipalActivity;
import lovera.kualpostinvou.views.receivers.CommonsReceiver;
import lovera.kualpostinvou.views.receivers.ReceiversNames;
import lovera.kualpostinvou.views.services.DelayService;
import lovera.kualpostinvou.views.services.LocalizacaoService;
import lovera.kualpostinvou.views.services.ServicesNames;

public class MainActivity extends AppCompatActivity implements CommonsReceiver.Receiver{

    private RelativeLayout layout;
    private CommonsReceiver receiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inicializarLayout();
        inicializarReceiver();
    }

    private void inicializarLayout(){
        this.layout = (RelativeLayout) findViewById(R.id.layoutPrincipal);
    }

    private void inicializarReceiver(){
        this.receiver = new CommonsReceiver(new Handler());
        this.receiver.setReceiver(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = new Intent(this, DelayService.class);
        intent.putExtra(ReceiversNames.DELAY, this.receiver);
        startService(intent);
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        if(resultCode == ServicesNames.DELAY){
            trocaDeTela();
        }
    }


    public void trocaDeTela(){
        Intent intent = new Intent(MainActivity.this, PrincipalActivity.class);
        startActivity(intent);
        finish();
    }
}
