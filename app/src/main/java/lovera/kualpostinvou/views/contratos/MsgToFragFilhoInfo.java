package lovera.kualpostinvou.views.contratos;

import java.util.List;

import lovera.kualpostinvou.modelos.Especialidade;
import lovera.kualpostinvou.modelos.Profissional;

public interface MsgToFragFilhoInfo {

    List<Profissional> getListaDeProfissionais();
    List<Especialidade> getListaDeEspecialidades();

}
