package lovera.kualpostinvou.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lovera.kualpostinvou.R;
import lovera.kualpostinvou.modelos.Estabelecimento;

import static lovera.kualpostinvou.views.utils.Utils_View.setTextToLabel;

public class FragEstabFilho_Endereco extends FragmentFilho {

    private Estabelecimento estabelecimento;

    public static String TITULO_FRAGMENT = "Enderecos";
    public static int ID_FRAGMENT = 3;
    public static int ICONE = R.drawable.ic_room_black_24dp;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.tab_estabelecimento_endereco, container, false);
        setarCampos();
        return inflate;
    }

    @Override
    public void onStart() {
        super.onStart();
        setarCampos();
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        this.estabelecimento = (Estabelecimento) args.get("ESTABELECIMENTO");
    }

    private void setarCampos(){
        try{
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
