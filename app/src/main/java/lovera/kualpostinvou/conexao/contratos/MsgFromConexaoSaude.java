package lovera.kualpostinvou.conexao.contratos;

import java.util.List;

import lovera.kualpostinvou.modelos.Especialidade;
import lovera.kualpostinvou.modelos.Estabelecimento;
import lovera.kualpostinvou.modelos.Profissional;
import lovera.kualpostinvou.modelos.Servicos;

public interface MsgFromConexaoSaude {

    void passarListaDeEstabelecimentos(List<Estabelecimento> listaDeEstabelecimentos);
    void passarEstabelecimento(Estabelecimento estabelecimento);
    void passarListaDeEspecialidades(List<Especialidade> especialidades);
    void passarListaDeProfissionais(List<Profissional> profissionais);
    void passarListaDeServicos(List<Servicos> servicos);
}
