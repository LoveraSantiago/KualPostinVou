package lovera.kualpostinvou.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import lovera.kualpostinvou.R;
import lovera.kualpostinvou.modelos.Estabelecimento;

public class EstabelecimentoAdapter extends BaseAdapter {

    private Context context;
    private List<Estabelecimento> listaEstabelecimentos;

    public EstabelecimentoAdapter(Context context, List<Estabelecimento> listaEstabelecimentos) {
        this.context = context;
        this.listaEstabelecimentos = listaEstabelecimentos;
    }

    @Override
    public int getCount() {
        return this.listaEstabelecimentos.size();
    }

    @Override
    public Object getItem(int position) {
        return this.listaEstabelecimentos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Estabelecimento estabelecimento = listaEstabelecimentos.get(position);

        LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = null;

        if(convertView == null){
            layout = inflater.inflate(R.layout.linha_lista_estabelecimento, null);
        }
        else{
            layout = convertView;
        }

        setTextToLabel(estabelecimento.getCodUnidade(), R.id.lblCodigo, layout);
        setTextToLabel(estabelecimento.getNomeFantasia(), R.id.lblNomeFantasia, layout);
        setTextToLabel(estabelecimento.getCodCnes(), R.id.lblCodCnes, layout);
        setTextToLabel(estabelecimento.getNatureza(), R.id.lblNatureza, layout);
        setTextToLabel(estabelecimento.getTipoUnidade(), R.id.lblTipoUnidade, layout);
        setTextToLabel(estabelecimento.getEsferaAdministrativa(), R.id.lblEsferaAdm, layout);
        setTextToLabel(estabelecimento.getVinculoSus(), R.id.lblVincSus, layout);
        setTextToLabel(estabelecimento.getRetencao(), R.id.lblRetencao, layout);
        setTextToLabel(estabelecimento.getFluxoClientela(), R.id.lblFlxClientela, layout);
        setTextToLabel(estabelecimento.getOrigemGeografica(), R.id.lblOrigGeograf, layout);
        setTextToLabel(estabelecimento.getTemAtendimentoAmbulatorial(), R.id.lblAtendAmbulat, layout);
        setTextToLabel(estabelecimento.getTemAtendimentoUrgencia(), R.id.lblAtendEmgc, layout);
        setTextToLabel(estabelecimento.getTemCentroCirurgico(), R.id.lblCCirurg, layout);
        return layout;
    }

    public void setTextToLabel(int texto, int id, View layout){
        setTextToLabel(String.valueOf(texto), id, layout);
    }

    public void setTextToLabel(String texto, int id, View layout){
        TextView lblCodigo = (TextView) layout.findViewById(id);
        lblCodigo.setText(texto);
    }


}
