package lovera.kualpostinvou.views.components;

import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

public class SeekBarChangeListenerImpl implements SeekBar.OnSeekBarChangeListener {

    private TextView lblSeekBar;

    public SeekBarChangeListenerImpl(TextView lblSeekBar) {
        this.lblSeekBar = lblSeekBar;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        this.lblSeekBar.setText(String.valueOf(1 + progress));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {}

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {}
}
