package lovera.kualpostinvou.views.adapters;

import java.util.List;

import lovera.kualpostinvou.conexao.contratos.MsgFromConexaoModelo;
import lovera.kualpostinvou.conexao.contratos.MsgFromConexaoSaude;
import lovera.kualpostinvou.modelos.ConteudoPostagem;
import lovera.kualpostinvou.modelos.ErrorObj;
import lovera.kualpostinvou.modelos.Especialidade;
import lovera.kualpostinvou.modelos.Estabelecimento;
import lovera.kualpostinvou.modelos.Grupo;
import lovera.kualpostinvou.modelos.Postagem;
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
    }

    @Override
    public void passarListaDeServicos(List<Servicos> servicos) {

    }

    @Override
    public void passarGrupo(Grupo grupo) {

    }

    @Override
    public void passarListaDeGrupos(List<Grupo> listaDeGrupos) {
    }

    @Override
    public void passarErrorObjeto(ErrorObj errorObj, int codigoErro) {
    }

    @Override
    public void passarPostagem(Postagem postagem) {

    }

    @Override
    public void passarConteudoPostagem(ConteudoPostagem conteudo) {

    }
}
