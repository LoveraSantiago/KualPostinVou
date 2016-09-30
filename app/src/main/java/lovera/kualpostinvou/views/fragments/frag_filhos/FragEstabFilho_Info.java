package lovera.kualpostinvou.views.fragments.frag_filhos;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import lovera.kualpostinvou.R;
import lovera.kualpostinvou.modelos.Estabelecimento;
import lovera.kualpostinvou.modelos.Profissional;
import lovera.kualpostinvou.views.contratos.MsgToFragFilhoInfo;
import lovera.kualpostinvou.views.fragments.FragmentFilho;

import static lovera.kualpostinvou.views.utils.Utils_View.setTextToLabel;

public class FragEstabFilho_Info extends FragmentFilho {

    private Estabelecimento estabelecimento;

    public static String TITULO_FRAGMENT = "Info";
    public static int ID_FRAGMENT = 1;
    public static int ICONE = R.drawable.ic_info_black_24dp;

    private View progressoProfissionais;
    private LinearLayout layoutProfissionais;

    private MsgToFragFilhoInfo msg;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_estabelecimento_info, container, false);
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        this.estabelecimento = (Estabelecimento) args.get("ESTABELECIMENTO");
    }

    @Override
    public void onStart() {
        super.onStart();
        inicializarProgressos();
        setarCampos();
    }

    private void setarCampos(){
        try{
            setTextToLabel(this.estabelecimento.getCodCnes()                   , R.id.lblCodCnes     , getView());
            setTextToLabel(this.estabelecimento.getCnpj()                      , R.id.lblCnpj        , getView());
            setTextToLabel(this.estabelecimento.getCodUnidade()                , R.id.lblCodigo      , getView());
            setTextToLabel(this.estabelecimento.getOrigemGeografica()          , R.id.lblOrigGeograf , getView());
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setListaDeProfissionais(List<Profissional> listaDeProfissionais){
        fecharProgressoProfissionais();
        this.layoutProfissionais = (LinearLayout) getActivity().findViewById(R.id.f6_layoutProfissionais);
        for(Profissional profissional : listaDeProfissionais){
            TextView textView = new TextView(getActivity());
            textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            textView.setPadding(5, 0, 0, 0);
            textView.setText(profissional.toString());
            this.layoutProfissionais.addView(textView);
        }
    }

    //Coisas relativas ao progresso de profissionais
    private void inicializarProgressos(){
        this.progressoProfissionais = getActivity().findViewById(R.id.f6_progressoProfissionais);
        if(this.msg.getListaDeProfissionais() != null){
            setListaDeProfissionais(this.msg.getListaDeProfissionais());
        }
    }

    private void fecharProgressoProfissionais(){
        this.progressoProfissionais.setVisibility(View.GONE);
    }

    public void setMsg(MsgToFragFilhoInfo msg) {
        this.msg = msg;
    }

    @Override
    public int getFragmentId() {
        return ID_FRAGMENT;
    }

    @Override
    public String getFragmentTitulo() {
        return TITULO_FRAGMENT;
    }

    @Override
    public int getIcone() {
        return ICONE;
    }
}