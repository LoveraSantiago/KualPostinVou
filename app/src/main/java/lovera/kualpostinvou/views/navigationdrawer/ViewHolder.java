package lovera.kualpostinvou.views.navigationdrawer;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import lovera.kualpostinvou.Aplicacao;
import lovera.kualpostinvou.R;
import lovera.kualpostinvou.modelos.Pessoa;
import lovera.kualpostinvou.views.contratos.MsgFromNavigationDrawer;
import lovera.kualpostinvou.views.contratos.MsgFromViewHolder;
import lovera.kualpostinvou.views.contratos.MsgToViewHolderHeader;

public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, MsgToViewHolderHeader{

    private int Holderid;

    private TextView textView;
    private ImageView imageView;

    private ImageView profile;
    private TextView Name;
    private TextView email;

    private final MsgFromNavigationDrawer msgN;
    private final MsgFromViewHolder msgV;

    public ViewHolder(View itemView, int ViewType, MsgFromNavigationDrawer msg, MsgFromViewHolder msgV) {
        super(itemView);
        this.msgN = msg;
        this.msgV = msgV;

        itemView.setClickable(true);
        itemView.setOnClickListener(this);

        if(ViewType == RecyclerViewAdapterImpl.TYPE_ITEM) {
            this.textView = (TextView) itemView.findViewById(R.id.linhaTexto);
            this.imageView = (ImageView) itemView.findViewById(R.id.linhaIcone);
            this.Holderid = 1;
        }
        else{
            Aplicacao.getPessoaLogada().setReceptorMsg(this);
            this.Name = (TextView) itemView.findViewById(R.id.nome);
            this.email = (TextView) itemView.findViewById(R.id.email);
            this.profile = (ImageView) itemView.findViewById(R.id.circleView);
            this.Holderid = 0;
            headerAlterado();
        }
    }

    @Override
    public void headerAlterado() {
        Pessoa pessoa = Aplicacao.getPessoaLogada().getPessoa();
        Log.i("pessoalog", pessoa.toString());
        this.Name.setText(pessoa.getNomeCompleto());
        this.email.setText(pessoa.getEmail());
        Aplicacao.getPessoaLogada().getImgPessoa(this.profile);
    }

    @Override
    public void onClick(View v) {
        if(getAdapterPosition() == 0) return;

        if(this.msgV.getUltimaViewClicada() != null){
            this.msgV.getUltimaViewClicada().setBackgroundColor(Color.WHITE);
        }

        v.setBackgroundColor(Color.RED);
        this.msgN.selectItem(getAdapterPosition());
        this.msgV.setUltimaViewClicada(v);
    }

    public int getHolderid() {
        return Holderid;
    }

    public TextView getTextView() {
        return textView;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public ImageView getProfile() {
        return profile;
    }

    public TextView getName() {
        return Name;
    }

    public TextView getEmail() {
        return email;
    }
}
