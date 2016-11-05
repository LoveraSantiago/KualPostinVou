package lovera.kualpostinvou.views.fragments.fraglistaestabelecimentos;

import android.content.Intent;
import android.os.Bundle;

import java.util.List;

import lovera.kualpostinvou.conexao.ConexaoSaude;
import lovera.kualpostinvou.modelos.Estabelecimento;
import lovera.kualpostinvou.views.contratos.MsgToActivity;
import lovera.kualpostinvou.views.fragments.FragEstabelecimento;
import lovera.kualpostinvou.views.receivers.ReceiversNames;
import lovera.kualpostinvou.views.services.NomeGeolocalizacaoService;

class Consumer {

    private List<Estabelecimento> listaEstabelecimentos;
    private Estabelecimento estabelecimento;

    private final FragListaEstabelecimentos fragment;
    private final MsgToActivity msgToActivity;

    private final Adapter adapter;

    public Consumer(FragListaEstabelecimentos fragment) {
        this.fragment = fragment;
        this.msgToActivity = (MsgToActivity) fragment.getActivity();
        this.adapter = new Adapter(this);
    }

    public void inicio_ConsumirEstabelecimento(int posicao){
        if(this.msgToActivity.isAbertoProgresso()) return;
        this.msgToActivity.abrirProgresso();
        this.msgToActivity.setarTextoProgresso("Consultando estabelecimento.");
        Estabelecimento estabSelecionado = this.listaEstabelecimentos.get(posicao);
        ConexaoSaude conexao = new ConexaoSaude(this.adapter);
        conexao.getEstabelelecimento(estabSelecionado.getCodUnidade());
    }

    public void callback_ConsumirEstabelecimento(Estabelecimento estabelecimento) {
        setEstabelecimento(estabelecimento);//salvando

        FragEstabelecimento fragEstabelecimento = new FragEstabelecimento();
        this.msgToActivity.setarTextoProgresso("Estabelecimento localizado.");
        this.msgToActivity.setarFragment(fragEstabelecimento);

        Bundle bundle = new Bundle();
        bundle.putSerializable("ESTABELECIMENTO", estabelecimento);
        fragEstabelecimento.setArguments(bundle);

        Intent intent = new Intent(this.fragment.getActivity(), NomeGeolocalizacaoService.class);
        intent.putExtra("ESTABELECIMENTO", estabelecimento);
        intent.putExtra(ReceiversNames.GEOLOCALIZACAO, fragEstabelecimento.getReceiver());
        this.fragment.getActivity().startService(intent);
    }

    public void fromActivity_consumirEstabelecimento() {
        callback_ConsumirEstabelecimento(this.estabelecimento);
    }

    public List<Estabelecimento> getListaEstabelecimentos() {
        return listaEstabelecimentos;
    }

    public void setListaEstabelecimentos(List<Estabelecimento> listaEstabelecimentos) {
        this.listaEstabelecimentos = listaEstabelecimentos;
    }

    public Estabelecimento getEstabelecimento() {
        return estabelecimento;
    }

    public void setEstabelecimento(Estabelecimento estabelecimento) {
        this.estabelecimento = estabelecimento;
    }
}
