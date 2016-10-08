package lovera.kualpostinvou.views.components.navigationdrawer;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Map;

import lovera.kualpostinvou.Aplicacao;
import lovera.kualpostinvou.R;
import lovera.kualpostinvou.views.contratos.MsgFromNavigationDrawer;
import lovera.kualpostinvou.views.contratos.MsgFromViewHolder;
import lovera.kualpostinvou.views.fragments.FragmentMenu;
import lovera.kualpostinvou.views.redes_sociais.PessoaLogada;

public class RecyclerViewAdapterImpl extends RecyclerView.Adapter<ViewHolder> implements MsgFromViewHolder{

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_ITEM = 1;

    private PessoaLogada pessoaLogada;

    private View ultimaView;

    private final Map<Integer, FragmentMenu> mapFragments;
    private MsgFromNavigationDrawer msg;

    @Override
    public void setUltimaViewClicada(View view) {
        this.ultimaView = view;
    }

    @Override
    public View getUltimaViewClicada() {
        return this.ultimaView;
    }

    public RecyclerViewAdapterImpl(Map<Integer, FragmentMenu> mapFragments, MsgFromNavigationDrawer msg){
        this.pessoaLogada = Aplicacao.getPessoaLogada();
        this.mapFragments = mapFragments;
        this.msg = msg;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.linha_lista_navigationdrawer, parent, false);
            ViewHolder vhItem = new ViewHolder(v,viewType, this.msg, this);
            return vhItem;

        }
        else if (viewType == TYPE_HEADER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.linha_header_navigationdrawer,parent,false);
            ViewHolder vhHeader = new ViewHolder(v,viewType, this.msg, this);
            return vhHeader;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(holder.getHolderid() ==1) {
            holder.getTextView().setText(this.mapFragments.get(position).getFragmentTitulo());
            holder.getImageView().setImageResource(this.mapFragments.get(position).getIcone());
        }
        else{
            this.pessoaLogada.getImgPessoa(holder.getProfileImg());
            holder.getName().setText(this.pessoaLogada.getPessoa().getNomeCompleto());
            holder.getEmail().setText(this.pessoaLogada.getPessoa().getEmail());
        }
    }

    @Override
    public int getItemCount() {
        return this.mapFragments.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;

        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }
}
