package lovera.kualpostinvou.views.contratos;

import android.app.Fragment;

public interface MsgToActivity {

    boolean isAbertoProgresso();
    void abrirProgresso();
    void fecharProgresso();
    void setarTextoProgresso(String texto);
    void setarFragment(Fragment fragment);
}
