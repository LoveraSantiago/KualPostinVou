package lovera.kualpostinvou.views.components.helpers.fragbuscaestabgeolocalizacao;

import android.app.Activity;
import android.os.Bundle;

import java.io.Serializable;
import java.util.List;

import lovera.kualpostinvou.Aplicacao;
import lovera.kualpostinvou.conexao.ConexaoSaude;
import lovera.kualpostinvou.modelos.Estabelecimento;
import lovera.kualpostinvou.modelos.Localizacao;
import lovera.kualpostinvou.modelos.utils.Distancia;
import lovera.kualpostinvou.views.components.dialogs.DismissDialog;
import lovera.kualpostinvou.views.contratos.MsgToActivity;
import lovera.kualpostinvou.views.fragments.FragListaEstabelecimentos;
import lovera.kualpostinvou.views.redes_sociais.google.HelperGeolocalizacao;

import static lovera.kualpostinvou.views.utils.FactoryViews.factoryDismissDialog;

public class Consumer {

    private MsgToActivity msgActivity;
    private HelperGeolocalizacao helperGps;

    private String paramCategoria;
    private Localizacao localizacao;

    public Consumer(Activity activity) {
        this.helperGps = Aplicacao.getHelperGps();
        this.msgActivity = (MsgToActivity) activity;
    }

    public void consumirEstabelecimentosGeolocalizacao(String categoria){
        this.msgActivity.abrirProgresso();
        this.msgActivity.setarTextoProgresso("Procurando sua localização.");
        this.paramCategoria = categoria;

        if(this.helperGps.temLastLocation()){
            consumirEstabelecimentos();
        }
        else{
            this.helperGps.popupLigarGps(this.receiver);
        }
    }

    private void consumirEstabelecimentos(){
        receberLocalizacao(this.helperGps.getLocalizacao());
        this.msgActivity.setarTextoProgresso("Buscando dados.");

        ConexaoSaude conexaoSaude = new ConexaoSaude(this.adapterMgs);
        conexaoSaude.getEstabelecimentos(localizacao.getLatitude(), localizacao.getLongitude(),
                Float.parseFloat(this.lblSeekBar.getText().toString()),
                null, this.paramCategoria, "codUnidade,nomeFantasia,bairro,cidade,lat,long", 0, 200);
    }

    public void receberLocalizacao(Localizacao localizacao){
        this.localizacao = localizacao;
    }

    public void receberListaDeEstabelecimentos(List<Estabelecimento> listaDeEstabelecimentos){
        if(listaDeEstabelecimentos.size() == 0){
            this.msgActivity.fecharProgresso();
            showDialogListaVazia();
            return;
        }
        else{
            calcularDistanciaDosEstabelecimentos(listaDeEstabelecimentos);
            Bundle bundle = new Bundle();
            bundle.putSerializable("LISTAESTABELECIMENTOS", (Serializable) listaDeEstabelecimentos);

            FragListaEstabelecimentos fragLista = new FragListaEstabelecimentos();
            fragLista.setArguments(bundle);
            this.msgActivity.setarFragment(fragLista);
        }
    }

    private void calcularDistanciaDosEstabelecimentos(List<Estabelecimento> listaDeEstabelecimentos){
        this.msgActivity.setarTextoProgresso("Calculando distâncias");
        Distancia distancia = new Distancia();
        distancia.calcularKmDistancia(listaDeEstabelecimentos, this.localizacao);
        this.msgActivity.fecharProgresso();
    }


}
