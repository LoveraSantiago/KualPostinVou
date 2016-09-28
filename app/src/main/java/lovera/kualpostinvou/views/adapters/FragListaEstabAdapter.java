package lovera.kualpostinvou.views.adapters;

import java.util.List;

import lovera.kualpostinvou.conexao.contratos.MsgFromConexaoSaude;
import lovera.kualpostinvou.modelos.Especialidade;
import lovera.kualpostinvou.modelos.Estabelecimento;
import lovera.kualpostinvou.modelos.Profissional;
import lovera.kualpostinvou.modelos.Servicos;
import lovera.kualpostinvou.views.fragments.FragListaEstabelecimentos;

public class FragListaEstabAdapter implements MsgFromConexaoSaude {

    private FragListaEstabelecimentos fragment;


    public FragListaEstabAdapter(FragListaEstabelecimentos fragment) {
        this.fragment = fragment;
    }

    @Override
    public void passarListaDeEstabelecimentos(List<Estabelecimento> listaDeEstabelecimentos) {
        this.fragment.receberEstabelecimento(listaDeEstabelecimentos.get(0));
    }

    @Override
    public void passarEstabelecimento(Estabelecimento estabelecimento) {
        this.fragment.receberEstabelecimento(estabelecimento);
    }

    @Override
    public void passarListaDeEspecialidades(List<Especialidade> especialidades) {

    }

    @Override
    public void passarListaDeProfissionais(List<Profissional> profissionais) {

    }

    @Override
    public void passarListaDeServicos(List<Servicos> servicos) {

    }
}
