package lovera.kualpostinvou.conexao.contratos;

import java.util.List;

import lovera.kualpostinvou.modelos.ConteudoPostagem;
import lovera.kualpostinvou.modelos.ErrorObj;
import lovera.kualpostinvou.modelos.Grupo;
import lovera.kualpostinvou.modelos.Media;
import lovera.kualpostinvou.modelos.Postagem;
import lovera.kualpostinvou.modelos.TipoObjeto;

public interface MsgFromConexaoModelo {

    int COD_ERRO_GRUPOS = 0;
    int COD_GRUPO_INEXISTENTE = 1;

    void passarGrupo(Grupo grupo, int resultCode);
    void passarListaDeGrupos(List<Grupo> listaDeGrupos);
    void passarErrorObjeto(ErrorObj errorObj, int codigoErro);
    void passarPostagem(Postagem postagem);
    void passarConteudoPostagem(ConteudoPostagem conteudo);
    void passarMedia(Media media);
    void passarTipoObjeto(TipoObjeto tipoObjeto);
}
