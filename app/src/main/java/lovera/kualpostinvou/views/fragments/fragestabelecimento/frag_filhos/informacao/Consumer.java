package lovera.kualpostinvou.views.fragments.fragestabelecimento.frag_filhos.informacao;

import java.util.List;

import lovera.kualpostinvou.conexao.ConexaoSaude;
import lovera.kualpostinvou.modelos.Estabelecimento;
import lovera.kualpostinvou.modelos.Profissional;

public class Consumer {

    private final FragFilho_Informacao fragment;

    private Estabelecimento estabelecimento;
    private List<Profissional> listaDeProfissionais;

    public Consumer(FragFilho_Informacao fragment) {
        this.fragment = fragment;
    }

    public void inicio_consumirProfissionais(){
        if(this.listaDeProfissionais == null){
            Adapter adapter = new Adapter(this);
            ConexaoSaude conexaoSaude = new ConexaoSaude(adapter);
            conexaoSaude.getProfissionais(this.estabelecimento.getCodUnidade());
        }
        else{
            callback_consumirProfissionais(this.listaDeProfissionais);
        }
    }

    public void callback_consumirProfissionais(List<Profissional> listaDeProfissionais){
        this.listaDeProfissionais = listaDeProfissionais;
        this.fragment.getViews().setListaDeProfissionaisParaTabela(this.listaDeProfissionais);
    }

    public void setEstabelecimento(Estabelecimento estabelecimento) {
        this.estabelecimento = estabelecimento;
    }

    public Estabelecimento getEstabelecimento() {
        return estabelecimento;
    }
}
