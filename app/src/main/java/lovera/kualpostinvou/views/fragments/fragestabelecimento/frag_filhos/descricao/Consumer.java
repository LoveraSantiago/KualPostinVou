package lovera.kualpostinvou.views.fragments.fragestabelecimento.frag_filhos.descricao;

import java.util.List;

import lovera.kualpostinvou.conexao.ConexaoSaude;
import lovera.kualpostinvou.modelos.Especialidade;
import lovera.kualpostinvou.modelos.Estabelecimento;

class Consumer {

    private final FragFilho_Descricao fragment;

    private Estabelecimento estabelecimento;
    private List<Especialidade> listaEspecialidades;

    public Consumer(FragFilho_Descricao fragment) {
        this.fragment = fragment;
    }

    public void inicio_consumirEspecialidades(){
        if(this.listaEspecialidades == null){
            Adapter adapter = new Adapter(this);
            ConexaoSaude conexaoSaude = new ConexaoSaude(adapter);
            conexaoSaude.getEspecialidades(this.estabelecimento.getCodUnidade());
        }
        else{
            callback_consumirEspecialidades(this.listaEspecialidades);
        }
    }

    public void callback_consumirEspecialidades(List<Especialidade> listaEspecialidades) {
        this.listaEspecialidades = listaEspecialidades;
        this.fragment.getViews().setListaEspecialidadesParaTabela(this.listaEspecialidades);
    }

    public Estabelecimento getEstabelecimento() {
        return estabelecimento;
    }

    public void setEstabelecimento(Estabelecimento estabelecimento) {
        this.estabelecimento = estabelecimento;
    }
}
