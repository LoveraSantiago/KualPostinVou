package lovera.kualpostinvou.views.fragments.fraglistaestabelecimentos;

import java.util.List;

import lovera.kualpostinvou.conexao.contratos.MsgFromConexaoSaude;
import lovera.kualpostinvou.modelos.Especialidade;
import lovera.kualpostinvou.modelos.Estabelecimento;
import lovera.kualpostinvou.modelos.Profissional;
import lovera.kualpostinvou.modelos.Servicos;

class Adapter implements MsgFromConexaoSaude {

    private Consumer consumer;


    public Adapter(Consumer consumer) {
        this.consumer = consumer;
    }

    @Override
    public void passarListaDeEstabelecimentos(List<Estabelecimento> listaDeEstabelecimentos) {
        this.consumer.callback_ConsumirEstabelecimento(listaDeEstabelecimentos.get(0));
    }

    @Override
    public void passarEstabelecimento(Estabelecimento estabelecimento) {
        this.consumer.callback_ConsumirEstabelecimento(estabelecimento);
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
}