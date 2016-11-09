package lovera.kualpostinvou.views.adapters;

import java.util.List;

import lovera.kualpostinvou.conexao.contratos.MsgFromConexaoSaude;
import lovera.kualpostinvou.modelos.Especialidade;
import lovera.kualpostinvou.modelos.Estabelecimento;
import lovera.kualpostinvou.modelos.Profissional;
import lovera.kualpostinvou.modelos.Servicos;
import lovera.kualpostinvou.views.fragments.fragestabelecimento.frag_filhos.informacao.FragFilho_Informacao;

public class FragEstabFilhoInfoAdapter implements MsgFromConexaoSaude{

    private FragFilho_Informacao fragment;

    public FragEstabFilhoInfoAdapter(FragFilho_Informacao fragment) {
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
