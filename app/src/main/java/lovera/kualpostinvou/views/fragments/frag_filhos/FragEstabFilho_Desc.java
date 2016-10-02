package lovera.kualpostinvou.views.fragments.frag_filhos;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import lovera.kualpostinvou.R;
import lovera.kualpostinvou.modelos.Especialidade;
import lovera.kualpostinvou.modelos.Estabelecimento;
import lovera.kualpostinvou.views.contratos.MsgToFragFilhos;
import lovera.kualpostinvou.views.fragments.FragmentFilho;

import static lovera.kualpostinvou.views.utils.Utils_View.setTextToLabel;

public class FragEstabFilho_Desc extends FragmentFilho {

    public static String TITULO_FRAGMENT = "Descricao";
    public static int ID_FRAGMENT = 0;
    public static int ICONE = R.drawable.ic_description_black_24dp;

    private Estabelecimento estabelecimento;

    private View progressoEspecialidades;

    private MsgToFragFilhos msg;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_estabelecimento_descricao, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        inicializarProgressos();
        setarCampos();
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        this.estabelecimento = (Estabelecimento) args.get("ESTABELECIMENTO");
    }

    private void setarCampos(){
        try{
            setTextToLabel(this.estabelecimento.getNomeFantasia()              , R.id.lblNomeFantasia, getView());
            setTextToLabel(this.estabelecimento.getTipoUnidade()               , R.id.lblTipoUnidade , getView());
            setTextToLabel(this.estabelecimento.getVinculoSus()                , R.id.lblVincSus     , getView());
            setTextToLabel(this.estabelecimento.getFluxoClientela()            , R.id.lblFlxClientela, getView());
            setTextToLabel(this.estabelecimento.getTemAtendimentoUrgencia()    , R.id.lblAtendEmgc   , getView());
            setTextToLabel(this.estabelecimento.getTemAtendimentoAmbulatorial(), R.id.lblAtendAmbulat, getView());
            setTextToLabel(this.estabelecimento.getTemCentroCirurgico()        , R.id.lblCCirurg     , getView());
            setTextToLabel(this.estabelecimento.getTemObstetra()               , R.id.lblObstetra    , getView());
            setTextToLabel(this.estabelecimento.getTemNeoNatal()               , R.id.lblNeonatal    , getView());
            setTextToLabel(this.estabelecimento.getTemDialise()                , R.id.lblDialise     , getView());
            setTextToLabel(this.estabelecimento.getDescricaoCompleta()         , R.id.lblDescompl    , getView());
            setTextToLabel(this.estabelecimento.getTurnoAtendimento()          , R.id.lblTurnoAtend  , getView());
        }
        catch(Exception e){
//            e.printStackTrace();
        }
    }

    public void setListaEspecialidades(List<Especialidade> listaEspecialidades){
        try{
            fecharProgressoProfissionais();
            LinearLayout layoutEspecialidades = (LinearLayout) getActivity().findViewById(R.id.f7_layoutEspecialidades);

            if(listaEspecialidades.size() > 0){
                for(Especialidade especialidade : listaEspecialidades){
                    layoutEspecialidades.addView(gerarTxtViewPProgressProf(especialidade.getDescricaoGrupo()));
                    layoutEspecialidades.addView(gerarTxtViewPProgressProf(especialidade.getDescricaoHabilitacao()));
                    layoutEspecialidades.addView(gerarTxtViewPProgressProf(" - "));
                }
            }
            else{
                layoutEspecialidades.addView(gerarTxtViewPProgressProf("Não há especialidades registradas"));
            }
        }catch (Exception e){}
    }

    private TextView gerarTxtViewPProgressProf(String texto){
        TextView textView = new TextView(getActivity());
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        textView.setPadding(5, 0, 0, 0);
        textView.setText(texto);
        return textView;
    }

    //Coisas relativas ao progresso de profissionais
    private void inicializarProgressos(){
        this.progressoEspecialidades = getActivity().findViewById(R.id.f7_progressoEspecialidades);
        if(this.msg.getListaDeProfissionais() != null){
            setListaEspecialidades(this.msg.getListaDeEspecialidades());
        }
    }

    private void fecharProgressoProfissionais(){
        this.progressoEspecialidades.setVisibility(View.GONE);
    }

    public void setMsg(MsgToFragFilhos msg) {
        this.msg = msg;
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
