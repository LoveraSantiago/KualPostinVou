package lovera.kualpostinvou.conexao.contratos;

import java.util.List;

import lovera.kualpostinvou.modelos.ErrorObj;
import lovera.kualpostinvou.modelos.Grupo;

public interface MsgFromConexaoModelo {

    int COD_ERRO_GRUPOS = 0;

    void passarListaDeGrupos(List<Grupo> listaDeGrupos);
    void passarErrorObjeto(ErrorObj errorObj, int codigoErro);
}
