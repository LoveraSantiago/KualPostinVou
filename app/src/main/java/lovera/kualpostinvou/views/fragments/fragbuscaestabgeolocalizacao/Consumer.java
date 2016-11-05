package lovera.kualpostinvou.views.fragments.fragbuscaestabgeolocalizacao;

import android.os.Bundle;

import java.io.Serializable;
import java.util.List;

import lovera.kualpostinvou.Aplicacao;
import lovera.kualpostinvou.conexao.ConexaoSaude;
import lovera.kualpostinvou.modelos.Estabelecimento;
import lovera.kualpostinvou.modelos.Localizacao;
import lovera.kualpostinvou.modelos.utils.Distancia;
import lovera.kualpostinvou.views.contratos.MsgToActivity;
import lovera.kualpostinvou.views.fragments.fraglistaestabelecimentos.FragListaEstabelecimentos;
import lovera.kualpostinvou.views.redes_sociais.google.HelperGeolocalizacao;

class Consumer {

    private final FragBuscaEstabGeoLocalizacao2 fragment;

    private final MsgToActivity msgActivity;
    private final HelperGeolocalizacao helperGps;
    private final Adapter adapterMgs;

    private String paramCategoria;

    private Localizacao localizacao;

    public Consumer(FragBuscaEstabGeoLocalizacao2 fragment) {
        this.fragment = fragment;
        this.helperGps = Aplicacao.getHelperGps();
        this.msgActivity = (MsgToActivity) fragment.getActivity();
        this.adapterMgs = new Adapter(this);
    }

    public void inicio_ConsumirEstabelecimentos(String categoria){
        this.msgActivity.abrirProgresso();
        this.msgActivity.setarTextoProgresso("Procurando sua localização.");
        this.paramCategoria = categoria;

        if(this.helperGps.temLastLocation()){
            inicio2_consumirEstabelecimentos();
        }
        else{
            this.helperGps.popupLigarGps(this.fragment.getReceiver().getCommonsReceiver());
        }
    }

    public void inicio2_consumirEstabelecimentos(){
        setLocalizacao(this.helperGps.getLocalizacao());
        this.msgActivity.setarTextoProgresso("Buscando dados.");

        ConexaoSaude conexaoSaude = new ConexaoSaude(this.adapterMgs);
        conexaoSaude.getEstabelecimentos(localizacao.getLatitude(), localizacao.getLongitude(),
                Float.parseFloat(this.fragment.getViews().getLblSeekBar().getText().toString()),
                null, this.paramCategoria, "codUnidade,nomeFantasia,bairro,cidade,lat,long", 0, 200);
    }

    public void callback_consumirEstabelecimentos(List<Estabelecimento> listaDeEstabelecimentos){
        if(listaDeEstabelecimentos.size() == 0){
            callback_consumirEstabelecimentos_FALHA();
        }
        else{
            callback_consumirEstabelecimentos_SUCESSO(listaDeEstabelecimentos);
        }
    }

    private void callback_consumirEstabelecimentos_FALHA(){
        this.msgActivity.fecharProgresso();
        this.fragment.getDialogs().showDialogListaVazia();
    }

    private void callback_consumirEstabelecimentos_SUCESSO(List<Estabelecimento> listaDeEstabelecimentos){
        calcularDistanciaDosEstabelecimentos(listaDeEstabelecimentos);
        Bundle bundle = new Bundle();
        bundle.putSerializable("LISTAESTABELECIMENTOS", (Serializable) listaDeEstabelecimentos);

        FragListaEstabelecimentos fragLista = new FragListaEstabelecimentos();
        fragLista.setArguments(bundle);
        this.msgActivity.setarFragment(fragLista);
    }

    private void calcularDistanciaDosEstabelecimentos(List<Estabelecimento> listaDeEstabelecimentos){
        this.msgActivity.setarTextoProgresso("Calculando distâncias");
        Distancia distancia = new Distancia();
        distancia.calcularKmDistancia(listaDeEstabelecimentos, this.localizacao);
        this.msgActivity.fecharProgresso();
    }

    private void setLocalizacao(Localizacao localizacao){
        this.localizacao = localizacao;
    }
}
