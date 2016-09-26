package lovera.kualpostinvou.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import lovera.kualpostinvou.R;
import lovera.kualpostinvou.modelos.Estabelecimento;

import static lovera.kualpostinvou.views.utils.Utils.setTextToLabel;

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

        setTextToLabel(tamanhoDaStringNome(estabelecimento.getNomeFantasia())        , R.id.l1_valNomeEstab, layout);
        setTextToLabel(estabelecimento.getCidade()                                   , R.id.l1_valueCidade , layout);
        setTextToLabel(estabelecimento.getBairro()                                   , R.id.l1_valueBairro , layout);
        setTextToLabel((String.format("%.1f", estabelecimento.getDistancia())+" km") , R.id.l1_km          , layout);
        return layout;
    }

    private String tamanhoDaStringNome(String texto){
        String textoNome = texto;
        if(textoNome.length() > 35){
            textoNome = textoNome.substring(0, 33) + "...";
        }
        return textoNome;
    }
}
