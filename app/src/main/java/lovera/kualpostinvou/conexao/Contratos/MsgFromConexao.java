package lovera.kualpostinvou.conexao.contratos;

import java.util.List;

import lovera.kualpostinvou.modelos.Especialidade;
import lovera.kualpostinvou.modelos.Estabelecimento;

public interface MsgFromConexao {

    void passarListaDeEstabelecimentos(List<Estabelecimento> listaDeEstabelecimentos);
    void passarEstabelecimento(Estabelecimento estabelecimento);
    void passarListaDeEspecialidades(List<Especialidade> especialidades);
}
