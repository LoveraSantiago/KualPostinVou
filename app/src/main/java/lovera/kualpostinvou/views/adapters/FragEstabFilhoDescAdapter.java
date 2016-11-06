package lovera.kualpostinvou.views.adapters;

import java.util.List;

import lovera.kualpostinvou.conexao.contratos.MsgFromConexaoSaude;
import lovera.kualpostinvou.modelos.Especialidade;
import lovera.kualpostinvou.modelos.Estabelecimento;
import lovera.kualpostinvou.modelos.Profissional;
import lovera.kualpostinvou.modelos.Servicos;
import lovera.kualpostinvou.views.fragments.fragestabelecimento.frag_filhos.FragEstabFilho_Desc;

public class FragEstabFilhoDescAdapter implements MsgFromConexaoSaude{

    private FragEstabFilho_Desc fragment;

    public FragEstabFilhoDescAdapter(FragEstabFilho_Desc fragment) {
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
        this.fragment.setListaEspecialidades(especialidades);
    }

    @Override
    public void passarListaDeProfissionais(List<Profissional> profissionais) {

    }

    @Override
    public void passarListaDeServicos(List<Servicos> servicos) {

    }
}
