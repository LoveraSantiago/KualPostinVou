package lovera.kualpostinvou.views.adapters;

import java.util.List;

import lovera.kualpostinvou.conexao.contratos.MsgFromConexaoSaude;
import lovera.kualpostinvou.modelos.Especialidade;
import lovera.kualpostinvou.modelos.Estabelecimento;
import lovera.kualpostinvou.modelos.Profissional;
import lovera.kualpostinvou.modelos.Servicos;
import lovera.kualpostinvou.views.fragments.frag_filhos.FragEstabFilho_Info;

public class FragEstabFilhoInfoAdapter implements MsgFromConexaoSaude{

    private FragEstabFilho_Info fragment;

    public FragEstabFilhoInfoAdapter(FragEstabFilho_Info fragment) {
        this.fragment = fragment;
    }

    @Override
    public void passarListaDeEstabelecimentos(List<Estabelecimento> listaDeEstabelecimentos) {

    }

    @Override
    public void passarEstabelecimento(Estabelecimento estabelecimento) {

    }

    @Override
    public void passarListaDeEspecialidades(List<Especialidade> especialidades) {

    }

    @Override
    public void passarListaDeProfissionais(List<Profissional> profissionais) {
        this.fragment.setListaDeProfissionais(profissionais);
    }

    @Override
    public void passarListaDeServicos(List<Servicos> servicos) {

    }
}
