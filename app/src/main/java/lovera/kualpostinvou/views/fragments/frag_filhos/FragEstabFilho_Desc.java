package lovera.kualpostinvou.views.fragments.frag_filhos;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import lovera.kualpostinvou.R;
import lovera.kualpostinvou.conexao.ConexaoSaude;
import lovera.kualpostinvou.modelos.Especialidade;
import lovera.kualpostinvou.modelos.Estabelecimento;
import lovera.kualpostinvou.views.adapters.FragEstabFilhoDescAdapter;
import lovera.kualpostinvou.views.fragments.FragmentFilho;

import static lovera.kualpostinvou.views.utils.Utils_View.setImageToImgView;
import static lovera.kualpostinvou.views.utils.Utils_View.setTextToLabel;

public class FragEstabFilho_Desc extends FragmentFilho {

    public static String TITULO_FRAGMENT = "Filho Descricao";
    public static int ID_FRAGMENT = 0;
    public static int ICONE = R.drawable.icn_arquivo;

    private Estabelecimento estabelecimento;

    private List<Especialidade> listaEspecialidades;

    private View progressoEspecialidades;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_estabelecimento_descricao, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        inicializarProgressos();
        consumirEspecialidades();
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
            setTextToLabel(this.estabelecimento.getDescricaoCompleta()         , R.id.lblDescompl    , getView());
            setTextToLabel(this.estabelecimento.getTurnoAtendimento()          , R.id.lblTurnoAtend  , getView());

            setImageToImgView(setImage(this.estabelecimento.getTemDialise())                , R.id.imgDialise     , getView());
            setImageToImgView(setImage(this.estabelecimento.getTemObstetra())               , R.id.imgObstetra    , getView());
            setImageToImgView(setImage(this.estabelecimento.getTemNeoNatal())               , R.id.imgNeonatal    , getView());
            setImageToImgView(setImage(this.estabelecimento.getTemCentroCirurgico())        , R.id.imgCCirurg     , getView());
            setImageToImgView(setImage(this.estabelecimento.getTemNeoNatal())               , R.id.imgNeonatal    , getView());
            setImageToImgView(setImage(this.estabelecimento.getTemAtendimentoUrgencia())    , R.id.imgAtendEmgc   , getView());
            setImageToImgView(setImage(this.estabelecimento.getTemAtendimentoAmbulatorial()), R.id.imgAtendAmbulat, getView());
        }
        catch(Exception e){
//            e.printStackTrace();
        }
    }

    private int setImage(String campo){
        return campo.equals("Sim") ? R.drawable.icn_check : R.drawable.icn_cancelar;
    }

    private void consumirEspecialidades(){
        if(this.listaEspecialidades != null){
            setListaEspecialidades(this.listaEspecialidades);
        }
        else{
            FragEstabFilhoDescAdapter adapter = new FragEstabFilhoDescAdapter(this);
            ConexaoSaude conexaoSaude = new ConexaoSaude(adapter);
            conexaoSaude.getEspecialidades(this.estabelecimento.getCodUnidade());
        }
    }

    public void setListaEspecialidades(List<Especialidade> listaEspecialidades){
        this.listaEspecialidades = listaEspecialidades;
        try{
            fecharProgressoProfissionais();
            LinearLayout linhaUnica = (LinearLayout) getActivity().findViewById(R.id.f7_layoutEspecialidades);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 0, 0, 1);

            if(listaEspecialidades.size() > 0){
                for(Especialidade especialidade : listaEspecialidades){
                    LinearLayout linha = new LinearLayout(getActivity());
                    linha.setLayoutParams(params);
                    linha.setOrientation(LinearLayout.HORIZONTAL);
                    linha.setGravity(Gravity.CENTER);
                    linha.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.mAzulBranco));

                    linha.addView(gerarTxtViewPProgressProf(especialidade.getDescricaoHabilitacao() + " - " + especialidade.getDescricaoGrupo()));
                    linhaUnica.addView(linha);
                }
            }
            else{
                LinearLayout linha = new LinearLayout(getActivity());
                linha.addView(gerarTxtViewPProgressProf("Não há especialidades registradas"));
                linhaUnica.addView(linha);
            }
        }catch (Exception e){e.printStackTrace();}
    }

    private TextView gerarTxtViewPProgressProf(String texto){
        TextView textView = new TextView(getActivity());
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        textView.setText(texto);
        textView.setPadding(5, 0, 0, 0);
        return textView;
    }

    //Coisas relativas ao progresso de profissionais
    private void inicializarProgressos(){
        this.progressoEspecialidades = getActivity().findViewById(R.id.f7_progressoEspecialidades);
    }

    private void fecharProgressoProfissionais(){
        this.progressoEspecialidades.setVisibility(View.GONE);
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
