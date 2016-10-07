package lovera.kualpostinvou.views.adapters;

import java.util.List;

import lovera.kualpostinvou.conexao.contratos.MsgFromConexaoModelo;
import lovera.kualpostinvou.modelos.ConteudoPostagem;
import lovera.kualpostinvou.modelos.ErrorObj;
import lovera.kualpostinvou.modelos.Grupo;
import lovera.kualpostinvou.modelos.Postagem;
import lovera.kualpostinvou.views.fragments.frag_filhos.FragEstabFilho_Avaliacao;

public class FragEstabFilhoAvAdapter implements MsgFromConexaoModelo{

    private FragEstabFilho_Avaliacao fragment;

    public FragEstabFilhoAvAdapter(FragEstabFilho_Avaliacao fragment) {
        this.fragment = fragment;
    }

    @Override
    public void passarGrupo(Grupo grupo, int resultCode) {
        this.fragment.consumirAvTempoAtend_receberGrupo(grupo, resultCode);
    }

    @Override
    public void passarListaDeGrupos(List<Grupo> listaDeGrupos) {

    }

    @Override
    public void passarErrorObjeto(ErrorObj errorObj, int codigoErro) {
        this.fragment.tratarErrorObjeto(errorObj, codigoErro);
    }

    @Override
    public void passarPostagem(Postagem postagem) {
        this.fragment.cadastrarTempoAtend_cadastrarConteudo(postagem);
    }

    @Override
    public void passarConteudoPostagem(ConteudoPostagem conteudo) {
        this.fragment.passarConteudoPostagem(conteudo);
    }
}
