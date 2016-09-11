package lovera.kualpostinvou;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import lovera.kualpostinvou.views.TempActivity;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout layout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inicializarLayout();
    }

    private void inicializarLayout(){
        this.layout = (RelativeLayout) findViewById(R.id.layoutPrincipal);
        this.layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent intent = new Intent(MainActivity.this, TempActivity.class);
                startActivity(intent);
                finish();
                return false;
            }
        });
    }
}
