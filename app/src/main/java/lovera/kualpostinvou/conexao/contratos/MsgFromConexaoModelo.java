package lovera.kualpostinvou.conexao.contratos;

import java.util.List;

import lovera.kualpostinvou.modelos.ErrorObj;
import lovera.kualpostinvou.modelos.Grupo;
import lovera.kualpostinvou.modelos.Postagem;

public interface MsgFromConexaoModelo {

    int COD_ERRO_GRUPOS = 0;
    int COD_GRUPO_INEXISTENTE = 1;

    void passarGrupo(Grupo grupo);
    void passarListaDeGrupos(List<Grupo> listaDeGrupos);
    void passarErrorObjeto(ErrorObj errorObj, int codigoErro);
    void passarPostagem(Postagem postagem);
}
