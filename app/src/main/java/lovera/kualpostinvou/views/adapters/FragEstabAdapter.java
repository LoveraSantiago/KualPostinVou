package lovera.kualpostinvou.views.adapters;

import java.util.List;

import lovera.kualpostinvou.conexao.contratos.MsgFromConexaoSaude;
import lovera.kualpostinvou.modelos.Especialidade;
import lovera.kualpostinvou.modelos.Estabelecimento;
import lovera.kualpostinvou.modelos.Profissional;
import lovera.kualpostinvou.modelos.Servicos;
import lovera.kualpostinvou.views.fragments.FragEstabelecimento;

public class FragEstabAdapter implements MsgFromConexaoSaude{

    private FragEstabelecimento fragEstabelecimento;

    public FragEstabAdapter(FragEstabelecimento fragEstabelecimento) {
        this.fragEstabelecimento = fragEstabelecimento;
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
        this.fragEstabelecimento.setListaDeProfissionais(profissionais);
    }

    @Override
    public void passarListaDeServicos(List<Servicos> servicos) {

    }
}