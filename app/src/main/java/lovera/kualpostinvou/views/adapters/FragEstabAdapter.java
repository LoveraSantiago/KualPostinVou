package lovera.kualpostinvou.views.adapters;

import java.util.List;

import lovera.kualpostinvou.conexao.contratos.MsgFromConexaoModelo;
import lovera.kualpostinvou.conexao.contratos.MsgFromConexaoSaude;
import lovera.kualpostinvou.modelos.ErrorObj;
import lovera.kualpostinvou.modelos.Especialidade;
import lovera.kualpostinvou.modelos.Estabelecimento;
import lovera.kualpostinvou.modelos.Grupo;
import lovera.kualpostinvou.modelos.Profissional;
import lovera.kualpostinvou.modelos.Servicos;
import lovera.kualpostinvou.views.fragments.FragEstabelecimento;

public class FragEstabAdapter implements MsgFromConexaoSaude, MsgFromConexaoModelo{

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

    @Override
    public void passarGrupo(Grupo grupo) {

    }

    @Override
    public void passarListaDeGrupos(List<Grupo> listaDeGrupos) {
        this.fragEstabelecimento.consumirAvaliacoesTempoAtendimento(listaDeGrupos.get(0));
    }

    @Override
    public void passarErrorObjeto(ErrorObj errorObj, int codigoErro) {
        this.fragEstabelecimento.errorConexaoModelo(errorObj, codigoErro);
    }
}
