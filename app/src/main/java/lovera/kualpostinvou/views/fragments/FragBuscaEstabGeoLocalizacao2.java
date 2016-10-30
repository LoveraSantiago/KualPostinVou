package lovera.kualpostinvou.views.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lovera.kualpostinvou.Aplicacao;
import lovera.kualpostinvou.R;
import lovera.kualpostinvou.conexao.ConexaoSaude;
import lovera.kualpostinvou.modelos.Estabelecimento;
import lovera.kualpostinvou.modelos.Localizacao;
import lovera.kualpostinvou.modelos.utils.Distancia;
import lovera.kualpostinvou.views.adapters.FragBuscaEstabGeoLocAdapter;
import lovera.kualpostinvou.views.components.SeekBarChangeListenerImpl;
import lovera.kualpostinvou.views.contratos.MsgToActivity;
import lovera.kualpostinvou.views.components.dialogs.DismissDialog;
import lovera.kualpostinvou.views.receivers.CommonsReceiver;
import lovera.kualpostinvou.views.redes_sociais.google.HelperGeolocalizacao;
import lovera.kualpostinvou.views.services.ServicesNames;

import static lovera.kualpostinvou.views.utils.FactoryViews.factoryDismissDialog;

public class FragBuscaEstabGeoLocalizacao2 extends FragmentMenu implements CommonsReceiver.Receiver{

    //Campos relativos a FragmentMenu
    public static String TITULO_FRAGMENT = "Estabelecimentos proximos";
    public static int ID_FRAGMENT = 1;
    public static int ICONE = R.drawable.localizacao;

    private HelperGeolocalizacao helperGps;
    private FragBuscaEstabGeoLocAdapter adapterMgs;
    private MsgToActivity msgActivity;

    //Parametros
    private String paramCategoria;
    private Localizacao localizacao;

    private TextView lblSeekBar;

    private CommonsReceiver receiver;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_buscaestabelecimento_geolocalizacao2, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.helperGps = Aplicacao.getHelperGps();
        this.adapterMgs = new FragBuscaEstabGeoLocAdapter(this);

        this.msgActivity = (MsgToActivity) getActivity();
        inicializarComponentes();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void inicializarComponentes(){
        lblSeekBar  = (TextView) getView().findViewById(R.id.f2_lblseekbar);
        final SeekBar seekBar = (SeekBar) getView().findViewById(R.id.f2_seekbar);
        seekBar.setOnSeekBarChangeListener(new SeekBarChangeListenerImpl(this.lblSeekBar));

        Button btnDecremento = (Button) getView().findViewById(R.id.f2_menos);
        btnDecremento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int result = Integer.parseInt(lblSeekBar.getText().toString()) - 2;
                if(result >= 0){
                    seekBar.setProgress(result);
                }
            }
        });

        Button btnIncremento = (Button) getView().findViewById(R.id.f2_mais);
        btnIncremento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int result = Integer.parseInt(lblSeekBar.getText().toString());
                if(result <= 50){
                    seekBar.setProgress(result);
                }
            }
        });
        inicializarReceivers();
    }

    private void inicializarReceivers(){
        this.receiver = new CommonsReceiver(new Handler());
        this.receiver.setReceiver(this);
    }

    private void showDialogGpsCancelado(){
        DismissDialog dialog = factoryDismissDialog(getActivity(),"Localização Requerida",
                "Para realizar a busca por estabelecimentos é necessario autorizar o gps.");
        dialog.show();
    }

    private void showDialogGpsLocalizacaoFalha(){
        DismissDialog dialog = factoryDismissDialog(getActivity(),"Localização Não Encontrada",
                "Não foi possivel encontrar sua localização. Tente mais tarde por favor.");
        dialog.show();
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

//    private void consumirEstabelecimentos(){
//        receberLocalizacao(this.helperGps.getLocalizacao());
//        this.msgActivity.setarTextoProgresso("Buscando dados.");
//
//        ConexaoSaude conexaoSaude = new ConexaoSaude(this.adapterMgs);
//        conexaoSaude.getEstabelecimentos(localizacao.getLatitude(), localizacao.getLongitude(),
//                Float.parseFloat(this.lblSeekBar.getText().toString()),
//                null, this.paramCategoria, "codUnidade,nomeFantasia,bairro,cidade,lat,long", 0, 200);
//    }

    private void consumirEstabelecimentos(){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                receberLocalizacao(helperGps.getLocalizacao());
                msgActivity.setarTextoProgresso("Buscando dados.");

                List<Estabelecimento> listaDeEstabelecimentos = new ArrayList<>();

                ConexaoSaude conexaoSaude = new ConexaoSaude(adapterMgs);
                conexaoSaude.getEstabelecimentos2(localizacao, Float.parseFloat(lblSeekBar.getText().toString()),
                        null, paramCategoria, "codUnidade,nomeFantasia,bairro,cidade,lat,long", 0, 200, listaDeEstabelecimentos);
                receberListaDeEstabelecimentos(listaDeEstabelecimentos);
            }
        });
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

    private void showDialogListaVazia(){
        DismissDialog dialog = factoryDismissDialog(getActivity(),"Nenhum Resultado",
                "Não foi encontrado estabelecimentos. Tente aumentar o raio da busca.");
        dialog.show();
    }

    private void calcularDistanciaDosEstabelecimentos(List<Estabelecimento> listaDeEstabelecimentos){
        this.msgActivity.setarTextoProgresso("Calculando distâncias");
        Distancia distancia = new Distancia();
        distancia.calcularKmDistancia(listaDeEstabelecimentos, this.localizacao);
        this.msgActivity.fecharProgresso();
    }

    //Metodos sobrescritos herdados da classe pai FragMenu
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

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        if(resultCode == ServicesNames.GPS_SERVICE){
            if(resultData == null){
                showDialogGpsCancelado();
            }
            else{
                if(resultData.getBoolean("resultado")){
                    consumirEstabelecimentos();
                }
                else{
                    showDialogGpsLocalizacaoFalha();
                }
            }
        }
    }
}
