package lovera.kualpostinvou.views.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.print.PrintJobInfo;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import lovera.kualpostinvou.R;
import lovera.kualpostinvou.conexao.ConexaoSaude;
import lovera.kualpostinvou.modelos.Estabelecimento;
import lovera.kualpostinvou.views.PrincipalActivity;
import lovera.kualpostinvou.views.adapters.ListEstabAdapter;
import lovera.kualpostinvou.views.adapters.FragListaEstabAdapter;
import lovera.kualpostinvou.views.contratos.MsgToActivity;
import lovera.kualpostinvou.views.receivers.ReceiversNames;
import lovera.kualpostinvou.views.services.NomeGeolocalizacaoService;

public class FragListaEstabelecimentos extends FragmentMenu implements AdapterView.OnItemClickListener{

    public static String TITULO_FRAGMENT = "Lista de Estabelecimentos";
    public static int ID_FRAGMENT = 4;
    public static int ICONE = 0;

    private FragListaEstabAdapter adapterMsg;

    private ListView listView;
    private List<Estabelecimento> listaEstabelecimentos;
    private Estabelecimento estabelecimento;

    private MsgToActivity msgToActivity;

    private boolean estouAtiva;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("ciclo2", "FragListaEstabelecimentos onCreateView");
        return inflater.inflate(R.layout.fragment_listaestabelecimentos, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i("ciclo2", "FragListaEstabelecimentos onActivityCreated");
        this.msgToActivity = (MsgToActivity) getActivity();
        this.adapterMsg = new FragListaEstabAdapter(this);
        inicializarListView();
        this.estouAtiva = true;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("ciclo2", "FragListaEstabelecimentos onStart");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i("ciclo2", "FragListaEstabelecimentos onAtach");
    }

    private void inicializarListView(){
        this.listView = (ListView) getActivity().findViewById(R.id.listaEstabelecimentos);
        this.listView.setAdapter(new ListEstabAdapter(getActivity(), this.listaEstabelecimentos));
        this.listView.setOnItemClickListener(this);
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        Log.i("ciclo2", "FragListaEstabelecimentos setArguments");
        this.listaEstabelecimentos = (List<Estabelecimento>) args.get("LISTAESTABELECIMENTOS");
        this.estabelecimento = (Estabelecimento) args.get("ESTABELECIMENTOSALVO");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("ciclo2", "FragListaEstabelecimentos onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("ciclo2", "FragListaEstabelecimentos onPause");
        this.estouAtiva = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("ciclo2", "FragListaEstabelecimentos onDestroy");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(this.msgToActivity.isAbertoProgresso()) return;
        this.msgToActivity.abrirProgresso();
        this.msgToActivity.setarTextoProgresso("Consultando estabelecimento.");
        Estabelecimento estabSelecionado = this.listaEstabelecimentos.get(position);
        ConexaoSaude conexao = new ConexaoSaude(this.adapterMsg);
        conexao.getEstabelelecimento(estabSelecionado.getCodUnidade());
    }

    public void inicializarFragEstabelecimento(){
        receberEstabelecimento(this.estabelecimento);
    }

    public void receberEstabelecimento(Estabelecimento estabelecimento){
        this.estabelecimento = estabelecimento;//salvando

        FragEstabelecimento fragEstabelecimento = new FragEstabelecimento();
        this.msgToActivity.setarTextoProgresso("Estabelecimento localizado.");
        this.msgToActivity.setarFragment(fragEstabelecimento);

        Bundle bundle = new Bundle();
        bundle.putSerializable("ESTABELECIMENTO", estabelecimento);
        fragEstabelecimento.setArguments(bundle);

        Intent intent = new Intent(getActivity(), NomeGeolocalizacaoService.class);
        intent.putExtra("ESTABELECIMENTO", estabelecimento);
        intent.putExtra(ReceiversNames.GEOLOCALIZACAO, fragEstabelecimento.getReceiver());
        getActivity().startService(intent);
    }

    public void resetEstabelecimento(){
        this.estabelecimento = null;
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

    public boolean isEstouAtiva() {
        return estouAtiva;
    }
}
