package lovera.kualpostinvou.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.Serializable;
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
import lovera.kualpostinvou.views.dialogs.DismissDialog;
import lovera.kualpostinvou.views.redes_sociais.google.HelperGeolocalizacao;
import lovera.kualpostinvou.views.services.LocalizacaoService;

public class FragBuscaEstabGeoLocalizacao2 extends FragmentMenu{

    //Campos relativos a FragmentMenu
    public static String TITULO_FRAGMENT = "Estabelecimentos proximos";
    public static int ID_FRAGMENT = 2;
    public static int ICONE = R.drawable.icn2;

    private HelperGeolocalizacao helperGps;
    private FragBuscaEstabGeoLocAdapter adapterMgs;
    private MsgToActivity msgActivity;
    //Componentes da tela
    private SeekBar seekBar;
    private TextView lblSeekBar;

    //Parametros
    private String paramCategoria;
    private Localizacao localizacao;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_buscaestabelecimento_geolocalizacao2, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.helperGps = new HelperGeolocalizacao(this);
        this.adapterMgs = new FragBuscaEstabGeoLocAdapter(this);

        this.msgActivity = (MsgToActivity) getActivity();
        inicializarComponentes();
    }

    private void inicializarComponentes(){
        this.lblSeekBar  = (TextView) getView().findViewById(R.id.f2_lblseekbar);
        this.seekBar = (SeekBar) getView().findViewById(R.id.f2_seekbar);
        this.seekBar.setOnSeekBarChangeListener(new SeekBarChangeListenerImpl(this.lblSeekBar));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == HelperGeolocalizacao.USUARIO_ESCOLHENDO_OPCAO){
            if(resultCode == getActivity().RESULT_OK){
                Aplicacao.setMensageiroGps(this.adapterMgs);

                Intent intent = new Intent(getActivity(), LocalizacaoService.class);
                getActivity().startService(intent);
            }
            else if(resultCode == getActivity().RESULT_CANCELED){
                this.msgActivity.fecharProgresso();
                showDialogGpsCancelado();
            }
        }
    }

    private void showDialogGpsCancelado(){
        DismissDialog dialog = new DismissDialog(getActivity());
        dialog.setTitle("Localização Requerida");
        dialog.setMessage("Para realizar a busca por estabelecimentos é necessario autorizar o gps");
        dialog.show();
    }

    public void incrementarDistancia(){
        int result = Integer.parseInt(this.lblSeekBar.getText().toString());
        if(result <= 50){
            this.seekBar.setProgress(result);
        }
    }

    public void decrementarDistancia(){
        int result = Integer.parseInt(this.lblSeekBar.getText().toString()) - 2;
        if(result >= 0){
            this.seekBar.setProgress(result);
        }
    }

    public void consumirEstabelecimentosGeolocalizacao(String categoria){
        this.msgActivity.abrirProgresso();
        this.msgActivity.setarTextoProgresso("Procurando sua localização.");
        this.paramCategoria = categoria;

        if(this.helperGps.temLastLocation()){
            procedimentoConsumoEstabelecimentoEmComum();
        }
        else{
            this.helperGps.popupLigarGps();
        }
    }

    public void gpsEnconstradoPeloService(){
        Aplicacao.setMensageiroGps(null);
        procedimentoConsumoEstabelecimentoEmComum();
    }

    private void procedimentoConsumoEstabelecimentoEmComum(){
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

    private void showDialogListaVazia(){
        DismissDialog dialog = new DismissDialog(getActivity());
        dialog.setTitle("Nenhum Resultado");
        dialog.setMessage("Não foi encontrado estabelecimentos. Tente aumentar o raio da busca.");
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
}
