package lovera.kualpostinvou.views.contratos;

import android.app.Fragment;

import java.util.List;

import lovera.kualpostinvou.modelos.Especialidade;
import lovera.kualpostinvou.modelos.Profissional;

public interface MsgToFragFilhos {

    List<Profissional> getListaDeProfissionais();
    List<Especialidade> getListaDeEspecialidades();
    Fragment getPaiFragment();

}
