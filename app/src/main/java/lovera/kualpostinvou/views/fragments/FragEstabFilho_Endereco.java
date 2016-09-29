package lovera.kualpostinvou.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lovera.kualpostinvou.R;
import lovera.kualpostinvou.modelos.Estabelecimento;

import static lovera.kualpostinvou.views.utils.Utils.setTextToLabel;

public class FragEstabFilho_Endereco extends Fragment {

    private Estabelecimento estabelecimento;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_estabelecimento_endereco, container, false);
    }


    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        this.estabelecimento = (Estabelecimento) args.get("ESTABELECIMENTO");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setarCampos();
    }

    private void setarCampos(){
        setTextToLabel(this.estabelecimento.getLogradouro()                , R.id.lblLogradouro  , getView());
        setTextToLabel(this.estabelecimento.getNumero()                    , R.id.lblNumero      , getView());
        setTextToLabel(this.estabelecimento.getBairro()                    , R.id.lblBairro      , getView());
        setTextToLabel(this.estabelecimento.getCidade()                    , R.id.lblCidade      , getView());
        setTextToLabel(this.estabelecimento.getUf()                        , R.id.lblUf          , getView());
        setTextToLabel(this.estabelecimento.getCep()                       , R.id.lblCep         , getView());
        setTextToLabel(this.estabelecimento.getTelefone()                  , R.id.lblTelefone    , getView());
        setTextToLabel(this.estabelecimento.getLat()                       , R.id.lblLatitude    , getView());
        setTextToLabel(this.estabelecimento.getLongi()                     , R.id.lblLongitude   , getView());
        setTextToLabel(this.estabelecimento.getDistancia()                 , R.id.lblDistancia   , getView());
    }
}
