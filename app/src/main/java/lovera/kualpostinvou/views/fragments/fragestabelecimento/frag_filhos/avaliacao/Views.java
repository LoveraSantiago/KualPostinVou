package lovera.kualpostinvou.views.fragments.fragestabelecimento.frag_filhos.avaliacao;

import android.view.View;
import android.widget.Button;

import lovera.kualpostinvou.R;
import lovera.kualpostinvou.views.components.dialogs.AvTempoDialog;

class Views {

    private FragFilho_Avaliacao fragment;

    private final Dialogs dialogs;
    private final AvTempoDialog avTempoDialog;
    private final AvTempoComponent tempoComponent;

    public Views(FragFilho_Avaliacao fragment) {
        this.fragment = fragment;

        this.dialogs = new Dialogs(fragment);
        this.tempoComponent = new AvTempoComponent(fragment.getActivity());
        this.avTempoDialog = new AvTempoDialog(fragment);

        inicializarComponentes();
    }

    private void inicializarComponentes(){
        Button btnRegistrarTempo = (Button) this.fragment.getActivity().findViewById(R.id.f8_btnRegistrarAtend);
        btnRegistrarTempo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.getController().showDialogCadastrarTempoDeAtendimento();
            }
        });
    }

    public AvTempoDialog getAvTempoDialog() {
        return avTempoDialog;
    }

    public AvTempoComponent getTempoComponent() {
        return tempoComponent;
    }

    public Dialogs getDialogs() {
        return dialogs;
    }
}
