package lovera.kualpostinvou.views.components.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import lovera.kualpostinvou.R;
import lovera.kualpostinvou.views.PrincipalActivity;
import lovera.kualpostinvou.views.fragments.FragRedesSociais;

public class AvAtendPermissoesDialog extends AlertDialog{

    private int imgOk = R.drawable.ic_check_black_24dp;
    private int imgNOk = R.drawable.ic_cancel_black_24dp;

    private ImageView imgLogado;
    private ImageView imgGps;

    private Button btnLogado;
    private Button btnGpsLigado;

    private View linhaLogado;
    private View linhaGpsLigado;

    public AvAtendPermissoesDialog(Activity activity) {
        super(activity);
        View mainView = inflarDialogXML(activity);
        inicializarComponentes(mainView, activity);
    }

    private View inflarDialogXML(Activity activity){
        LayoutInflater inflater = activity.getLayoutInflater();
        View mainView = inflater.inflate(R.layout.dialog_avtempo_permissoes, null);
        setView(mainView);

        return mainView;
    }

    private void inicializarComponentes(View mainView, final Activity activity){
        this.linhaLogado = mainView.findViewById(R.id.d1_linhaLogado);
        this.linhaGpsLigado = mainView.findViewById(R.id.d1_linhaGpsLigado);

        this.imgLogado = (ImageView) mainView.findViewById(R.id.d1_lblLogEstado);
        this.imgGps = (ImageView) mainView.findViewById(R.id.d1_lblGpsEstado);

        this.btnLogado = (Button) mainView.findViewById(R.id.d1_btnLogar);
        this.btnGpsLigado = (Button) mainView.findViewById(R.id.d1_btnLigarGps);

        this.btnLogado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((PrincipalActivity) activity).selectItem(FragRedesSociais.ID_FRAGMENT);
                dismiss();
            }
        });

        this.btnGpsLigado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();
            }
        });
    }

    public void configurarLinhaLogado(boolean linhaVisivel, boolean check){
        procedimentoComumConfiguracaoLinha(this.linhaLogado, this.imgLogado, this.btnLogado, linhaVisivel, check);
    }

    public void configurarLinhaGps(boolean linhaVisivel, boolean check){
        procedimentoComumConfiguracaoLinha(this.linhaGpsLigado, this.imgGps, this.btnGpsLigado, linhaVisivel, check);
    }

    private void procedimentoComumConfiguracaoLinha(View linha, ImageView img, Button btn, boolean linhaVisivel, boolean check){
        if(linhaVisivel){
            linha.setVisibility(View.VISIBLE);
        }
        else{
            linha.setVisibility(View.GONE);
            return;
        }

        if(check){
            img.setImageResource(this.imgOk);
            btn.setVisibility(View.GONE);
        }
        else{
            img.setImageResource(this.imgNOk);
            btn.setVisibility(View.VISIBLE);
        }
        this.imgLogado.invalidate();
    }
}
