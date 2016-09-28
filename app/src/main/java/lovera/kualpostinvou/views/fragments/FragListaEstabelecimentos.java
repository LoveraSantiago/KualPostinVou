package lovera.kualpostinvou.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import lovera.kualpostinvou.R;
import lovera.kualpostinvou.conexao.ConexaoSaude;
import lovera.kualpostinvou.modelos.Estabelecimento;
import lovera.kualpostinvou.views.adapters.EstabelecimentoAdapter;
import lovera.kualpostinvou.views.adapters.FragListaEstabAdapter;
import lovera.kualpostinvou.views.contratos.MsgToActivity;
import lovera.kualpostinvou.views.services.NomeGeolocalizacaoService;

public class FragListaEstabelecimentos extends FragmentMenu implements AdapterView.OnItemClickListener{

    //Campos relativos a FragmentMenu ACHO Q NÂO SERAO USADOS
    public static String TITULO_FRAGMENT = "Lista de Estabelecimentos";
    public static int ID_FRAGMENT = 4;
    public static int ICONE = R.drawable.icn2;

    private FragListaEstabAdapter adapterMsg;

    private List<Estabelecimento> listaEstabelecimentos;

    private MsgToActivity msgToActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.lista_estabelecimentos, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.msgToActivity = (MsgToActivity) getActivity();
        this.adapterMsg = new FragListaEstabAdapter(this);

        ListView lv = (ListView) getActivity().findViewById(R.id.listaEstabelecimentos);
        lv.setAdapter(new EstabelecimentoAdapter(getActivity(), this.listaEstabelecimentos));
        lv.setOnItemClickListener(this);
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        this.listaEstabelecimentos = (List<Estabelecimento>) args.get("LISTAESTABELECIMENTOS");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        this.msgToActivity.abrirProgresso();
        this.msgToActivity.setarTextoProgresso("Consultando estabelecimento.");

        Estabelecimento estabSelecionado = this.listaEstabelecimentos.get(position);
        ConexaoSaude conexao = new ConexaoSaude(this.adapterMsg);
        conexao.getEstabelelecimento(estabSelecionado.getCodUnidade());
    }

    public void receberEstabelecimento(Estabelecimento estabelecimento){
        this.msgToActivity.setarTextoProgresso("Estabelecimento localizado.");
        Bundle bundle = new Bundle();
        bundle.putSerializable("ESTABELECIMENTO", estabelecimento);
        FragEstabelecimento fragEstabelecimento = new FragEstabelecimento();
        fragEstabelecimento.setArguments(bundle);

        Intent intent = new Intent(getActivity(), NomeGeolocalizacaoService.class);
        intent.putExtra("ESTABELECIMENTO", estabelecimento);
        intent.putExtra("RECEIVER", fragEstabelecimento.getReceiver());
        getActivity().startService(intent);

        this.msgToActivity.setarFragment(fragEstabelecimento);
    }

    //Metodos sobrescritos herdados da classe pai FragmentMenu
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
