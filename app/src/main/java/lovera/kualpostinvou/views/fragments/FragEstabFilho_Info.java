package lovera.kualpostinvou.views.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lovera.kualpostinvou.R;
import lovera.kualpostinvou.modelos.Estabelecimento;

import static lovera.kualpostinvou.views.utils.Utils.setTextToLabel;

public class FragEstabFilho_Info extends FragmentFilho {

    private Estabelecimento estabelecimento;

    public static String TITULO_FRAGMENT = "Info";
    public static int ID_FRAGMENT = 1;
    public static int ICONE = R.drawable.ic_info_black_24dp;

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
