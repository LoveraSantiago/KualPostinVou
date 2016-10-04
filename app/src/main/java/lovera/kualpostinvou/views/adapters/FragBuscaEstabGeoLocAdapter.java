package lovera.kualpostinvou.views.adapters;

import java.util.List;

import lovera.kualpostinvou.conexao.contratos.MsgFromConexaoSaude;
import lovera.kualpostinvou.modelos.Especialidade;
import lovera.kualpostinvou.modelos.Estabelecimento;
import lovera.kualpostinvou.modelos.Profissional;
import lovera.kualpostinvou.modelos.Servicos;
import lovera.kualpostinvou.views.fragments.FragBuscaEstabGeoLocalizacao2;

public class FragBuscaEstabGeoLocAdapter implements MsgFromConexaoSaude{

    private FragBuscaEstabGeoLocalizacao2 fragment;

    public FragBuscaEstabGeoLocAdapter(FragBuscaEstabGeoLocalizacao2 fragment) {
        this.fragment = fragment;
    }

    @Override
    public void passarListaDeEstabelecimentos(List<Estabelecimento> listaDeEstabelecimentos) {
        this.fragment.receberListaDeEstabelecimentos(listaDeEstabelecimentos);
    }

    @Override
    public void passarEstabelecimento(Estabelecimento estabelecimento) {}

    @Override
    public void passarListaDeEspecialidades(List<Especialidade> especialidades) {}

    @Override
    public void passarListaDeProfissionais(List<Profissional> profissionais) {}

    @Override
    public void passarListaDeServicos(List<Servicos> servicos) {
    }
}
