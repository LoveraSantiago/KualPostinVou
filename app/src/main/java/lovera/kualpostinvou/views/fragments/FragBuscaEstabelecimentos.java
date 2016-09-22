package lovera.kualpostinvou.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.Serializable;
import java.util.List;

import lovera.kualpostinvou.R;
import lovera.kualpostinvou.conexao.ConexaoMetaModelo;
import lovera.kualpostinvou.conexao.contratos.MsgFromConexaoSaude;
import lovera.kualpostinvou.modelos.Especialidade;
import lovera.kualpostinvou.modelos.Estabelecimento;
import lovera.kualpostinvou.modelos.Profissional;
import lovera.kualpostinvou.modelos.Servicos;
import lovera.kualpostinvou.modelos.constantes.Especialidades;
import lovera.kualpostinvou.views.ListaEstabelecimentosActivity;

public class FragBuscaEstabelecimentos extends FragmentMenu implements MsgFromConexaoSaude {

    public static String TITULO_FRAGMENT = "Estabelecimentos";
    public static int ID_FRAGMENT = 1;
    public static int ICONE = R.drawable.icn1;

    private Spinner spinner;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_buscaestabelecimentos, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        inicializarSpinner();
    }

    private void inicializarSpinner(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, Especialidades.getTextos());
        this.spinner = (Spinner) getView().findViewById(R.id.f1_spinner);
        this.spinner.setAdapter(adapter);
    }

    public void consumirEstabelecimentos() {
        String municipio = getStringFromIptText(R.id.f1_edtMunicipio);
        String uf = getStringFromIptText(R.id.f1_edtUf);
        int paginas = Integer.parseInt(getStringFromIptText(R.id.f1_edtPaginas));
        int qtdd = Integer.parseInt(getStringFromIptText(R.id.f1_edtQtd));
        String especialidade = this.spinner.getSelectedItem().toString();

        ConexaoMetaModelo conexaoMetaModelo = new ConexaoMetaModelo(this);
        conexaoMetaModelo.getEstabelecimentos(municipio, uf, null, especialidade, paginas, qtdd);
    }

    public String getStringFromIptText(int id) {
        EditText editText = (EditText) getView().findViewById(id);
        return editText.getText().toString();
    }

    @Override
    public void passarListaDeEstabelecimentos(List<Estabelecimento> listaDeEstabelecimentos) {
        Intent intent = new Intent(getActivity(), ListaEstabelecimentosActivity.class);
        intent.putExtra("LISTAESTABELECIMENTOS", (Serializable) listaDeEstabelecimentos);
        startActivity(intent);
    }

    @Override
    public void passarEstabelecimento(Estabelecimento estabelecimento) {

    }

    @Override
    public void passarListaDeEspecialidades(List<Especialidade> especialidades) {

    }

    @Override
    public void passarListaDeProfissionais(List<Profissional> profissionais) {

    }

    @Override
    public void passarListaDeServicos(List<Servicos> servicos) {

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
