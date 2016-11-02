package lovera.kualpostinvou.views.fragments.fragbuscaestabgeolocalizacao;

import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import lovera.kualpostinvou.R;
import lovera.kualpostinvou.views.components.SeekBarChangeListenerImpl;

class Views {

    private TextView lblSeekBar;

    public Views(FragBuscaEstabGeoLocalizacao2 fragment) {
        inicializarComponentes(fragment);
    }

    private void inicializarComponentes(FragBuscaEstabGeoLocalizacao2 fragment){
        View view = fragment.getView();

        this.lblSeekBar  = (TextView) view.findViewById(R.id.f2_lblseekbar);
        final SeekBar seekBar = (SeekBar) view.findViewById(R.id.f2_seekbar);
        seekBar.setOnSeekBarChangeListener(new SeekBarChangeListenerImpl(this.lblSeekBar));

        Button btnDecremento = (Button) view.findViewById(R.id.f2_menos);
        btnDecremento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int result = Integer.parseInt(lblSeekBar.getText().toString()) - 2;
                if(result >= 0){
                    seekBar.setProgress(result);
                }
            }
        });

        Button btnIncremento = (Button) view.findViewById(R.id.f2_mais);
        btnIncremento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int result = Integer.parseInt(lblSeekBar.getText().toString());
                if(result <= 50){
                    seekBar.setProgress(result);
                }
            }
        });
    }

    public TextView getLblSeekBar() {
        return lblSeekBar;
    }
}
