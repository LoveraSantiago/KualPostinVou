package lovera.kualpostinvou.views.fragments.fragestabelecimento.frag_filhos.informacao;

import java.util.List;

import lovera.kualpostinvou.conexao.contratos.MsgFromConexaoSaude;
import lovera.kualpostinvou.modelos.Especialidade;
import lovera.kualpostinvou.modelos.Estabelecimento;
import lovera.kualpostinvou.modelos.Profissional;
import lovera.kualpostinvou.modelos.Servicos;
import lovera.kualpostinvou.views.fragments.fragestabelecimento.frag_filhos.informacao.FragFilho_Informacao;

class Adapter implements MsgFromConexaoSaude{

    private Consumer consumer;

    public Adapter(Consumer consumer) {
        this.consumer = consumer;
    }

    @Override
    public void passarListaDeEstabelecimentos(List<Estabelecimento> listaDeEstabelecimentos) {

    }

    @Override
    public void passarEstabelecimento(Estabelecimento estabelecimento) {

    }

    @Override
    public void passarListaDeEspecialidades(List<Especialidade> especialidades) {

    }

    @Override
    public void passarListaDeProfissionais(List<Profissional> profissionais) {
        this.consumer.callback_consumirProfissionais(profissionais);
    }

    @Override
    public void passarListaDeServicos(List<Servicos> servicos) {

    }
}
