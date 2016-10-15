package lovera.kualpostinvou.conexao.contratos;

import java.util.List;

import lovera.kualpostinvou.modelos.ConteudoPostagem;
import lovera.kualpostinvou.modelos.ErrorObj;
import lovera.kualpostinvou.modelos.Grupo;
import lovera.kualpostinvou.modelos.GrupoR;
import lovera.kualpostinvou.modelos.Media;
import lovera.kualpostinvou.modelos.Postagem;
import lovera.kualpostinvou.modelos.PostagemR;
import lovera.kualpostinvou.modelos.TipoObjeto;

public interface MsgFromConexaoModelo {

    int COD_ERRO_GRUPOS = 0;
    int COD_GRUPO_INEXISTENTE = 1;

    void passarGrupo(GrupoR grupo, int resultCode);
    void passarListaDeGrupos(List<Grupo> listaDeGrupos);
    void passarErrorObjeto(ErrorObj errorObj, int codigoErro);
    void passarPostagem(PostagemR postagem);
    void passarCodConteudoPostagem(ConteudoPostagem conteudo);
    void passarMedia(Media media);
    void passarTipoObjeto(TipoObjeto tipoObjeto);
    void passarPostagem(PostagemR postagem, boolean usuarioPostou);
    void passarConteudoDaPostagem(ConteudoPostagem conteudoPostagem);
    void conteudoPostagemEditado(ConteudoPostagem body);
}
