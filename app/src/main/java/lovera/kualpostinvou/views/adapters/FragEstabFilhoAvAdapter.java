package lovera.kualpostinvou.views.adapters;

import java.util.List;

import lovera.kualpostinvou.conexao.contratos.MsgFromConexaoModelo;
import lovera.kualpostinvou.modelos.ConteudoPostagem;
import lovera.kualpostinvou.modelos.ErrorObj;
import lovera.kualpostinvou.modelos.Grupo;
import lovera.kualpostinvou.modelos.GrupoR;
import lovera.kualpostinvou.modelos.Media;
import lovera.kualpostinvou.modelos.PostagemR;
import lovera.kualpostinvou.modelos.TipoObjeto;
import lovera.kualpostinvou.views.fragments.fragestabelecimento.frag_filhos.avaliacao.FragFilho_Avaliacao;

public class FragEstabFilhoAvAdapter implements MsgFromConexaoModelo{

    private FragFilho_Avaliacao fragment;

    public FragEstabFilhoAvAdapter(FragFilho_Avaliacao fragment) {
        this.fragment = fragment;
    }

    @Override
    public void passarGrupo(GrupoR grupo, int resultCode) {
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
    public void passarPostagem(PostagemR postagem) {
        this.fragment.cadastrarTempoAtend_cadastrarConteudo(postagem);
    }

    @Override
    public void passarPostagem(PostagemR postagem, boolean usuarioPostou) {
        this.fragment.passarPostagensParaMedia(postagem, usuarioPostou);
    }

    @Override
    public void passarCodConteudoPostagem(ConteudoPostagem conteudo) {
        this.fragment.cadastrarTempoAtend_passarCodConteudoPostagem(conteudo);
    }

    @Override
    public void passarConteudoDaPostagem(ConteudoPostagem conteudoPostagem) {
        this.fragment.passarConteudoPostagemParaDialogTimer(conteudoPostagem);
    }

    @Override
    public void conteudoPostagemEditado(ConteudoPostagem conteudoPostagem) {
        this.fragment.cadastrarTempoAtend_passarCodConteudoPostagem(conteudoPostagem);
    }

    @Override
    public void passarMedia(Media media) {
        this.fragment.passarMedia(media);
    }

    @Override
    public void passarTipoObjeto(TipoObjeto tipoObjeto) {
        this.fragment.consumirAvTempoAtend_receberTipoDeObjeto(tipoObjeto);
    }
}
