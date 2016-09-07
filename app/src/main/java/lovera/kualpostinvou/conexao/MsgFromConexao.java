package lovera.kualpostinvou.conexao;

import java.util.List;

import lovera.kualpostinvou.modelos.Estabelecimento;

public interface MsgFromConexao {

    void passarListaDeEstabelecimentos(List<Estabelecimento> listaDeEstabelecimentos);

}
