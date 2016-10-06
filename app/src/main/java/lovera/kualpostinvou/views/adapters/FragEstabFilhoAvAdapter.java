package lovera.kualpostinvou.views.adapters;

import java.util.List;

import lovera.kualpostinvou.conexao.contratos.MsgFromConexaoModelo;
import lovera.kualpostinvou.modelos.ErrorObj;
import lovera.kualpostinvou.modelos.Grupo;
import lovera.kualpostinvou.views.fragments.frag_filhos.FragEstabFilho_Avaliacao;

public class FragEstabFilhoAvAdapter implements MsgFromConexaoModelo{

    private FragEstabFilho_Avaliacao fragment;

    public FragEstabFilhoAvAdapter(FragEstabFilho_Avaliacao fragment) {
        this.fragment = fragment;
    }

    @Override
    public void passarGrupo(Grupo grupo) {
        this.fragment.consumirAvTempoAtend_receberGrupo(grupo);
    }

    @Override
    public void passarListaDeGrupos(List<Grupo> listaDeGrupos) {

    }

    @Override
    public void passarErrorObjeto(ErrorObj errorObj, int codigoErro) {
        this.fragment.tratarErrorObjeto(errorObj, codigoErro);
    }
}
